package school.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import school.entity.Subject;

public interface SubjectDao extends JpaRepository<Subject, Long> {

}
