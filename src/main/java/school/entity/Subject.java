package school.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	private String subjectLevel; // honors, AP, etc.   
	
	/*@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true) // the One to many relationship
	private Set<Teacher> teachers = new HashSet<>(); */
	
	// Many (school) to Many (subjects)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "subjects", cascade = CascadeType.PERSIST)
	private Set<School> schools = new HashSet<>();  
}
