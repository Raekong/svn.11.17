package edu.nuist.ojs.baseinfo.monitorentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ShowSetting")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowSetting {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long uid;

    @Column (columnDefinition="TEXT") //定义成文本
    private String settingjson;    
    
}
