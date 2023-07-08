package school.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import school.entity.Subject;
import school.entity.Teacher;
import school.service.SchoolService;
import schools.controller.model.SchoolData;
import schools.controller.model.SchoolData.SchoolSubject;

@RestController
@RequestMapping("/school")
@Slf4j
public class SchoolController {

	@Autowired
	private SchoolService schoolService;

	/* 												SCHOOL 											*/
	// Create School
	@PostMapping("/school")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData saveSchool(@RequestBody SchoolData schoolData) {
		log.info("Creating school {}", schoolData);
		return schoolService.saveSchool(schoolData);
	}

	// Read all schools
	@GetMapping("/school")
	public List<SchoolData> retrieveAllSchools() {
		log.info("Retrieve all schools called.");
		return schoolService.retrieveAllSchools();
	}

	// Read school by ID
	@GetMapping("/school/{schoolId}")
	public SchoolData retrieveSchoolById(@PathVariable Long schoolId) {
		log.info("Retrieving school with ID = {} ", schoolId);
		return schoolService.retrieveSchoolById(schoolId);
	}
	

	// Update school
	@PutMapping("/school/{schoolId}")
	public SchoolData updateSchool(@PathVariable Long schoolId, @RequestBody SchoolData schoolData) {
		schoolData.setSchoolId(schoolId);
		log.info("Updating school {}", schoolData);
		return schoolService.updateSchool(schoolId, schoolData);
	}
	
	// Delete school by ID 
		@DeleteMapping("/{schoolId}")
		public Map<String, String> deleteSchoolById(@PathVariable Long schoolId) {
			log.info("Deleting school with ID {} = ", schoolId);
			schoolService.deleteSchoolById(schoolId);
			return Map.of("message", "Deletion of subject with ID = " + schoolId + " was successful.");
		}

	/* 												TEACHER 											*/
	// Create Teacher
	@PostMapping("/{schoolId}/teacher")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData.SchoolTeacher insertTeacher(@PathVariable("schoolId") Long schoolId,
			@RequestBody SchoolData.SchoolTeacher schoolTeacher) {
		log.info("Creating teacher {}", schoolTeacher);
		return schoolService.saveTeacher(schoolId, schoolTeacher);
	}

	// Read all teachers
	@GetMapping("/teacher")
	public List<SchoolData.SchoolTeacher> retrieveAllTeachers() {
		log.info("Retrieve all teachers called.");
		return schoolService.retrieveAllTeachers();
	}

	// Read teacher by ID 
	@GetMapping("/teacher/{teacherId}")
	public Teacher retrieveTeacherById(@PathVariable Long teacherId) {
		log.info("Retrieving teacher with ID = {} ", teacherId);
		return schoolService.retrieveTeacherById(teacherId);
	}

	// Update teacher
	@PutMapping("/teacher/{teacherId}")
	public SchoolData.SchoolTeacher updateTeacher(@PathVariable Long teacherId,
			@RequestBody SchoolData.SchoolTeacher schoolTeacher) {
		schoolTeacher.setTeacherId(teacherId);
		log.info("Updating teacher {}", schoolTeacher);
		return schoolService.updateTeacher(teacherId, schoolTeacher);
	}

	// Delete all teachers. Returns exception
	@DeleteMapping("/teacher")
	public void deleteAllTeachers() {
		log.info("attemping to delete all teachers.");
		throw new UnsupportedOperationException("Deleting all teachers is not allowed.");
	}

	// Delete teacher by ID
	@DeleteMapping("/teacher/{teacherId}")
	public Map<String, String> deleteTeacherById(@PathVariable Long teacherId) {
		log.info("Deleting teacher with ID + {} ", teacherId);
		schoolService.deleteTeacherById(teacherId);
		return Map.of("message", "Deletion of teacher with ID = " + teacherId + " was successful.");
	}

	
	/* 												SUBJECT 											*/
	// Create subject
	@PostMapping("/{schoolId}/subject")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData.SchoolSubject saveSubject(@PathVariable Long schoolId, @RequestBody SchoolSubject schoolSubject) {
		log.info("Creating subject {}", schoolSubject);
		return schoolService.saveSubject(schoolId, schoolSubject);
	}

	// Update subject
	@PutMapping("subject/{subjectId}")
	public SchoolData.SchoolSubject updateSubject(@PathVariable Long subjectId,
			@RequestBody SchoolData.SchoolSubject schoolSubject) {
		schoolSubject.setSubjectId(subjectId);
		log.info("Updating subject {}", schoolSubject);
		return schoolService.updateSubject(subjectId, schoolSubject);
	}

	// Get all subjects
	@GetMapping("/subject")
	public List<schools.controller.model.SchoolData.SchoolSubject> retrieveAllSubjects() {
		log.info("Retrieve all subjects called.");
		return schoolService.retrieveAllSubjects();
	}

	// Get subjects by ID
	@GetMapping("/subject/{subjectId}")
	public Subject retrieveSubjectById(@PathVariable Long subjectId) {
		log.info("Retrieving subject with ID = {} ", subjectId);
		return schoolService.retrieveSubjectById(/* schoolId, */subjectId);
	}
}