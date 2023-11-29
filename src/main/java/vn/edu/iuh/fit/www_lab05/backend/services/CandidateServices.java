package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.Candidate;
import vn.edu.iuh.fit.www_lab05.backend.models.Skill;
import vn.edu.iuh.fit.www_lab05.backend.repositories.CandidateRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServices {
    @Autowired
    private CandidateRepository candidateRepository;

    public Page<Candidate> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return candidateRepository.findAll(pageable);
    }

    public Page<Candidate> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Candidate> list;
        List<Candidate> candidates = candidateRepository.findAll();

        if (candidates.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, candidates.size());
            list = candidates.subList(startItem, toIndex);
        }

        Page<Candidate> candidatePage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), candidates.size());

        return candidatePage;
    }

    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

    public Candidate updateCandidate(Long id, Candidate updatedCandidate) {
        Candidate existingCandidate = candidateRepository.findById(id).orElse(null);
        if (existingCandidate != null) {
            existingCandidate.setFullName(updatedCandidate.getFullName());
            existingCandidate.setCandidateSkills(updatedCandidate.getCandidateSkills());
            existingCandidate.setAddress(updatedCandidate.getAddress());
            existingCandidate.setDob(updatedCandidate.getDob());
            existingCandidate.setEmail(updatedCandidate.getEmail());
            existingCandidate.setExperiences(updatedCandidate.getExperiences());

            return candidateRepository.save(existingCandidate);
        }
        return null;
    }

    public void delete(long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        candidate.ifPresent(value -> candidateRepository.delete(value));
    }
    public List<Candidate> findCandidatesBySkills(List<Skill> candidateSkill) {
        return candidateRepository.findCandidatesCandidateSkills(candidateSkill);
    }

}
