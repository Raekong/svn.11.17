package edu.nuist.ojs.baseinfo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "journal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Journal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long journalId;//自增

	private long pid;//出版社id？

	private String title;

	private String abbreviation;

	@Column(nullable = true)
	private double jOrder;

	@Column(nullable = true)
	private String cover;

	@Column(nullable = true)
	private String publisher;

	@Column(nullable = true)
	private String researchfield;

	@Column(nullable = true)
	private String onlineissn;

	@Column(nullable = true)
	private String printissn;

	@Column(columnDefinition = "text", nullable = true )
	private String jsummary;

	@Column(columnDefinition = "text", nullable = true)
	private String jabout;
	
	private boolean isComplete;

	@Column(columnDefinition="text", nullable=true)
	private String emaillist;

	@Column(nullable=true)
	private String principalname;

	@Column(nullable=true)
	private String principaltitle;

	@Column(nullable=true)
	private String principalemail;

	@Column(nullable=true)
	private String principalphone;

	@Column(nullable=true)
	private String principalAffiliation;

	@Column(nullable=true)
	private String supportname;

	@Column(nullable=true)
	private String supportemail;

	@Column(nullable=true)
	private String supportphone;

	@Transient
	private List<String> mailList;



}
