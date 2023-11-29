package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.Candidate;
import vn.edu.iuh.fit.www_lab05.backend.models.Job;
import vn.edu.iuh.fit.www_lab05.backend.models.JobSkill;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;
import vn.edu.iuh.fit.www_lab05.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.www_lab05.backend.repositories.JobRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    public Page<Job> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findAll(pageable);
    }
    public Optional<Job> getJobPostingById(Long id) {
        return jobRepository.findById(id);
    }

    public void save(Job jobPosting) {
         jobRepository.save(jobPosting);
    }

    public void deleteJobPosting(Long id) {
        jobRepository.deleteById(id);
    }

    public List<Job> recommendJobsForCandidate(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);

        if (candidate != null) {
            List<Long> candidateSkillIds = candidate.getCandidateSkills()
                    .stream()
                    .map(candidateSkill -> candidateSkill.getSkill().getId())  // Map to skill ID
                    .collect(Collectors.toList());

            List<Job> recommendedJobs = jobRepository.findByJobSkillsSkillIdIn(candidateSkillIds);

            return recommendedJobs;
        }

        return Collections.emptyList();
    }


    public List<Candidate> getRecommendedCandidatesForJob(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);

        if (job != null) {
            List<Skill> requiredSkills = job.getJobSkills()
                    .stream()
                    .map(JobSkill::getSkill)
                    .collect(Collectors.toList());

            return candidateRepository.findCandidatesCandidateSkills(requiredSkills);
        }

        return Collections.emptyList();
    }
}
