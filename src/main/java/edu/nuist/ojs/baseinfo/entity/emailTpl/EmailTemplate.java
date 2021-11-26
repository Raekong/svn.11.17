package edu.nuist.ojs.baseinfo.entity.emailTpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "email_tpl")
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long configPointId;
    private boolean defaultTpl;

    @Column (columnDefinition="TEXT") //定义成文本
    private String jsonData;
}
