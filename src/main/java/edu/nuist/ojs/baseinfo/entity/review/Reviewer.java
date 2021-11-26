package edu.nuist.ojs.baseinfo.entity.review;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.nuist.ojs.baseinfo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Reviewer")
public class Reviewer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;
	
	private String affiliation;
	private String researchField;
	private long pid = -1l;

	private int totalReview;
	private int completedReview;
	private int reviewing;
	private int overResponseDue;
	private int overReviewDue;
	private int closed;

	@Transient
	private boolean isUser;

	@Transient
	private String statis;
	
	public Reviewer( User u ) {  
		this.affiliation = (u.getAffiliation() == null ? "-" : u.getAffiliation());
		this.name = (u.getUsername() == null ? "-" : u.getUsername());
		this.email = (u.getEmail() == null ? "-" : u.getEmail());
		this.researchField = (u.getInterests() == null ? "" : u.getInterests());
		this.pid = u.getPublisherId();
	}
	
	
	public Reviewer(String researchField, String affiliation, String name, String email ) {
		this.affiliation = (affiliation == null ? "-" : affiliation);
		this.researchField = (researchField == null ? "-" : researchField);
		this.name = (name == null ? "-" : name);
		this.email = (email == null ? "-" : email);
	}
	
}
