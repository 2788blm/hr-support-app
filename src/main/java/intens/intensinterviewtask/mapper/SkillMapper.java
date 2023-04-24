package intens.intensinterviewtask.mapper;


import intens.intensinterviewtask.domain.Skill;
import intens.intensinterviewtask.dto.SkillDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill toEntity(SkillDto skillDto);
    SkillDto toDto(Skill skill);
}
