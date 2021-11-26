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
@Table(name = "PaymentHistory")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pid;
    private long aid;
    private String paydate;
    private String paytype;
    private int paynum;
    private int paid;
    private String payemail;
}
