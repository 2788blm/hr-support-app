package intens.intensinterviewtask.service;

import intens.intensinterviewtask.dto.SkillDto;
import org.springframework.data.domain.Page;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface SkillService {

    SkillDto save(SkillDto skillDto) throws EntityExistsException;

    SkillDto update(SkillDto skillDto) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;

    SkillDto findById(Long id);

    Page<SkillDto> findByName(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String nameFilter);

}
