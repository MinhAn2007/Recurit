package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillType;
import vn.edu.iuh.fit.www_lab05.backend.models.Candidate;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;
import vn.edu.iuh.fit.www_lab05.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.www_lab05.backend.repositories.SkillRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class SkillService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private CandidateRepository candidateRepository;


    public Skill addSkill(Skill skill){
        return skillRepository.save(skill);
    }

    public List<Skill> getSkill(){
        return skillRepository.findAll();
    }

    public List<Skill> suggestSkillsForCandidate(Long candidateId) {
        // Lấy thông tin ứng viên
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);

        if (candidate != null) {
            // Lấy tất cả kỹ năng trong hệ thống
            List<Skill> allSkills = skillRepository.findAll();

            // Lấy tất cả kỹ năng của ứng viên
            List<Skill> candidateSkills = candidate.getCandidateSkills()
                    .stream()
                    .map(candidateSkill -> candidateSkill.getSkill())
                    .collect(Collectors.toList());

            // Lọc những kỹ năng mà ứng viên chưa có
            List<Skill> missingSkills = allSkills.stream()
                    .filter(skill -> !candidateSkills.contains(skill))
                    .toList();

            int numberOfSuggestions = 10;

            return missingSkills.stream()
                    .filter(skill -> {
                        SkillType lastSkillType = candidateSkills.isEmpty() ?
                                SkillType.UNSPECIFIC : candidateSkills.get(candidateSkills.size() - 1).getType();

                        return suggestSkillTypes(lastSkillType).contains(skill.getType());
                    })
                    .limit(numberOfSuggestions)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private List<SkillType> suggestSkillTypes(SkillType existingSkillType) {
        List<SkillType> suggestedSkillTypes = new ArrayList<>();

        switch (existingSkillType) {
            case UNSPECIFIC -> {
                suggestedSkillTypes.add(SkillType.TECHNICAL_SKILL);
                suggestedSkillTypes.add(SkillType.SOFT_SKILL);
            }
            case TECHNICAL_SKILL -> suggestedSkillTypes.add(SkillType.SOFT_SKILL);
            case SOFT_SKILL -> suggestedSkillTypes.add(SkillType.TECHNICAL_SKILL);
            default -> {
            }
        }

        return suggestedSkillTypes;
    }

    public Optional<Skill> findById(long id){
        return skillRepository.findById(id);
    }
}

