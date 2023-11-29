package vn.edu.iuh.fit.www_lab05.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.www_lab05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.www_lab05.backend.models.*;
import vn.edu.iuh.fit.www_lab05.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.www_lab05.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.www_lab05.backend.services.CandidateServices;
import vn.edu.iuh.fit.www_lab05.backend.services.CandidateSkillService;
import vn.edu.iuh.fit.www_lab05.backend.services.JobService;
import vn.edu.iuh.fit.www_lab05.backend.services.SkillService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/can")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateServices candidateServices;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CandidateSkillService candidateSkillService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private JobService jobService;

    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll());
        return "candidates/list_no_paging";
    }

    @GetMapping("/candidates")
    public String showCandidateListPaging(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Candidate> candidatePage = candidateServices.findAll(currentPage - 1, pageSize, "id", "asc");

        model.addAttribute("candidatePage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "/candidates/list";
    }

    @GetMapping("/show-add-form")
    public ModelAndView add(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("address", candidate.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("candidates/add");
        return modelAndView;
    }

    @PostMapping("/add")
    public String addCandidate(@ModelAttribute("candidate") Candidate candidate, @ModelAttribute("address") Address address, BindingResult result, Model model) {
        addressRepository.save(address);
        candidate.setAddress(address);
        candidateRepository.save(candidate);
        return "redirect:/can/candidates";
    }

    @GetMapping("/show-edit-form/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Candidate> opt = candidateRepository.findById(id);
        if (opt.isPresent()) {
            Candidate candidate = opt.get();
            modelAndView.addObject("candidate", candidate);
            modelAndView.addObject("address", candidate.getAddress());
            modelAndView.addObject("countries", CountryCode.values());
            modelAndView.setViewName("candidates/update");
        }
        return modelAndView;
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("candidate") Candidate candidate, @ModelAttribute("address") Address address, BindingResult result, Model model) {
        System.out.println(candidate);
        addressRepository.save(address);
        candidate.setAddress(address);
        candidateRepository.save(candidate);
        return "redirect:/can/candidates";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        candidateServices.delete(id);
        return "redirect:/can/candidates";
    }

    @GetMapping("/details/{canId}")
    public String detailsCan(Model model, @PathVariable Long canId) {
        Candidate candidate = candidateServices.getCandidateById(canId);
        List<Job> recommendedJobs = jobService.recommendJobsForCandidate(canId);
        if (!recommendedJobs.isEmpty()) {
            model.addAttribute("recommendedJobs", recommendedJobs);
        } else {
            model.addAttribute("noJobsFound", true);
        }
        model.addAttribute("candidate", candidate);
        return "candidates/canDetails";
    }

    @GetMapping("/show-add-can-skill/{canId}")
    public ModelAndView showAddJobSkillForm(Model model, @PathVariable("canId") long canId) {
        ModelAndView modelAndView = new ModelAndView();

        CandidateSkill candidateSkill = new CandidateSkill();
        modelAndView.addObject("canId", canId);
        System.out.println(canId);
        modelAndView.addObject("skills", skillService.getSkill());
        modelAndView.addObject("canSkill", candidateSkill);
        modelAndView.setViewName("candidates/addCanSkill");

        return modelAndView;
    }


    @PostMapping("/addCanSkill")
    public String addJobSkill(
            @ModelAttribute("job") CandidateSkill candidateSkill,
            @RequestParam("canId") long canId,
            @RequestParam("skillId") int skillId,
            @RequestParam("skillLevel") SkillLevel skillLevel,

            Model model) {

        Candidate candidate = candidateServices.getCandidateById(canId);
        candidateSkill.setCandidate(candidate);

        Skill skill = skillService.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid skill Id:" + skillId));
        candidateSkill.setSkill(skill);

        candidateSkill.setSkillLevel(skillLevel);


        candidateSkillService.save(candidateSkill);
        return "redirect:/can/details/" + candidate.getId();
    }
}
