package net.lbenzzine.backend.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Month;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MonthlyRewards {

    private Long customerId;
    private Month month;
    private Long monthlyReward;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getMonthlyReward() {
        return monthlyReward;
    }

    public void setMonthlyReward(Long monthlyReward) {
        this.monthlyReward = monthlyReward;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}
