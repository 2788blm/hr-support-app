package intens.intensinterviewtask.dao;

import intens.intensinterviewtask.domain.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.DoubleStream;


@Repository
public interface SkillDao  extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);


    Page<Skill> findByNameContaining(String nameFilter, Pageable pageable);
}
