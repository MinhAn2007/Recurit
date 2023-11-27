package vn.edu.iuh.fit.www_lab05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.www_lab05.backend.models.Address;
import vn.edu.iuh.fit.www_lab05.backend.models.Candidate;
import vn.edu.iuh.fit.www_lab05.backend.models.Job;
import vn.edu.iuh.fit.www_lab05.backend.services.JobService;

import java.util.List;

@Controller
@RequestMapping("/job-postings")

public class JobController {
    @Autowired
    private JobService jobService;
    @GetMapping("/list")
    public String listJobPostings(Model model) {
        List<Job> jobPostings = jobService.getAllJobPostings();
        model.addAttribute("jobPostings", jobPostings);
        return "jobpostings/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("jobPosting", new Job());
        return "jobpostings/create";
    }

    @PostMapping("/create")
    public String createJobPosting(@ModelAttribute Job jobPosting) {
        jobService.saveJobPosting(jobPosting);
        return "redirect:/job-postings";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Job jobPosting = jobService.getJobPostingById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job posting Id:" + id));
        model.addAttribute("jobPosting", jobPosting);
        return "jobpostings/edit";
    }
    @PostMapping("/update/{id}")
    public String editJobPosting( @ModelAttribute Job jobPosting) {
        jobService.saveJobPosting(jobPosting);
        return "redirect:/job-postings";
    }

    @GetMapping("/delete/{id}")
    public String deleteJobPosting(@PathVariable Long id) {
        jobService.deleteJobPosting(id);
        return "redirect:/job-postings";
    }
}
