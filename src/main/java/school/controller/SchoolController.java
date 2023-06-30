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
import school.service.SchoolService;
import schools.controller.model.SchoolData;
import schools.controller.model.TeacherData;

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
	public List<SchoolData> retrieveAllSchools(){
		log.info("Retrieve all schools called.");
		return schoolService.retrieveAllSchools();
	}
	
	@GetMapping("/school/{schoolId}")
	public SchoolData retrieveSchoolById(@PathVariable Long schoolId) {
		log.info("Retrieving school with ID = {} ", schoolId);
		return schoolService.retrieveSchoolById(schoolId); 
	}
	
	// Teacher
	
	@PostMapping("/teacher")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeacherData saveTeacher(@RequestBody TeacherData teacherData) {
	//public TeacherData insertTeacher(@RequestBody TeacherData teacherData) {
		log.info("Creating teacher {}", teacherData);
		return schoolService.saveTeacher(teacherData); 
	}
	
	@PutMapping("/teacher/{teacherId}")
	public TeacherData updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherData teacherData) {
		teacherData.setTeacherId(teacherId); 
		log.info("Updating teacher {}", teacherData); 
		return schoolService.saveTeacher(teacherData);
	}
	
	@GetMapping("/teacher")
	public List<TeacherData> retrieveAllTeachers(){
		log.info("Retrieve all teachers called.");
		return schoolService.retrieveAllTeachers();
	}
	
	@GetMapping("/teacher/{teacherId}")
	public TeacherData retrieveTeacherById(@PathVariable Long teacherId) {
		log.info("Retrieving teacher with ID = {} ", teacherId);
		return schoolService.retrieveTeacherById(teacherId); 
	}
	
	@DeleteMapping("/teacher")
	public void deleteAllTeachers() {
		log.info("attemping to delete all teachers.");
		throw new UnsupportedOperationException("Deleting all teachers is not allowed."); 
	}
	
	@DeleteMapping("/teacher/{teacherId}")
	public Map<String, String> deleteTeacherById(@PathVariable Long teacherId){
		log.info("Deleting teacher with ID + {} ", teacherId);
		
		schoolService.deleteTeacherById(teacherId);
		
		return Map.of("message", "Deletion of teacher with ID = " + teacherId + " was successful.");
	} 
	
}
