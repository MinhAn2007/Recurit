package vn.edu.iuh.fit.www_lab05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillType;
import vn.edu.iuh.fit.www_lab05.backend.models.Company;
import vn.edu.iuh.fit.www_lab05.backend.models.Job;
import vn.edu.iuh.fit.www_lab05.backend.models.JobSkill;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;
import vn.edu.iuh.fit.www_lab05.backend.services.CompanyService;
import vn.edu.iuh.fit.www_lab05.backend.services.JobService;
import vn.edu.iuh.fit.www_lab05.backend.services.SkillService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobService jobService;

    @Autowired
    private SkillService skillService;

    @GetMapping("/list")
    public String getAllCompanies(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "company/list";
    }

    @GetMapping("/{companyId}/jobs")
    public String showCompanyJobsForm(@PathVariable Long companyId, Model model) {
        Optional<Company> companyOptional = companyService.getCompanyById(companyId);

        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            Job job = new Job();

            List<Skill> skills = skillService.getSkill();


            model.addAttribute("company", company);
            model.addAttribute("job", job);
            model.addAttribute("skills", skills);

            return "company/post";
        } else {
            throw new IllegalArgumentException("Company not found with ID: " + companyId);
        }
    }

    @PostMapping("/{companyId}/jobs")
    public String saveJob(@ModelAttribute("job")  Job job,
                          @RequestParam(name = "skills") String skills,
                          @PathVariable String companyId,
                          BindingResult bindingResult) {

        // Validate the form inputs
        if (bindingResult.hasErrors()) {
            // Handle validation errors, if any
            return "company/post";
        }

        // Split the skills input into an array
        String[] skillArray = skills.split(",");

        // Create a new list to store JobSkills
        List<JobSkill> jobSkills = new ArrayList<>();

        // Iterate over the skills array and create JobSkill objects
        for (String skillName : skillArray) {
            JobSkill jobSkill = new JobSkill();
            jobSkill.getSkill().setSkillName(skillName.trim());  // Trim to remove leading/trailing whitespaces
            jobSkill.setJob(job);
            // You may set other properties of JobSkill here

            // Add the JobSkill to the list
            jobSkills.add(jobSkill);
        }

        // Set the list of JobSkills in the Job object
        job.setJobSkills(jobSkills);

        // Continue with the rest of your logic...
        // For example, save the job and redirect to the company's jobs page

        return "redirect:/company/" + companyId + "/jobs";
    }}
