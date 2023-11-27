package vn.edu.iuh.fit.www_lab05.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.www_lab05.models.JobSkill;
import vn.edu.iuh.fit.www_lab05.models.Skill;

public interface JobSkillRepository extends JpaRepository<JobSkill, Skill> {
}