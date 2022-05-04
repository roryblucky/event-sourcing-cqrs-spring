package com.rory.eventsourcingcqrsspring.dto;

import lombok.Data;

@Data
public class AccountCreateDTO {
    private double startingBalance;

    private String currency;
}
