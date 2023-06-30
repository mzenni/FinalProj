package schools.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import school.entity.Teacher;

@Data // creates getters and setters for us
@NoArgsConstructor
public class TeacherData {
	private Long teacherId;
	private String teacherFirstName;
	private String teacherLastName;
	
	public TeacherData(Teacher teacher) {
		teacherId = teacher.getTeacherId();
		teacherFirstName = teacher.getTeacherFirstName();
		teacherLastName = teacher.getTeacherLastName();
	}
}