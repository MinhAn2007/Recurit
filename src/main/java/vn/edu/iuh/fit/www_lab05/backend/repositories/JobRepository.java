package vn.edu.iuh.fit.www_lab05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.www_lab05.backend.models.Job;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;

import java.util.List;


public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByJobSkillsSkillIdIn(List<Long> skillIds);

}
