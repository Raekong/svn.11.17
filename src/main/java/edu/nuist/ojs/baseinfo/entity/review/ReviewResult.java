package edu.nuist.ojs.baseinfo.entity.review;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.nuist.ojs.baseinfo.entity.article.ArticleFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ReviewResult")
public class ReviewResult {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long reviewActionId;
	@Transient
	private List<ArticleFile> files;  

    @Column (columnDefinition="TEXT") //定义成文本
	private String filesStr;
	private String appropriateness;
	private String clarity;
	private String originality;
	private String substance;
	private String significance;
	private String impact;
	private int recommendType;

	private Timestamp timeStamp;
	@Column (columnDefinition="TEXT") //定义成文本
	private String commendforAuthor;
	@Column (columnDefinition="TEXT") //定义成文本
	private String commendforEditor;
	
	   
	public static String getScoreStr( int score ) {
		String rst = "";
		switch(score) {
		 	case 4:
		 		rst = "Very Strong";
		 		break;
		 	case 3: 
		 		rst = "Strong";
		 		break;
		 	case 2:
		 		rst = "Moderate";
		 		break;	
		 	case 1:
		 		rst = "Poor";
		 		break;
		 	case 0:
		 		rst = "Very Poor";
		 		break;
		}           
		return rst;
	}
	
	public int getScore(String comment ) {
		int rst = -1;
		if(comment!=null) {
			switch(comment) {
		 	case "Very Strong": 
		 		rst = 4;
		 		break;
		 	case "Strong": 
		 		rst = 3;
		 		break;
		 	case "Moderate": 
		 		rst = 2;
		 		break;	
		 	case "Poor": 
		 		rst = 1;
		 		break;
		 	case "Very Poor": 
		 		rst = 0;
		 		break;	
			} 
		}          
		return rst;
	}
}
