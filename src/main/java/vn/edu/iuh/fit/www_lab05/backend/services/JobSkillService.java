package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.*;
import vn.edu.iuh.fit.www_lab05.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.www_lab05.backend.repositories.JobRepository;
import vn.edu.iuh.fit.www_lab05.backend.repositories.JobSkillRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSkillService {
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public void save(JobSkill jobSkill) {
        jobSkillRepository.save(jobSkill);
    }


}
