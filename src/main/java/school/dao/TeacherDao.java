package school.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import school.entity.Teacher;

public interface TeacherDao extends JpaRepository<Teacher, Long> {

}
