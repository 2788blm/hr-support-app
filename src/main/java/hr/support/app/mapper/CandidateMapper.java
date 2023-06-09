package hr.support.app.mapper;


import hr.support.app.dto.CandidateDto;
import hr.support.app.domain.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

    Candidate toEntity(CandidateDto candidateDto);
    CandidateDto toDto(Candidate candidate);


}
