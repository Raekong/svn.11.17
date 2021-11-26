package edu.nuist.ojs.baseinfo.entity;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PayInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    public static final int WAITING = 0;
    public static final int PAYEDANDWAITCHECK = 1;
    public static final int CHECKED = 1;

    public static final String WIRE = "Wire Transfer";
    public static final String ONLINE = "Online";

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long aid;
    private long jid;
    //这是由编辑设置的支付参数
	private int orgPageNumber;
	private int orgTotalAPC;
    private int orgWire;
    private int orgOnline;
    
    //这是用户设置的真实支付参数,如果是外链是可以空的
    private long userId;
	private String payEmail;
	private int payPageNumber;
    private int payTotal;
	private String payType;

	// 0,待支付 1,待审查 2,完成支付
	private int state;
	
	//外链ID
	private String linkMd5;
	//如果是使用文件上传则要保存上传文件的ID
    private long wireFileId;

	//支付时间
	private Timestamp paytime;

	@Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
	@CreatedDate
	private Timestamp timeStamp;

    //是否补交
    private boolean isBack;
}
