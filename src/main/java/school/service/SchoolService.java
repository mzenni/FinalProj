package school.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import school.dao.SchoolDao;
import school.dao.SubjectDao;
import school.dao.TeacherDao;
import school.entity.School;
import school.entity.Teacher;
import schools.controller.model.SchoolData;
import schools.controller.model.TeacherData;

@Service 
public class SchoolService {
	
	@Autowired
	private SchoolDao schoolDao;
	
	@Autowired
	private TeacherDao teacherDao; 
	
	@Autowired
	private SubjectDao subjectDao; 

	@Transactional(readOnly = false)
	public SchoolData saveSchool(SchoolData schoolData) {
		Long schoolId = schoolData.getSchoolId();
		School school = findOrCreateSchool(schoolId); 
		
		setFieldsInSchool(school, schoolData); // school is destination, school data is source
		return new SchoolData(schoolDao.save(school)); 
	}
	
	private void setFieldsInSchool(School school, SchoolData schoolData) {
	school.setSchoolName(schoolData.getSchoolName());
	school.setSchoolType(schoolData.getSchoolType());	
		
	}

	private School findOrCreateSchool(Long schoolId) {
		School school; 
		
		if(Objects.isNull(schoolId)) {
			school = new School(); 
		}
		else {
			school = findSchoolById(schoolId); 
		}
		
		return school; 
	}

	private School findSchoolById(Long schoolId) {
		return schoolDao.findById(schoolId).orElseThrow(() -> new NoSuchElementException("School with ID = " + schoolId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<SchoolData> retrieveAllSchools() {
		// @ fortmatter:off
		return schoolDao.findAll()
		.stream()
		.map(SchoolData::new)
		.toList();
		// @formatter: on 
	}
	
	@Transactional(readOnly = true)
	public SchoolData retrieveSchoolById(Long schoolId) {
		School school = findSchoolById(schoolId);
		return new SchoolData(school); 
	}

	@Transactional(readOnly = false)
	public TeacherData saveTeacher(TeacherData teacherData) {
		Long teacherId = teacherData.getTeacherId();
		Teacher teacher = findOrCreateTeacher(teacherId); 
		
		setFieldsInTeacher(teacher, teacherData); // teacher is destination, teacherdata is source
		
		return new TeacherData(teacherDao.save(teacher)); 
	}
	
	private Teacher findOrCreateTeacher(Long teacherId) {
		Teacher teacher;  
		
		if(Objects.isNull(teacherId)) {
			teacher = new Teacher(); 
		}
		else {
			teacher = findTeacherById(teacherId); 
		}
		
		return teacher; 
	}

	private Teacher findTeacherById(Long teacherId) {
		return teacherDao.findById(teacherId).orElseThrow(() -> new NoSuchElementException("Teacher with ID = " + teacherId + " was not found."));
	}

	private void setFieldsInTeacher(Teacher teacher, TeacherData teacherData) {
		teacher.setTeacherFirstName(teacherData.getTeacherFirstName());
		teacher.setTeacherLastName(teacherData.getTeacherLastName());
	}

	public List<TeacherData> retrieveAllTeachers() {
		// @ fortmatter:off
				return teacherDao.findAll()
				.stream()
				.map(TeacherData::new)
				.toList();
				// @formatter: on 
	}

	public TeacherData retrieveTeacherById(Long teacherId) {
		Teacher teacher = findTeacherById(teacherId);
		return new TeacherData(teacher); 
	}

	@Transactional(readOnly = false)
	public void deleteTeacherById(Long teacherId) {
		Teacher teacher = findTeacherById(teacherId);
		teacherDao.delete(teacher); 
	}
}
