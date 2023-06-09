package hr.support.app.controller;


import hr.support.app.dto.SkillDto;
import hr.support.app.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }



    @PostMapping()
    public ResponseEntity<Object> save(@Valid @RequestBody SkillDto skillDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(skillService.save(skillDto));
        } catch (EntityExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<Object> update(@Valid @RequestBody SkillDto skillDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(skillService.update(skillDto));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            skillService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(skillService.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("filter")
    public ResponseEntity<Page<SkillDto>> findByName(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "5") Integer pageSize,
                                                     @RequestParam(defaultValue = "name") String sortBy,
                                                     @RequestParam(defaultValue = "asc") String sortOrder,
                                                     @RequestParam(required = false, defaultValue = "") String nameFilter) {
        return new ResponseEntity<Page<SkillDto>>(skillService.findByName(pageNo, pageSize, sortBy, sortOrder, nameFilter), new HttpHeaders(), HttpStatus.OK);
    }


}
