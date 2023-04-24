package intens.intensinterviewtask.mapper;


import intens.intensinterviewtask.domain.Candidate;
import intens.intensinterviewtask.dto.CandidateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    Candidate toEntity(CandidateDto candidateDto);
    CandidateDto toDto(Candidate candidate);
}
