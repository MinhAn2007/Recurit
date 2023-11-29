package vn.edu.iuh.fit.www_lab05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.www_lab05.backend.models.Candidate;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;

import java.util.List;


public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query("SELECT c FROM Candidate c JOIN c.candidateSkills cs WHERE cs.skill IN :skills")
    List<Candidate> findCandidatesCandidateSkills(@Param("skills") List<Skill> skills);
}
