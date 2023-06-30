package schools.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import school.entity.School;
import school.entity.Subject;
import school.entity.Teacher;

@Data
@NoArgsConstructor
public class SchoolData {
	
	private Long schoolId;
	private String schoolName;
	private String schoolType; // public, private, religious, charter, vocational

	private Set<TeacherResponse> teachers = new HashSet<>(); 
	
	public SchoolData(School school) {
		schoolId = school.getSchoolId();
		schoolName = school.getSchoolName();
		schoolType = school.getSchoolType();
		
		for(Teacher teacher : school.getTeachers()) {
			teachers.add(new TeacherResponse(teacher));
		}
		
		
	} 
	
	@Data
	@NoArgsConstructor
	static class TeacherResponse{
		private Long teacherId;
		private String teacherFirstName;
		private String teacherLastName;
		//private Subject teacherSubject; 
		private Set<String> subjects = new HashSet<>(); 
		
		TeacherResponse(Teacher teacher){
			teacherId = teacher.getTeacherId();
			teacherFirstName = teacher.getTeacherFirstName();
			teacherLastName = teacher.getTeacherLastName();
			//teacherSubject = teacher.getTeacherSubject();
		}
	}
	
}
