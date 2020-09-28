package net.lbenzzine.backend.models;


import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "customer")
public class Customer extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @OneToMany(targetEntity = Transaction.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private List<Transaction> transactions;
    @NonNull
    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    private String email;


    @Column(name = "monthly_rewards")
    private Long monthlyRewards;


    @Column(name = "total_rewards")
    private Long quarterlyRewards;

    public Customer() {

    }

    public Customer(String firstname, String lastname, String email, Long monthlyRewards, Long quarterlyRewards) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.monthlyRewards = monthlyRewards;
        this.quarterlyRewards = quarterlyRewards;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Long getMonthlyRewards() {
        return monthlyRewards;
    }

    public void setMonthlyRewards(Long monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }

    public Long getQuarterlyRewards() {
        return quarterlyRewards;
    }

    public void setQuarterlyRewards(Long quarterlyRewards) {
        this.quarterlyRewards = quarterlyRewards;
    }
}
