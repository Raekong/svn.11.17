package edu.nuist.ojs.baseinfo.entity.emailTpl;

import java.util.List;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "EmailConfigPoint")
public class EmailConfigPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String configPoint;
    private boolean email;
    private long jid;

    @Transient
    private List<EmailTemplate> tpls;
}
