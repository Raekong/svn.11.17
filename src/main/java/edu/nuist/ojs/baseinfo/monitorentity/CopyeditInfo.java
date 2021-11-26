package edu.nuist.ojs.baseinfo.monitorentity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CopyeditInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CopyeditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pid;
    private long aid;
    private long eid;
    private String eemail;
    private String ename;

    private String status;
    private String startdate;
    private String enddate;
}
