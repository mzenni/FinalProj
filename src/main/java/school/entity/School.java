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

@Data
@Entity
public class School {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolId;
	private String schoolName;
	private String schoolType; // public, private, religious, charter, vocational

	// Many (school) to Many (subjects)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "school_subject", joinColumns = @JoinColumn(name = "school_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private Set<Subject> subjects = new HashSet<>();

	// One (school) to Many (teachers)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JoinTable(name = "school_teacher", joinColumns = @JoinColumn(name =
	// "school_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	private Set<Teacher> teachers = new HashSet<>();

}
