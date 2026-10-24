package com.banking.transactionservice.model;

/**
 * Transaction Lifecycle Flow
 * Pending --> Processing --> completed (clean transaction)
 *                        --> PENDING.VERIFICATION (SUSPICIOUS DETECTED)
 *              verified  --> Completed (verified)
 *          not verified  --> FLAGGED (saga refund)
 *          FAILED
 *          FLAGGED
 */
public enum TransactionStatus {
    PENDING,
    PROCESSING,
    PENDING_VERIFICATION,
    COMPLETED,
    FAILED,
    FLAGGED

}
