package school.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import schools.controller.model.JoinTableController;

@Repository
public interface JoinTableDao extends JpaRepository<JoinTableController, Long> {
    // Add any additional custom methods, if needed
}
