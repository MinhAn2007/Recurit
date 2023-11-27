package vn.edu.iuh.fit.www_lab05.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.www_lab05.models.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}