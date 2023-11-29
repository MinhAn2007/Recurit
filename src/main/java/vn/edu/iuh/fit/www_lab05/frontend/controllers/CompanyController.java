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
import vn.edu.iuh.fit.www_lab05.backend.services.JobSkillService;
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
    @Autowired
    private JobSkillService jobSkillService;

    @GetMapping("/list")
    public String getAllCompanies(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "company/list";
    }

    @GetMapping("/details/{companyId}")
    public String getCompany(Model model, @PathVariable Long companyId) {
        Company company = companyService.getCompanyById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        model.addAttribute("company", company);
        return "company/details";
    }

    @GetMapping("/detailsJob/{jobId}")
    public String detailsJob(Model model, @PathVariable Long jobId) {
        Job job = jobService.getJobPostingById(jobId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        model.addAttribute("job", job);
        List<Candidate> recommendedCandidates = jobService.getRecommendedCandidatesForJob(jobId);
        model.addAttribute("recommendedCandidates", recommendedCandidates);

        return "company/detailsJob";
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

        modelAndView.addObject("job", job);
        modelAndView.addObject("company", companyId);
        modelAndView.addObject("skills", skills);
        modelAndView.addObject("type", SkillType.values());


        return modelAndView;
    }

    @GetMapping("/show-add/{companyId}")
    public ModelAndView showAddJobForm(Model model, @PathVariable("companyId") long companyId) {
        ModelAndView modelAndView = new ModelAndView();

        Job job = new Job();
        modelAndView.addObject("companyId", companyId);
        System.out.println(companyId);

        modelAndView.addObject("job", job);
        modelAndView.setViewName("company/addJob");

        return modelAndView;
    }

    @PostMapping("/add")
    public String addJob(
            @ModelAttribute("job") Job job,
            @RequestParam("companyId") long companyId,
            Model model) {

        Company company = companyService.getCompanyById(companyId).orElseThrow(() -> new IllegalArgumentException("Invalid companyId posting Id:" + companyId));
        job.setCompany(company);

        jobService.save(job);

        return "redirect:/company/details/" + companyId;
    }

    @PostMapping("/addSkill")
    public String addJobSkill(
            @ModelAttribute("job") Skill skill,

            Model model) {

        skillService.addSkill(skill);

        return "redirect:/company/list";
    }

    @GetMapping("/show-add-job-skill/{jobId}/{companyId}")
    public ModelAndView showAddJobSkillForm(Model model, @PathVariable("jobId") long jobId, @PathVariable("companyId") long companyId) {
        ModelAndView modelAndView = new ModelAndView();

        JobSkill jobSkill = new JobSkill();
        modelAndView.addObject("jobId", jobId);
        System.out.println(jobId);
        modelAndView.addObject("skills", skillService.getSkill());
        modelAndView.addObject("jobSkill", jobSkill);
        modelAndView.setViewName("company/addJobSkill");

        return modelAndView;
    }



    @PostMapping("/addJobSkill")
    public String addJobSkill(
            @ModelAttribute("job") JobSkill jobSkill,
            @RequestParam("jobId") long jobId,
            @RequestParam("skillId") int skillId,
            @RequestParam("companyId") long companyId,
            @RequestParam("skillLevel") SkillLevel skillLevel,

            Model model) {

        Job job = jobService.getJobPostingById(jobId).orElseThrow(() -> new IllegalArgumentException("Invalid job posting Id:" + jobId));
        jobSkill.setJob(job);

        Skill skill = skillService.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid skill Id:" + skillId));
        jobSkill.setSkill(skill);

        jobSkill.setSkillLevel(skillLevel);


        jobSkillService.save(jobSkill);
        return "redirect:/company/detailsJob/" + job.getId();
    }
}
