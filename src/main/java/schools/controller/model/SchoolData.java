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

	private Set<SchoolTeacher> teachers = new HashSet<>();
	private Set<SchoolSubject> subjects = new HashSet<>();

	public SchoolData(School school) {
		schoolId = school.getSchoolId();
		schoolName = school.getSchoolName();
		schoolType = school.getSchoolType();

		for (Subject subject : school.getSubjects()) {
			subjects.add(new SchoolSubject(subject));
		}

		for (Teacher teacher : school.getTeachers()) {
			teachers.add(new SchoolTeacher(teacher));
		}
	}

	@Data
	@NoArgsConstructor
	public static class SchoolSubject {
		private Long subjectId;
		private String subjectName;
		private String subjectLevel;

		public SchoolSubject(Subject subject) {
			subjectId = subject.getSubjectId();
			subjectName = subject.getSubjectName();
			subjectLevel = subject.getSubjectLevel();
		}
	}

	@Data
	@NoArgsConstructor
	public static class SchoolTeacher {
		private Long teacherId;
		private String teacherFirstName;
		private String teacherLastName;
		private String teacherEmail;

		public SchoolTeacher(Teacher teacher) {
			teacherId = teacher.getTeacherId();
			teacherFirstName = teacher.getTeacherFirstName();
			teacherLastName = teacher.getTeacherLastName();
			teacherEmail = teacher.getTeacherEmail();
		}
	}
}
