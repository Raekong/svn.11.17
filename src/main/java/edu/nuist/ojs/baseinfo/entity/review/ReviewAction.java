package edu.nuist.ojs.baseinfo.entity.review;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ReviewAction")
public class ReviewAction {
	public static final int REQUESTED = 0;	//邀请
	public static final int REJECT = 1;		//拒绝
	public static final int REVIEW = 2;		//审稿
	public static final int COMPLETE = 3;	//审稿结束
	public static final int CLOSE = 4;	//关闭
	public static final int RESPONSEOVERDUE = 5;// 邀请回复逾期
	public static final int REVIEWOVERDUE = 6; // 审稿逾期
	
	public static final String[][] status = {
		{"Requested", "邀请中"},{"Rejected", "拒绝邀请"},{"Reviewing", "审稿中"}
		,{"Completed", "审稿结束"},{"Closed", "被中止"},{"Response Over Due", "邀请回复逾期"},
		{"Reviewing Over Due", "审稿逾期"}
	};

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long roundId;
	private long reviewId;
	private String responseDue;
	private String reviewDue;
	private int resultType;

	@Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false,updatable = false)
	@CreatedDate
	private Timestamp creatTimestamp;
	
	private int curstate;
	private long articleId;
	private long editorId;
	private boolean closed;
	private long lastUpdate;  

	private String reviewerName;
	private String reviewerEmail;
	
	@Column (columnDefinition="TEXT") //定义成文本
	private String fileJson;

	@Transient
	public static final SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd");
	
	public String formatDate( long date ) {
		return myFmt2.format(new Date( date ));
	}
	
	public static long getDateFromDateStr( String date ) {
		try {
			return myFmt2.parse( date ).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	

}
