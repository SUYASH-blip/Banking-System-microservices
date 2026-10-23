package com.banking.transactionservice.events;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
public class TransactionInitiatedEvent
{

    private String transactionId;

    private String senderAccountNumber;

    private String receiverAccountNumber;

    private BigDecimal amount;

    private String description;
}
