package vn.edu.iuh.fit.www_lab05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.www_lab05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Skill> {
}