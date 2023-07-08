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
// All service methods in School Service
@Service
public class SchoolService {

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private SubjectDao subjectDao;

	/* 							SCHOOL METHODS 								*/
	// save school method: gets school Id and finds/creates school with ID.
	// throws exception if null
	@Transactional(readOnly = false)
	public SchoolData saveSchool(SchoolData schoolData) {
		Long schoolId = schoolData.getSchoolId();
		School school;

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

	// Copies school data values
	private void copySchoolFields(School school, SchoolData schoolData) {
		school.setSchoolId(schoolData.getSchoolId());
		school.setSchoolName(schoolData.getSchoolName());
		school.setSchoolType(schoolData.getSchoolType());
	}

	// finds school based off ID, or creates school based off ID
	private School findOrCreateSchool(Long schoolId) {
		School school;

		if (Objects.isNull(schoolId)) {

			school = new School();

		} else {
			school = findSchoolById(schoolId);
		}
		return school;
	}

	// Gets all school data
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

	// Gets school data based off school ID
	@Transactional(readOnly = true)
	public SchoolData retrieveSchoolById(Long schoolId) {
		School school = findSchoolById(schoolId);
		return new SchoolData(school);
	}

	// Deletes school by school ID
	public void deleteSchoolById(Long schoolId) {
		School school = findSchoolById(schoolId);
		schoolDao.delete(school);
	}

	// Finds school by ID
	public School findSchoolById(Long schoolId) {
		return schoolDao.findById(schoolId).orElse(null);
	}

	// Updates school
	@Transactional(readOnly = false)
	public SchoolData updateSchool(Long schoolId, SchoolData schoolData) {
		School school = schoolDao.findById(schoolId)
				.orElseThrow(() -> new NoSuchElementException("School with ID = " + schoolId + " was not found."));

		copySchoolFields(school, schoolData);

		School dbSchool = schoolDao.save(school);
		return new SchoolData(dbSchool);
	}

	/* 							TEACHER METHODS 								*/

	// Updates teacher data values
	@Transactional(readOnly = false)
	public SchoolTeacher updateTeacher(Long teacherId, SchoolTeacher schoolTeacher) {
		Teacher teacher = teacherDao.findById(teacherId)
				.orElseThrow(() -> new NoSuchElementException("Teacher with ID = " + teacherId + " was not found"));

		copyTeacherFields(teacher, schoolTeacher);

		Teacher dbTeacher = teacherDao.save(teacher);
		return new SchoolTeacher(dbTeacher);
	}

	// Saves teacher values to database
	@Transactional(readOnly = false)
	public SchoolTeacher saveTeacher(Long schoolId, SchoolTeacher schoolTeacher) {
		School school = findSchoolById(schoolId);
		Long teacherId = schoolTeacher.getTeacherId();
		Teacher teacher = findOrCreateTeacher(schoolId, teacherId);

		copyTeacherFields(teacher, schoolTeacher);

		teacher.setSchool(school);
		school.getTeachers().add(teacher);

		Teacher dbTeacher = teacherDao.save(teacher);
		return new SchoolTeacher(dbTeacher);
	}

	// Finds teacher by ID or creates teacher if teacher does not exist
	private Teacher findOrCreateTeacher(Long schoolId, Long teacherId) {
		Teacher teacher;

		if (Objects.isNull(teacherId)) {
			teacher = new Teacher();
		} else {
			teacher = findTeacherById(schoolId, teacherId);
		}

		return teacher;
	}

	// Finds teacher by ID
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

	// Copies teacher data values
	private void copyTeacherFields(Teacher teacher, SchoolTeacher schoolTeacher) {
		teacher.setTeacherId(schoolTeacher.getTeacherId());
		teacher.setTeacherFirstName(schoolTeacher.getTeacherFirstName());
		teacher.setTeacherLastName(schoolTeacher.getTeacherLastName());
		teacher.setTeacherEmail(schoolTeacher.getTeacherEmail());
	}

	// Gets all teachers
	@Transactional(readOnly = true)
	public List<SchoolTeacher> retrieveAllTeachers() {
		// @ fortmatter:off
		return teacherDao.findAll().stream().map(SchoolTeacher::new).toList();
		// @formatter: on
	}

	// Gets teacher based off teacher ID
	@Transactional(readOnly = true)
	public Teacher retrieveTeacherById(Long teacherId) {
		return teacherDao.findById(teacherId)
				.orElseThrow(() -> new NoSuchElementException("Teacher with ID = " + teacherId + " does not exist."));
	}

	// Deletes teacher based off ID
	public void deleteTeacherById(Long teacherId) {
		Teacher teacher = retrieveTeacherById(teacherId);
		teacherDao.delete(teacher);
	}

	/* 							SUBJECT METHODS 								*/
	// Saves subject
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

	// updates subject
	@Transactional(readOnly = false)
	public SchoolSubject updateSubject(Long subjectId, SchoolSubject schoolSubject) {
		Subject subject = subjectDao.findById(subjectId)
				.orElseThrow(() -> new NoSuchElementException("Subject with ID = " + subjectId + " was not found"));

		copySubjectFields(subject, schoolSubject);

		Subject dbSubject = subjectDao.save(subject);
		return new SchoolSubject(dbSubject);
	}

	// Finds subject by Subject ID
	private Subject findSubjectById(Long schoolId, Long subjectId) {
		Optional<Subject> subjectOptional = subjectDao.findById(subjectId);
		Subject subject = subjectOptional
				.orElseThrow(() -> new NoSuchElementException("Subject with ID = " + subjectId + " was not found"));
		if (subject.getSchools().stream().anyMatch(p -> p.getSchoolId().equals(schoolId))) {
			return subject;
		} else {
			throw new IllegalArgumentException("Invalid school ID for the subject.");
		}
	}

	// Finds subject or creates subject if it doesn't exist
	private Subject findOrCreateSubject(Long schoolId, Long subjectId) {
		Subject subject;

		if (Objects.isNull(subjectId)) {
			subject = new Subject();
		} else {
			subject = findSubjectById(schoolId, subjectId);
		}

		return subject;
	}

	// copies subject data values
	private void copySubjectFields(Subject subject, SchoolSubject schoolSubject) {
		subject.setSubjectId(schoolSubject.getSubjectId());
		subject.setSubjectName(schoolSubject.getSubjectName());
		subject.setSubjectLevel(schoolSubject.getSubjectLevel());
	}

	// Gets all subjects
	@Transactional(readOnly = true)
	public List<SchoolSubject> retrieveAllSubjects() {
		// @ fortmatter:off
		return subjectDao.findAll().stream().map(SchoolSubject::new).toList();
		// @formatter: on
	}

	// Gets subject by subject ID
	@Transactional(readOnly = true)
	public Subject retrieveSubjectById(Long subjectId) {
		return subjectDao.findById(subjectId)
				.orElseThrow(() -> new NoSuchElementException("Subject with ID = " + subjectId + " does not exist."));
	}

	// Deletes subject by subject Id
	public void deleteSubjectById(Long subjectId) {
		Subject subject = retrieveSubjectById(subjectId);
		subjectDao.delete(subject);
	}
}