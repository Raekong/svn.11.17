package edu.nuist.ojs.baseinfo.entity.article;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ArticleEditorBoard")
public class ArticleEditorBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long roleId;
    private long aid;
    private long uid;
    private boolean decision;
}
