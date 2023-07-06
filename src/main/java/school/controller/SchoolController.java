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
	public SchoolData retrieveSchoolById(@PathVariable Long schoolId) { // is SchoolData the return value or just School?
																	// check pet store example
		log.info("Retrieving school with ID = {} ", schoolId);
		return schoolService.retrieveSchoolById(schoolId);
	}

	// Teacher
	@PostMapping("/{schoolId}/teacher")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData.SchoolTeacher insertTeacher(@PathVariable("schoolId") Long schoolId,
			@RequestBody SchoolData.SchoolTeacher schoolTeacher) {
		log.info("Creating teacher {}", schoolTeacher);
		return schoolService.saveTeacher(schoolId, schoolTeacher);
	}

	@PutMapping("/teacher/{teacherId}")
	public SchoolData.SchoolTeacher updateTeacher(@PathVariable Long teacherId,
			@RequestBody SchoolData.SchoolTeacher schoolTeacher) {
		schoolTeacher.setTeacherId(teacherId);
		log.info("Updating teacher {}", schoolTeacher);
//		return schoolService.saveTeacher(schoolTeacher);
		return schoolService.updateTeacher(teacherId, schoolTeacher);
	}
	
//	@PutMapping("/pet_store/{petStoreId}")
//	public PetStoreData updatePetStoreData(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
//		petStoreData.setPetStoreId(petStoreId);
//		log.info("Update pet store {} for ID=", petStoreData);
//		return petStoreService.savePetStore(petStoreData);
//	}
//	
//	@PutMapping("/teacher/{teacherId}")
//	public SchoolData.SchoolTeacher updateTeacher(@PathVariable Long schoolId, @PathVariable Long teacherId,
//			@RequestBody SchoolData.SchoolTeacher schoolTeacher) {
//		schoolTeacher.setTeacherId(teacherId);
//		log.info("Updating teacher {}", schoolTeacher);
//		return schoolService.saveTeacher(schoolId, schoolTeacher);
//	}


	@GetMapping("/teacher")
	public List<SchoolData.SchoolTeacher> retrieveAllTeachers() {
		log.info("Retrieve all teachers called.");
		return schoolService.retrieveAllTeachers();
	}

	@GetMapping("/teacher/{teacherId}")
	public Teacher retrieveTeacherById(@PathVariable Long teacherId) {
		log.info("Retrieving teacher with ID = {} ", teacherId);
		return schoolService.retrieveTeacherById(teacherId);
	}
	
	@DeleteMapping("/teacher")
	public void deleteAllTeachers() {
		log.info("attemping to delete all teachers.");
		throw new UnsupportedOperationException("Deleting all teachers is not allowed.");
	}

	@DeleteMapping("/teacher/{teacherId}")
	public Map<String, String> deleteTeacherById(@PathVariable Long teacherId) {
		log.info("Deleting teacher with ID + {} ", teacherId);
		schoolService.deleteTeacherById(teacherId);
		return Map.of("message", "Deletion of teacher with ID = " + teacherId + " was successful.");
	}
//	@DeleteMapping("/{schoolId}/teacher/{teacherId}")
//	public Map<String, String> deleteTeacherById(@PathVariable Long schoolId, @PathVariable Long teacherId) {
//		log.info("Deleting teacher with ID + {} ", teacherId);
//		schoolService.deleteTeacherById(schoolId, teacherId);
//		return Map.of("message", "Deletion of teacher with ID = " + teacherId + " was successful.");
//	}

	// Subject
	@PostMapping("/{schoolId}/subject")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData.SchoolSubject saveSubject(@PathVariable Long schoolId, @RequestBody SchoolSubject schoolSubject) {
		log.info("Creating subject {}", schoolSubject);
		return schoolService.saveSubject(schoolId, schoolSubject);
	}

	@PutMapping("subject/{subjectId}")
	public SchoolData.SchoolSubject updateSubject(@PathVariable Long subjectId,
			@RequestBody SchoolData.SchoolSubject schoolSubject) {
		schoolSubject.setSubjectId(subjectId);
		log.info("Updating subject {}", schoolSubject);
		return schoolService.updateSubject(subjectId, schoolSubject);
	}

	
//	@PutMapping("/teacher/{teacherId}")
//	public SchoolData.SchoolTeacher updateTeacher(@PathVariable Long teacherId,
//			@RequestBody SchoolData.SchoolTeacher schoolTeacher) {
//		schoolTeacher.setTeacherId(teacherId);
//		log.info("Updating teacher {}", schoolTeacher);
//		return schoolService.updateTeacher(teacherId, schoolTeacher);
//	}
	

//	@PutMapping("/subject/{subjectId}")
//	public SchoolSubject updateSubject(@PathVariable Long schoolId, @PathVariable Long subjectId, @RequestBody SchoolSubject schoolSubject) {
//		schoolSubject.setSubjectId(subjectId);
//		log.info("Updating subject {}", schoolSubject);
//		return schoolService.saveSubject(schoolSubject);
//	}

	@GetMapping("/subject")
	public List<schools.controller.model.SchoolData.SchoolSubject> retrieveAllSubjects() {
		log.info("Retrieve all subjects called.");
		return schoolService.retrieveAllSubjects();
	}

	@GetMapping("/subject/{subjectId}")
	public Subject retrieveSubjectById(/*@PathVariable Long schoolId,*/
			@PathVariable Long subjectId) {
		log.info("Retrieving subject with ID = {} ", subjectId);
		return schoolService.retrieveSubjectById(/*schoolId, */subjectId);
	}
	
	// school_subject join table
//	@GetMapping("/school_subject")
//	public List<SchoolSubject> retrieveAllJoinTable(){
//		log.info("Retrieve all school subjects called."); 
//		return schoolService.retrieveAllJoinTable();
//	}
	
	//CURRENT 12:12
//	@GetMapping("/school_subject")
//	public List<SchoolData> retrieveAllJoinTable(){
//		log.info("Retrieve school subject join table called.");
//		return schoolService.retrieveAllJoinTable(); 
//	}
	  
	  
//	  @GetMapping("/school_subject")
//	    public List<Object[]> getAllSchoolSubjects() {
//	        return schoolSubjectDao.getAllSchoolSubjects();
//	    }
	
	// Get subjects by school 
//	@GetMapping("/school/({schoolId}/subject")
//	public Set<Subject> retrieveSubjectsBySchoolId(@PathVariable Long schoolId){
//		School school = schoolService.findSchoolById(schoolId);
//		Set<Subject> subjects = school.getSubjects(); 
//		return subjects; 
//	}
	
//	@GetMapping("/schoolsubject")
//	public List<schools.controller.model.SchoolData.SchoolSubject> retrieveAllSubjects() {
//		log.info("Retrieve all subjects called.");
//		return schoolService.retrieveAllSubjects();
//	}
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
