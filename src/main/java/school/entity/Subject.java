package school.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subjectId;
	private String subjectName; // math, history
	private String subjectLevel; // honors, AP
	//private int subjectPeriod; 
	  
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true) // the One to many relationship
	private Set<Teacher> teachers = new HashSet<>(); 
	
	// Many (school) to Many (subjects)
	@ManyToMany(mappedBy = "subjects")
	private Set<School> schools = new HashSet<>();  
}
