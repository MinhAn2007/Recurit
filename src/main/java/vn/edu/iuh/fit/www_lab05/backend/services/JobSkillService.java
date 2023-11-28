package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.JobSkill;
import vn.edu.iuh.fit.www_lab05.backend.repositories.JobSkillRepository;

@Service
public class JobSkillService {
    @Autowired
    private JobSkillRepository skillRepository;

    public void save(JobSkill jobSkill){skillRepository.save(jobSkill);}
}
