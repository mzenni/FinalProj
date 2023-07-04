package school.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import school.dao.SchoolDao;
import school.dao.SubjectDao;
import school.dao.TeacherDao;
import school.entity.School;
import school.entity.Subject;
import school.entity.Teacher;
import schools.controller.model.SchoolData;
import schools.controller.model.SchoolData.SchoolSubject;
import schools.controller.model.SchoolData.SchoolTeacher;

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
		School school;
		// = findOrCreateSchool(schoolData.getSchoolId());

		if (schoolId == null) {
			school = findOrCreateSchool(schoolId);
		} else {
			school = findSchoolById(schoolId);
			if (school == null) {
				throw new NoSuchElementException("School with ID = " + schoolId + " is not found.");
			}
		}

		copySchoolFields(school, schoolData);
		return new SchoolData(schoolDao.save(school));

	}

	// setFields in Contributor method guide I
	private void copySchoolFields(School school, SchoolData schoolData) {
		// Example:
		// contributor.setContributorEmail(contributorData.getContributorEmail());
		school.setSchoolId(schoolData.getSchoolId());
		school.setSchoolName(schoolData.getSchoolName());
		school.setSchoolType(schoolData.getSchoolType());
	}

	private School findOrCreateSchool(Long schoolId) {
		School school;

		if (Objects.isNull(schoolId)) {

			school = new School();

		} else {
			school = findSchoolById(schoolId);
		}
		return school;
	}

	private School findSchoolById(Long schoolId) {
		return schoolDao.findById(schoolId).orElse(null);
	}
	@Transactional(readOnly = false)
	public SchoolTeacher updateTeacher(Long teacherId, SchoolTeacher schoolTeacher) {
		Teacher teacher =  teacherDao.findById(teacherId)
				.orElseThrow(() -> new NoSuchElementException("Teacher with ID = " + teacherId + " was not found"));
		
		copyTeacherFields(teacher, schoolTeacher);
		
		Teacher dbTeacher = teacherDao.save(teacher);
		return new SchoolTeacher(dbTeacher); 
	}
	
	@Transactional(readOnly = false)
	public SchoolSubject updateSubject(Long subjectId, SchoolSubject schoolSubject) {
		Subject subject =  subjectDao.findById(subjectId)
				.orElseThrow(() -> new NoSuchElementException("Subject with ID = " + subjectId + " was not found"));
		
		copySubjectFields(subject, schoolSubject);
		
		Subject dbSubject = subjectDao.save(subject);
		return new SchoolSubject(dbSubject); 
	}
	
	@Transactional(readOnly = false)
	public SchoolTeacher saveTeacher(Long schoolId, SchoolTeacher schoolTeacher) {
		School school = findSchoolById(schoolId);
		Long teacherId = schoolTeacher.getTeacherId();
		Teacher teacher = findOrCreateTeacher(schoolId, schoolTeacher.getTeacherId());

		copyTeacherFields(teacher, schoolTeacher);

		teacher.setSchool(school);
		school.getTeachers().add(teacher);

		Teacher dbTeacher = teacherDao.save(teacher);
		return new SchoolTeacher(dbTeacher);
	}
	
	
//	@Transactional(readOnly = false)
//	public SchoolTeacher updateTeacher(Long schoolId, SchoolTeacher schoolTeacher) {
//		Long teacherId = schoolTeacher.getTeacherId();
//		Teacher teacher = findOrCreateTeacher(schoolId, schoolTeacher.getTeacherId());
//
//		copyTeacherFields(teacher, schoolTeacher);
//
//		teacher.setSchool(school);
//		school.getTeachers().add(teacher);
//
//		Teacher dbTeacher = teacherDao.save(teacher);
//		return new SchoolTeacher(dbTeacher);
//	}
	
	
	private Teacher findOrCreateTeacher(Long schoolId, Long teacherId) {
		Teacher teacher;

		if (Objects.isNull(teacherId)) {
			teacher = new Teacher();
		} else {
			teacher = findTeacherById(schoolId, teacherId);
		}

		return teacher;
	}

	private Teacher findTeacherById(Long schoolId, Long teacherId) {
		// Note that findById returns an Optional. If the Optional is
		// empty .orElseThrow throws a NoSuchElementException. If the
		// Optional is not empty a Teacher is returned.
		Teacher teacher = teacherDao.findById(teacherId)
				.orElseThrow(() -> new NoSuchElementException("Teacher with ID = " + teacherId + " was not found"));
		if (teacher.getSchool().getSchoolId().equals(schoolId)) {
			return teacher;
		} else {
			throw new IllegalArgumentException("Invalid School ID for the teacher.");
		}
	}

	private void copyTeacherFields(Teacher teacher, SchoolTeacher schoolTeacher) {
		teacher.setTeacherId(schoolTeacher.getTeacherId());
		teacher.setTeacherFirstName(schoolTeacher.getTeacherFirstName());
		teacher.setTeacherLastName(schoolTeacher.getTeacherLastName());
		teacher.setTeacherEmail(schoolTeacher.getTeacherEmail());
	}
	@Transactional(readOnly = false)
	public SchoolSubject saveSubject(Long schoolId, SchoolSubject schoolSubject) {
		School school = findSchoolById(schoolId);
		Subject subject = findOrCreateSubject(schoolId, schoolSubject.getSubjectId());

		copySubjectFields(subject, schoolSubject);

		Set<School> schools = new HashSet<>();
		schools.add(school);

		subject.setSchools(schools);
		school.getSubjects().add(subject);

		return new SchoolSubject(subjectDao.save(subject));
	}

	private Subject findSubjectById(Long schoolId, Long subjectId) {
		// Note that findById returns an Optional. If the Optional is
		// empty .orElseThrow throws a NoSuchElementException. If the
		// Optional is not empty an Customer is returned.
		Optional<Subject> subjectOptional = subjectDao.findById(subjectId);
		Subject subject = subjectOptional
				.orElseThrow(() -> new NoSuchElementException("Subject with ID = " + subjectId + " was not found"));
		if (subject.getSchools().stream().anyMatch(p -> p.getSchoolId().equals(schoolId))) {
			return subject;
		} else {
			throw new IllegalArgumentException("Invalid school ID for the subject.");
		}
	}

	private Subject findOrCreateSubject(Long schoolId, Long subjectId) {
		Subject subject;

		if (Objects.isNull(subjectId)) {
			subject = new Subject();
		} else {
			subject = findSubjectById(schoolId, subjectId);
		}

		return subject;
	}

	private void copySubjectFields(Subject subject, SchoolSubject schoolSubject) {
		subject.setSubjectId(schoolSubject.getSubjectId());
		subject.setSubjectName(schoolSubject.getSubjectName());
		subject.setSubjectLevel(schoolSubject.getSubjectLevel());
	}

	@Transactional(readOnly = true)
	public List<SchoolData> retrieveAllSchools() {
		List<School> schools = schoolDao.findAll();
		List<SchoolData> result = new LinkedList<>();

		for (School school : schools) {
			SchoolData sD = new SchoolData(school);

			sD.getSubjects().clear();
			sD.getTeachers().clear();

			result.add(sD);
		}
		return result;
	}

	@Transactional(readOnly = true)
	public School retrieveSchoolById(Long schoolId) {
		return schoolDao.findById(schoolId)
				.orElseThrow(() -> new NoSuchElementException("School with ID = " + schoolId + " does not exist."));
	}

	public void deleteSchoolById(Long schoolId) {
		School school = findSchoolById(schoolId);
		schoolDao.delete(school);

	}

	@Transactional(readOnly = true)
	public List<SchoolTeacher> retrieveAllTeachers() {
		// @ fortmatter:off
		return teacherDao.findAll().stream().map(SchoolTeacher::new).toList();
		// @formatter: on
	}

	/*@Transactional(readOnly = true)
	public SchoolTeacher retrieveTeacherById(Long schoolId, Long teacherId) {
		Teacher teacher = findTeacherById(schoolId, teacherId);
		return new SchoolTeacher(teacher);
	}*/ 
	
	@Transactional(readOnly = true)
	public Teacher retrieveTeacherById(Long teacherId) {
		return teacherDao.findById(teacherId)
				.orElseThrow(() -> new NoSuchElementException("Teacher with ID = " + teacherId + " does not exist."));
	}

//	public void deleteTeacherById(Long schoolId, Long teacherId) {
//		Teacher teacher = findTeacherById(schoolId, teacherId);
//		teacherDao.delete(teacher);
//	}
	
	public void deleteTeacherById(Long teacherId) {
		 Teacher teacher = retrieveTeacherById(teacherId);
		 teacherDao.delete(teacher);
	}

	@Transactional(readOnly = true)
	public List<SchoolSubject> retrieveAllSubjects() {
		// @ fortmatter:off
		return subjectDao.findAll().stream().map(SchoolSubject::new).toList();
		// @formatter: on
	}

//	@Transactional(readOnly = true)
//	public SchoolSubject retrieveSubjectById(Long schoolId, Long subjectId) {
//		Subject subject = findSubjectById(schoolId, subjectId);
//		return new SchoolSubject(subject);
//	}
	@Transactional(readOnly = true)
	public Subject retrieveSubjectById(Long subjectId) {
		return subjectDao.findById(subjectId)
				.orElseThrow(() -> new NoSuchElementException("Subject with ID = " + subjectId + " does not exist."));
	}
}

// EVERYTHING BELOW WAS FROM JULY 3 OR BEFORE:
//@Service 
//public class SchoolService {
//	
//	@Autowired
//	private SchoolDao schoolDao;
//	
//	@Autowired
//	private TeacherDao teacherDao; 
//	
//	@Autowired
//	private SubjectDao subjectDao; 
//
//	
//	// School
//	@Transactional(readOnly = false)
//	public SchoolData saveSchool(SchoolData schoolData) {
//		Long schoolId = schoolData.getSchoolId();
//		School school = findOrCreateSchool(schoolId); 
//		
//		setFieldsInSchool(school, schoolData); // school is destination, school data is source
//		return new SchoolData(schoolDao.save(school)); 
//	}
//	
//	private void setFieldsInSchool(School school, SchoolData schoolData) {
//	school.setSchoolName(schoolData.getSchoolName());
//	school.setSchoolType(schoolData.getSchoolType());	
//		
//	}
//
//	private School findOrCreateSchool(Long schoolId) {
//		School school; 
//		
//		if(Objects.isNull(schoolId)) {
//			school = new School(); 
//		}
//		else {
//			school = findSchoolById(schoolId); 
//		}
//		
//		return school; 
//	}
//
//	private School findSchoolById(Long schoolId) {
//		return schoolDao.findById(schoolId).orElseThrow(() -> new NoSuchElementException("School with ID = " + schoolId + " was not found."));
//	}
//
//	@Transactional(readOnly = true)
//	public List<SchoolData> retrieveAllSchools() {
//		// @ fortmatter:off
//		return schoolDao.findAll()
//		.stream()
//		.map(SchoolData::new)
//		.toList();
//		// @formatter: on 
//	}
//	
//	@Transactional(readOnly = true)
//	public SchoolData retrieveSchoolById(Long schoolId) {
//		School school = findSchoolById(schoolId);
//		return new SchoolData(school); 
//	}
//
//	
//	// Teacher
//	@Transactional(readOnly = false)
//	public SchoolTeacher saveTeacher(Long schoolId, /*Long subjectId, */ SchoolTeacher schoolTeacher) {
//		School school = findSchoolById(schoolId); 
//		//Subject subject = findSubjectById(schoolId, subjectId); 
//		Long teacherId = schoolTeacher.getTeacherId();
//		Teacher teacher = findOrCreateTeacher(schoolId, /*subjectId, */ teacherId); 
//		
//		setFieldsInTeacher(teacher, schoolTeacher); // teacher is destination, teacherdata is source
//		
//		return new SchoolTeacher(teacherDao.save(teacher)); 
//	}
//	
//	private Teacher findOrCreateTeacher(Long schoolId, /*Long subjectId,*/ Long teacherId) {
//		Teacher teacher;  
//		
//		if(Objects.isNull(teacherId)) {
//			teacher = new Teacher(); 
//		}
//		else {
//			teacher = findTeacherById(schoolId, teacherId); 
//		}
//		
//		return teacher; 
//	}
//
//	private Teacher findTeacherById(Long schoolId, Long teacherId) {
//		return teacherDao.findById(teacherId).orElseThrow(() -> new NoSuchElementException("Teacher with ID = " + teacherId + " was not found."));
//	}
//
//	private void setFieldsInTeacher(Teacher teacher, SchoolTeacher teacherData) {
//		teacher.setTeacherFirstName(teacherData.getTeacherFirstName());
//		teacher.setTeacherLastName(teacherData.getTeacherLastName());
//	}
//
//	public List<SchoolTeacher> retrieveAllTeachers() {
//		// @ fortmatter:off
//				return teacherDao.findAll()
//				.stream()
//				.map(SchoolTeacher::new)
//				.toList();
//				// @formatter: on 
//	}
//
//	public SchoolTeacher retrieveTeacherById(Long schoolId, Long teacherId) {
//		Teacher teacher = findTeacherById(schoolId, teacherId);
//		return new SchoolTeacher(teacher); 
//	}
//
//	@Transactional(readOnly = false)
//	public void deleteTeacherById(Long schoolId, Long teacherId) {
//		Teacher teacher = findTeacherById(schoolId, teacherId);
//		teacherDao.delete(teacher); 
//	}
//	
//	// Subject
//
//	@Transactional(readOnly = false)
//	public SchoolSubject saveSubject(Long schoolId, SchoolSubject schoolSubject) {
//		School school = findSchoolById(schoolId); 
//		Long subjectId = schoolSubject.getSubjectId();
//		Subject subject = findOrCreateSubject(schoolId, subjectId); 
//		
//		setFieldsInSubject(subject, schoolSubject); // teacher is destination, teacherdata is source
//		
//		return new SchoolSubject(subjectDao.save(subject)); 
//	}
//
//	private void setFieldsInSubject(Subject subject, SchoolSubject schoolSubject) {
//		subject.setSubjectName(schoolSubject.getSubjectName());
//		subject.setSubjectLevel(schoolSubject.getSubjectLevel());
//	}
//
//	private Subject findOrCreateSubject(Long schoolId, Long subjectId) {
//		Subject subject;  
//		
//		if(Objects.isNull(subjectId)) {
//			subject = new Subject(); 
//		}
//		else {
//			subject = findSubjectById(schoolId, subjectId); 
//		}
//		
//		return subject; 
//	}
//
//	private Subject findSubjectById(Long schoolId, Long subjectId) {
//		return subjectDao.findById(subjectId).orElseThrow(() -> new NoSuchElementException("Subject with ID = " + subjectId + " was not found."));
//	}
//
//	public List<SchoolSubject> retrieveAllSubjects() {
//		// @ fortmatter:off
//		return subjectDao.findAll()
//		.stream()
//		.map(SchoolSubject::new)
//		.toList();
//		// @formatter: on 
//	}
//
//	public SchoolSubject retrieveSubjectById(Long schoolId, Long subjectId) {
//		Subject subject = findSubjectById(schoolId, subjectId);
//		return new SchoolSubject(subject); 
//	}
//}
