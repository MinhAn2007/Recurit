package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.www_lab05.backend.repositories.CandidateSkillRepository;

@Service
public class CandidateSkillService {
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    public void save(CandidateSkill candidateSkill){candidateSkillRepository.save(candidateSkill);}
}
