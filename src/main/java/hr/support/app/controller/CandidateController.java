package hr.support.app.controller;


import hr.support.app.dto.CandidateDto;
import hr.support.app.service.CandidateService;
import hr.support.app.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;
    private final SkillService skillService;

    @Autowired
    public CandidateController(CandidateService candidateService, SkillService skillService) {
        this.candidateService = candidateService;
        this.skillService = skillService;
    }



    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody CandidateDto candidateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(candidateService.save(candidateDto));
        } catch (EntityExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@Valid @RequestBody CandidateDto candidateDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(candidateService.update(candidateDto));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            candidateService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(candidateService.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("nameFilter")
    public ResponseEntity<Page<CandidateDto>> filterByName(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "5") Integer pageSize,
                                                           @RequestParam(defaultValue = "fullName") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String sortOrder,
                                                           @RequestParam(required = false, defaultValue = "") String nameFilter) {

        return new ResponseEntity<Page<CandidateDto>>(candidateService.filterByName(pageNo, pageSize, sortBy, sortOrder, nameFilter), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("skillFilter")
    public ResponseEntity<Page<CandidateDto>> filterBySkill(@RequestParam(defaultValue = "0") Integer pageNo,
                                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                                            @RequestParam(defaultValue = "fullName") String sortBy,
                                                            @RequestParam(defaultValue = "asc") String sortOrder,
                                                            @RequestParam(required = false, defaultValue = "") String skillFilter) {

        return new ResponseEntity<Page<CandidateDto>>(candidateService.filterBySkill(pageNo, pageSize, sortBy, sortOrder, skillFilter), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("{candidateId}/skill/{skillId}")
    public ResponseEntity<Object> addSkill(@PathVariable Long candidateId, @PathVariable Long skillId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(candidateService.addSkill(candidateId, skillId));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("{candidateId}/skill/{skillId}")
    public ResponseEntity<Object> removeSkill(@PathVariable Long candidateId, @PathVariable Long skillId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(candidateService.removeSkill(candidateId, skillId));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
