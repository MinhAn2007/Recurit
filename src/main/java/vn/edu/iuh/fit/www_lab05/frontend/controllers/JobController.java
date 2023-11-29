package vn.edu.iuh.fit.www_lab05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.www_lab05.backend.models.*;
import vn.edu.iuh.fit.www_lab05.backend.services.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/job")

public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private JobSkillService jobSkillService;
    @Autowired
    private CandidateServices candidateServices;
    @GetMapping("/list")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Job> candidatePage = jobService.findAll(currentPage - 1,
                pageSize, "id", "asc");

        model.addAttribute("jobPage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "/job/list";
    }



    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Job jobPosting = jobService.getJobPostingById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job posting Id:" + id));
        model.addAttribute("jobPosting", jobPosting);
        return "job/edit";
    }


    @GetMapping("/delete/{id}")
    public String deleteJobPosting(@PathVariable Long id) {
        jobService.deleteJobPosting(id);
        return "redirect:/job";
    }
    @PostMapping("/skills/save")
    public String saveSkill(Skill skill){
        skillService.addSkill(skill);

        return "redirect:/skills";
    }
}
