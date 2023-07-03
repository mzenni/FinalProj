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
import school.entity.School;
import school.service.SchoolService;
import schools.controller.model.SchoolData;

@RestController
@RequestMapping("/school")
@Slf4j
public class SchoolController {

	@Autowired
	private SchoolService schoolService;

	// Create School
	@PostMapping("/school")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData saveSchool(@RequestBody SchoolData schoolData) {
		log.info("Creating school {}", schoolData);
		return schoolService.saveSchool(schoolData);
	}

	// read all school
	@GetMapping("/school")
	public List<SchoolData> retrieveAllSchools() {
		log.info("Retrieve all schools called.");
		return schoolService.retrieveAllSchools();
	}

	@GetMapping("/school/{schoolId}")
	public School retrieveSchoolById(@PathVariable Long schoolId) { // is SchoolData the return value or just School? check pet store example 
		log.info("Retrieving school with ID = {} ", schoolId);
		return schoolService.retrieveSchoolById(schoolId);
	}

	// Teacher
	@PostMapping("/{schoolId}/teacher")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData.SchoolTeacher insertTeacher(@PathVariable("schoolId") Long schoolId, @RequestBody SchoolData.SchoolTeacher schoolTeacher) {
		log.info("Creating teacher {}", schoolTeacher);
		return schoolService.saveTeacher(schoolId, schoolTeacher);
	}
	
	@PutMapping("/teacher/{teacherId}")
	public SchoolData.SchoolTeacher updateTeacher(@PathVariable Long schoolId, @PathVariable Long teacherId,
			@RequestBody SchoolData.SchoolTeacher schoolTeacher) {
		schoolTeacher.setTeacherId(teacherId);
		log.info("Updating teacher {}", schoolTeacher);
		return schoolService.saveTeacher(schoolId, schoolTeacher);
	}
	
	@GetMapping("/teacher")
	public List<SchoolData.SchoolTeacher> retrieveAllTeachers() {
		log.info("Retrieve all teachers called.");
		return schoolService.retrieveAllTeachers();
	}

	
	@GetMapping("/teacher/{teacherId}")
	public schools.controller.model.SchoolData.SchoolTeacher retrieveTeacherById(@PathVariable Long schoolId, @PathVariable Long teacherId) {
		log.info("Retrieving teacher with ID = {} ", teacherId);
		return schoolService.retrieveTeacherById(schoolId, teacherId); 
	}
	
	@DeleteMapping("/teacher")
	public void deleteAllTeachers() {
		log.info("attemping to delete all teachers.");
		throw new UnsupportedOperationException("Deleting all teachers is not allowed.");
	}

	@DeleteMapping("/{schoolId}/teacher/{teacherId}")
	public Map<String, String> deleteTeacherById(@PathVariable Long schoolId, @PathVariable Long teacherId) {
		log.info("Deleting teacher with ID + {} ", teacherId);
		schoolService.deleteTeacherById(schoolId, teacherId);
		return Map.of("message", "Deletion of teacher with ID = " + teacherId + " was successful.");
	}

	// Subject

	@PostMapping("/{schoolId}/subject")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData.SchoolSubject saveSubject(@PathVariable Long schoolId, @RequestBody schools.controller.model.SchoolData.SchoolSubject schoolSubject) {
		log.info("Creating subject {}", schoolSubject);
		return schoolService.saveSubject(schoolId, schoolSubject);
	}

	@PutMapping("/subject/{subjectId}")
	public schools.controller.model.SchoolData.SchoolSubject updateSubject(@PathVariable Long schoolId, @PathVariable Long subjectId,
			@RequestBody schools.controller.model.SchoolData.SchoolSubject schoolSubject) {
		schoolSubject.setSubjectId(subjectId);
		log.info("Updating teacher {}", schoolSubject);
		return schoolService.saveSubject(subjectId, schoolSubject);
	}

	@GetMapping("/subject")
	public List<schools.controller.model.SchoolData.SchoolSubject> retrieveAllSubjects() {
		log.info("Retrieve all subjects called.");
		return schoolService.retrieveAllSubjects();
	}

	@GetMapping("/subject/{subjectId}")
	public schools.controller.model.SchoolData.SchoolSubject retrieveSubjectById(@PathVariable Long schoolId, @PathVariable Long subjectId) {
		log.info("Retrieving subject with ID = {} ", subjectId);
		return schoolService.retrieveSubjectById(schoolId, subjectId);
	}
}

//@GetMapping("/teacher/{teacherId}")
//public SchoolTeacher retrieveTeacherById(@PathVariable Long schoolId, @PathVariable Long teacherId) {
//	log.info("Retrieving teacher with ID = {} ", teacherId);
//	return schoolService.retrieveTeacherById(schoolId, teacherId);
//}
//@GetMapping("/teacher/{teacherId}")
//public SchoolData.SchoolTeacher retrieveTeacherById(@PathVariable Long schoolId, @PathVariable Long teacherId) {
//    log.info("Retrieving teacher with ID = {} ", teacherId);
//    return schoolService.retrieveTeacherById(schoolId, teacherId);
//}
//
//@GetMapping("/teacher/{teacherId}")
//public School retrieveTeacherById(@PathVariable Long schoolId, @PathVariable Long teacherId){
//	log.info("Retrieve teacher by ID called.");
//	return schoolService.retrieveTeacherById(teacherId); 
//}
//
