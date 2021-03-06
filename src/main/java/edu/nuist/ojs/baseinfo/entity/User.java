package edu.nuist.ojs.baseinfo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table(name = "User")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(name = "username")
	private String username;

	@Column(nullable = false,name = "password")
	private String password;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "middlename")
	private String middlename;

	@Column(name = "lastname")
	private String lastname;

	@Column(nullable = true,name = "title")
	private String title;

	@Column(nullable = false,name = "email")
	private String email;

	@Column(name = "url")
	private String url;

	@Column(name = "mailing_address")
	private String mailing_address;

	@Column(name = "country")
	private String country;

	@Column( name = "disabled")
	private boolean disabled;

	@Column(name = "inline_help")
	private Byte inline_help;

	@Column(name = "affiliation", columnDefinition="TEXT")
	private String affiliation;

	@Column(name = "interests")
	private String interests;
	
	private boolean isActived;
	
	@Lob
	@Column(name = "role", columnDefinition="TEXT")
	private String role;

	@Column(name = "super_user")
	private boolean superUser ;		//是否超级用户

	@Column(name = "root")
	private boolean root ;	//是否出版社管理员

	@Column(name = "publisher_id")
	private long publisherId;	//用户所属出版社Id, 超级用户不属于任一出版社，该值为-1

	
	private String i18n;

	public static User valueOf(String json) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, User.class);
    }
}
