package schools.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // creates getters and setters for us
@NoArgsConstructor
public class SchoolTeacher {
	
	// DIDN'T WANT TO DELETE, EVERYTHING BELOW IS FROM BEFORE 7/3
	private Long teacherId;
	private String teacherFirstName;
	private String teacherLastName;
	private String teacherEmail; 		// don't forget to add email if you change this
	
//	private Long teacherId;
//	private String teacherFirstName;
//	private String teacherLastName;
//	private SchoolData schoolData; 
//	// private Long schoolId; 
//	
//	public SchoolTeacher(Teacher teacher) {
//		teacherId = teacher.getTeacherId();
//		teacherFirstName = teacher.getTeacherFirstName();
//		teacherLastName = teacher.getTeacherLastName();
//		
//		if(teacher.getSchool() != null) {
//			schoolData = new SchoolData(teacher.getSchool());
//		}
//	}
//	
//	@Data
//	@NoArgsConstructor
//	public static class TeacherSchool{
//		private Long schoolId;
//		private String schoolName;
//		private String schoolType;
//		
//		public TeacherSchool(School school) {
//			schoolId = school.getSchoolId();
//			schoolName = school.getSchoolName();
//			schoolType = school.getSchoolType(); 
//		}
//	}
//	/*
//	@Data
//	@NoArgsConstructor
//	public static class TeacherSubject{
//		private Long subjectId;
//		private String subjectName;
//		private String subjectLevel;
//		
//		public TeacherSubject(Subject subject) {
//			subjectId = subject.getSubjectId();
//			subjectName = subject.getSubjectName();
//			subjectLevel = subject.getSubjectLevel(); 
//		}
//	} */
}