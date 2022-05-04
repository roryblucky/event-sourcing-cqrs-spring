package com.rory.eventsourcingcqrsspring.dto;

import lombok.Data;

@Data
public class MoneyCreditDTO {
    private double creditAmount;

    private String currency;
}
