package edu.nuist.ojs.baseinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_role_relation")
public class UserRoleRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long journalId;//default value = -1 means original role

    private long roleId;

    private long userId;
}
