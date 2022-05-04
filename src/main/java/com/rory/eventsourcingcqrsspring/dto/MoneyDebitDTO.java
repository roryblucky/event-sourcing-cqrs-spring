package com.rory.eventsourcingcqrsspring.dto;

import lombok.Data;

@Data
public class MoneyDebitDTO {
    private double debitAmount;

    private String currency;
}
