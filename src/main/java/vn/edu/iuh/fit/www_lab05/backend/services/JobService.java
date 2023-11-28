package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.www_lab05.backend.models.Candidate;
import vn.edu.iuh.fit.www_lab05.backend.models.Job;
import vn.edu.iuh.fit.www_lab05.backend.models.JobSkill;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;
import vn.edu.iuh.fit.www_lab05.backend.repositories.JobRepository;
import vn.edu.iuh.fit.www_lab05.backend.repositories.SkillRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
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


}
