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
}