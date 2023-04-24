package intens.intensinterviewtask.dao;

import intens.intensinterviewtask.domain.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidateDao  extends JpaRepository<Candidate, Long> {

    Page<Candidate> findByFullNameContaining(String nameFilter, Pageable pageable);

    @Query("SELECT c FROM Candidate c JOIN c.skills s WHERE s.name LIKE %:skillName%")
    Page<Candidate> findAllBySkillName(@Param("skillName") String skillName, Pageable pageable);

}
