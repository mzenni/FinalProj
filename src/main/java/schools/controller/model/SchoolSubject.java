package schools.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchoolSubject {
	
	private Long subjectId;
	private String subjectName;
	private String subjectLevel;
	
	// DIDN'T WANT TO DELETE, EVERYTHING BELOW IS FROM BEFORE 7/3
//	private Long subjectId;
//	private String subjectName;
//	private String subjectLevel;
//	private Set<String> teachers = new HashSet<>();
//
//	public SchoolSubject(Subject subject) {
//		subjectId = subject.getSubjectId();
//		subjectName = subject.getSubjectName();
//		subjectLevel = subject.getSubjectLevel();
//		
//		/* for(Teacher teacher : subject.getTeachers()) {
//			teachers.addAll((Collection<? extends String>) new TeacherResponse(teacher));
//		} */
//	}
//	@Data
//	@NoArgsConstructor
//	public static class SubjectSchool{
//		private Long schoolId;
//		private String schoolName;
//		private String schoolType;
//		
//		public SubjectSchool(School school) {
//			schoolId = school.getSchoolId();
//			schoolName = school.getSchoolName();
//			schoolType = school.getSchoolType(); 
//		}
//	}
//	/*
//	@Data
//	@NoArgsConstructor
//	static class TeacherResponse{
//		private Long teacherId;
//		private String teacherFirstName;
//		private String teacherLastName;
//		//private Subject teacherSubject; 
//		//private Set<String> subjects = new HashSet<>(); 
//		
//		TeacherResponse(Teacher teacher){
//			teacherId = teacher.getTeacherId();
//			teacherFirstName = teacher.getTeacherFirstName();
//			teacherLastName = teacher.getTeacherLastName();
//			//teacherSubject = teacher.getTeacherSubject();
//		}
//	} */ 
//	
}
