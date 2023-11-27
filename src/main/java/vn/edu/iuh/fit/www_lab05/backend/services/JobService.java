package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.Job;
import vn.edu.iuh.fit.www_lab05.backend.repositories.JobRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobPostings() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobPostingById(Long id) {
        return jobRepository.findById(id);
    }

    public Job saveJobPosting(Job jobPosting) {
        return jobRepository.save(jobPosting);
    }

    public void deleteJobPosting(Long id) {
        jobRepository.deleteById(id);
    }
}
