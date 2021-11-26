package edu.nuist.ojs.baseinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "JournalSetting")
public class JournalSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long journalId;

    private String configPoint;

    @Lob
    @Column (columnDefinition="TEXT")
    private String configContent;
}
