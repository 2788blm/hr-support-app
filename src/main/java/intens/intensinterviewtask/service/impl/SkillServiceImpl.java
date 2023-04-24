package intens.intensinterviewtask.service.impl;

import intens.intensinterviewtask.dao.SkillDao;
import intens.intensinterviewtask.domain.Skill;
import intens.intensinterviewtask.dto.SkillDto;
import intens.intensinterviewtask.mapper.SkillMapper;
import intens.intensinterviewtask.service.SkillService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SkillServiceImpl implements SkillService {

    private final SkillDao skillDao;

    private final SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);

    @Autowired
    public SkillServiceImpl(SkillDao skillDao) {
        this.skillDao = skillDao;
    }


    @Transactional
    @Override
    public SkillDto save(SkillDto skillDto) throws EntityExistsException {
        Optional<Skill> entity = skillDao.findByName(skillDto.getName());
        if (entity.isPresent()) {
            throw new EntityExistsException("Skill already exists!");
        }
        Skill skill = skillDao.save(skillMapper.toEntity(skillDto));
        return skillMapper.toDto(skill);
    }

    @Transactional
    @Override
    public SkillDto update(SkillDto skillDto) throws EntityNotFoundException {
        Optional<Skill> entity = skillDao.findById(skillDto.getId());
        if (!entity.isPresent()) {
            throw new EntityNotFoundException("Skill does not exist:" + skillDto.getName());
        }
        Skill c = skillDao.save(skillMapper.toEntity(skillDto));
        return skillMapper.toDto(c);
    }

    @Transactional
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<Skill> entity = skillDao.findById(id);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException("Skill does not exist!");
        }
        skillDao.deleteById(id);
    }

    @Override
    public SkillDto findById(Long id) {
        Optional<Skill> entity = skillDao.findById(id);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException("Skill with id " + id + " does not exist");
        }
        return skillMapper.toDto(entity.get());
    }

    @Override
    public Page<SkillDto> findByName(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String nameFilter) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        Page<Skill> skills = skillDao.findByNameContaining(nameFilter, pageable);
        return skills.map(skillMapper::toDto);
    }


}
