package hr.support.app.dao;

import hr.support.app.domain.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SkillDao  extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);


    Page<Skill> findByNameContaining(String nameFilter, Pageable pageable);
}
