package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.Experience;
import vn.edu.iuh.fit.www_lab05.backend.repositories.ExperienceRepository;

import java.util.Optional;

@Service
public class ExperienceServices {
    @Autowired
    private   ExperienceRepository experienceRepository;

    public void getAll() {experienceRepository.findAll();}
    public void save(Experience  experience){experienceRepository.save(experience);}
}
