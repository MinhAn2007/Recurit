package vn.edu.iuh.fit.www_lab05.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillType;
import vn.edu.iuh.fit.www_lab05.backend.models.*;
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
    @GetMapping("/details/{companyId}")
    public String getCompany(Model model, @PathVariable Long companyId) {
        Company company = companyService.getCompanyById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found")); // Replace with a more appropriate exception
        model.addAttribute("company", company);
        return "company/details";
    }
    @GetMapping("/addJob/{companyId}")
    public ModelAndView showCompanyJobsForm(@PathVariable Long companyId) {
        ModelAndView modelAndView = new ModelAndView("company/post");

        Job job = new Job();
        List<JobSkill> jobSkills = job.getJobSkills();
        List<Skill> skills = new ArrayList<>();

        if (jobSkills != null) {
            for (JobSkill jobSkill : jobSkills) {
                if (jobSkill.getSkill() != null) {
                    skills.add(jobSkill.getSkill());
                }
            }
        }

        modelAndView.addObject("job",job);
        modelAndView.addObject("company", companyId);
        modelAndView.addObject("skills", skills);
        modelAndView.addObject("type", SkillType.values());


        return modelAndView;
    }
    @GetMapping("/show-add/{companyId}")
    public ModelAndView showAddJobForm(Model model, @PathVariable("companyId") long companyId) {
        ModelAndView modelAndView = new ModelAndView();

        Job job = new Job();
        modelAndView.addObject("companyId",companyId);
        System.out.println(companyId);

        modelAndView.addObject("job",job);
        modelAndView.setViewName("company/addJob");

        return modelAndView;
    }
    @PostMapping("/add")
    public String addJob(
            @ModelAttribute("job") Job job,
            @RequestParam("companyId") long companyId,
            Model model) {

        Company company = companyService.getCompanyById(companyId) .orElseThrow(() -> new IllegalArgumentException("Invalid companyId posting Id:" + companyId));
        job.setCompany(company);

        jobService.save(job);

        return "redirect:/company/details/" + companyId;
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
