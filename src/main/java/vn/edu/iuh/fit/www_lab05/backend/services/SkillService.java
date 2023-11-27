package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;
import vn.edu.iuh.fit.www_lab05.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.www_lab05.backend.repositories.SkillRepository;

import java.util.List;
import java.util.Optional;

@Service

public class SkillService {
    @Autowired
    private SkillRepository skillRepository;


    public Skill addSkill(Skill skill){
        return skillRepository.save(skill);
    }

    public List<Skill> getSkill(){
        return skillRepository.findAll();
    }

    public void deleteSkill(long id){
        skillRepository.deleteById(id);
    }

    public Optional<Skill> findById(long id){
        return skillRepository.findById(id);
    }
}

