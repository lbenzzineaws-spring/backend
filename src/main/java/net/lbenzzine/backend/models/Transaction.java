package net.lbenzzine.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "transaction")
public class Transaction extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_spent")
    private Long amount;

    @Column(name = "reward_earned")
    private Long rewardPoints;

    //date as a string format mm-dd-yyyy
    @Column(name = "transaction_date")
    private String transactionDate;

    public Transaction(String tdate, Long amount, Long rewardPoints) {
        super();
        this.transactionDate = tdate;
        this.amount = amount;
        this.rewardPoints = rewardPoints;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) { this.amount = amount; }

    public Long getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Long rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

}
