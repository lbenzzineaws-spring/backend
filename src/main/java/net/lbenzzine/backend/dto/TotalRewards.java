package net.lbenzzine.backend.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TotalRewards {

    private Long customerId;
    private Long totalRewards;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTotalRewards() {
        return totalRewards;
    }

    public void setTotalRewards(Long totalRewards) {
        this.totalRewards = totalRewards;
    }
}