package hr.support.app.dto;

import hr.support.app.domain.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {

    private Long id;

    @NotBlank
    private String fullName;

    private LocalDate dateOfBirth;

    @NotBlank
    private String contactNumber;

    @NotBlank
    private String email;

    private Set<Skill> skills;


}
