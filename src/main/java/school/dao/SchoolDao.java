package school.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import school.entity.School;

public interface SchoolDao extends JpaRepository<School, Long> {

}
