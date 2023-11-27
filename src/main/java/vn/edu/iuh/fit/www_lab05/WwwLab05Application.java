package vn.edu.iuh.fit.www_lab05;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillType;
import vn.edu.iuh.fit.www_lab05.backend.models.*;
import vn.edu.iuh.fit.www_lab05.backend.repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class WwwLab05Application {

    public static void main(String[] args) {
        SpringApplication.run(WwwLab05Application.class, args);
    }
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private SkillRepository skillRepository;



}
