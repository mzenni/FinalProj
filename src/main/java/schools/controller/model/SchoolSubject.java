package schools.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchoolSubject {
	
	private Long subjectId;
	private String subjectName;
	private String subjectLevel;
	private Long schoolId; 
}
