package hr.support.app.service.impl;

import hr.support.app.dao.CandidateDao;
import hr.support.app.dao.SkillDao;
import hr.support.app.dto.CandidateDto;
import hr.support.app.domain.Candidate;
import hr.support.app.domain.Skill;
import hr.support.app.mapper.CandidateMapper;
import hr.support.app.service.CandidateService;
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
public class CandidateServiceImpl implements CandidateService {

    private final CandidateDao candidateDao;
    private final SkillDao skillDao;

    private final CandidateMapper candidateMapper = Mappers.getMapper(CandidateMapper.class);

    @Autowired
    public CandidateServiceImpl(CandidateDao candidateDao, SkillDao skillDao) {
        this.candidateDao = candidateDao;
        this.skillDao = skillDao;
    }


    @Transactional
    @Override
    public CandidateDto save(CandidateDto candidateDto) throws EntityExistsException {
        if(candidateDto.getId() != null){
            Optional<Candidate> entity = candidateDao.findById(candidateDto.getId());
            if (entity.isPresent()) {
                throw new EntityExistsException("Candidate already exists!");
            }
        }
        if(candidateDto.getEmail() != null){
            Optional<Candidate> entity = candidateDao.findByEmail(candidateDto.getEmail());
            if (entity.isPresent()) {
                throw new EntityExistsException("Candidate with email: " + candidateDto.getEmail() + " already exists!");
            }
        }
            Candidate candidate = candidateDao.save(candidateMapper.toEntity(candidateDto));
        return candidateMapper.toDto(candidate);
    }

    @Transactional
    @Override
    public CandidateDto update(CandidateDto candidateDto) throws EntityNotFoundException {
        Optional<Candidate> entity = candidateDao.findById(candidateDto.getId());
        if (!entity.isPresent()) {
            throw new EntityNotFoundException("Candidate with id " + candidateDto.getId() + " does not exist");
        }
        Candidate candidate = candidateDao.save(candidateMapper.toEntity(candidateDto));
        return candidateMapper.toDto(candidate);
    }

    @Transactional
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<Candidate> entity = candidateDao.findById(id);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException("Candidate with id " + id + " does not exist");
        }
        candidateDao.deleteById(id);
    }

    @Override
    public CandidateDto findById(Long id) {
        Optional<Candidate> entity = candidateDao.findById(id);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException("Candidate with id " + id + " does not exist");
        }
        return candidateMapper.toDto(entity.get());
    }

    @Override
    public Page<CandidateDto> filterByName(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String nameFilter) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return candidateDao.findByFullNameContaining(nameFilter, pageable).map(candidateMapper::toDto);
    }

    @Override
    public Page<CandidateDto> filterBySkill(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String skillName) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return candidateDao.findAllBySkillName(skillName, pageable).map(candidateMapper::toDto);
    }

    @Transactional
    @Override
    public CandidateDto addSkill(Long candidateId, Long skillId) throws EntityNotFoundException {
        Optional<Candidate> candidateOpt = candidateDao.findById(candidateId);
        Optional<Skill> skillOpt = skillDao.findById(skillId);
        if(!candidateOpt.isPresent()) {
            throw new EntityNotFoundException("Candidate with id " + candidateId + " does not exist");
        }
        if (!skillOpt.isPresent()) {
            throw new EntityNotFoundException("Skill with id " + skillId + " does not exist");
        }
        candidateOpt.get().getSkills().add(skillOpt.get());
        Candidate candidate = candidateDao.save(candidateOpt.get());
        return candidateMapper.toDto(candidate);
    }

    @Transactional
    @Override
    public CandidateDto removeSkill(Long candidateId, Long skillId) throws EntityNotFoundException {
        Optional<Candidate> candidateOpt = candidateDao.findById(candidateId);
        Optional<Skill> skillOpt = skillDao.findById(skillId);
        if(!candidateOpt.isPresent()) {
            throw new EntityNotFoundException("Candidate with id " + candidateId + " does not exist");
        }
        if (!skillOpt.isPresent()) {
            throw new EntityNotFoundException("Skill with id " + skillId + " does not exist");
        }
        candidateOpt.get().getSkills().remove(skillOpt.get());
        Candidate candidate = candidateDao.save(candidateOpt.get());
        return candidateMapper.toDto(candidate);
    }


}
