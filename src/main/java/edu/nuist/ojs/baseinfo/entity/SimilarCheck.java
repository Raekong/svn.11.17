package edu.nuist.ojs.baseinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "similarcheck")
public class SimilarCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long aid; //论文ID
    private long jid;
    private String title;
    private String fileType;
    private String fileName;
    private String checkid;//查重ID
    private int round;   //第几轮查重
    private boolean pass;//是否通过
    private String link;//文件链接
    private String totalSimilar;//总重复率
    private String frsSimilar; //第一个分项率
    private String secSimilar;//第二个分项率
    private String thrSimilar; //第三个分项率

    private boolean uploaded; //是否上传检索

}
