package hr.support.app.service;

import hr.support.app.dto.CandidateDto;
import org.springframework.data.domain.Page;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;


public interface CandidateService {

    CandidateDto save(CandidateDto candidateDto) throws EntityExistsException;

    CandidateDto update(CandidateDto candidateDto) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;

    CandidateDto findById(Long id);

    Page<CandidateDto> filterByName(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String nameFilter);

    Page<CandidateDto> filterBySkill(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String skillName);

    CandidateDto addSkill(Long candidateId, Long skillId) throws EntityNotFoundException;

    CandidateDto removeSkill(Long candidateId, Long skillId) throws EntityNotFoundException;


}
