package vn.edu.iuh.fit.www_lab05.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.www_lab05.models.CandidateSkill;
import vn.edu.iuh.fit.www_lab05.models.Skill;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Skill> {
}