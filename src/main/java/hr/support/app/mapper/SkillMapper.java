package hr.support.app.mapper;


import hr.support.app.domain.Skill;
import hr.support.app.dto.SkillDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);

    Skill toEntity(SkillDto skillDto);
    SkillDto toDto(Skill skill);


}
