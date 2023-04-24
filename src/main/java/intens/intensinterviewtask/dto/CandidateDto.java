package intens.intensinterviewtask.dto;

import intens.intensinterviewtask.domain.Skill;
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

    private LocalDate birthdate;

    @NotBlank
    private String contactNumber;

    @NotBlank
    private String email;

    private Set<Skill> skills;


}
