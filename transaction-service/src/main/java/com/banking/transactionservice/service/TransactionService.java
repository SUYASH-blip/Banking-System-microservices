package com.banking.transactionservice.service;

import com.banking.transactionservice.DTO.TransactionResponse;
import com.banking.transactionservice.DTO.TransferRequest;
import com.banking.transactionservice.client.AccountServiceClient;
import com.banking.transactionservice.entity.Transaction;
import com.banking.transactionservice.entity.TransactionStatus;
import com.banking.transactionservice.entity.TransactionType;
import com.banking.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountServiceClient accountServiceClient;

    private static final String TRANSACTION_INITIATED_TOPIC = "transaction.initiated";
    private static final String TRANSACTION_COMPLETED_TOPIC = "transaction.completed";
    private static final String TRANSACTION_REFUNDED_TOPIC = "transaction.refunded";

    /**
     * SAGA STEP - 1: Initiate transfer
     * Deducts from sender via feign
     * Saves transaction as PROCESSING.
     * Publish event to kafka for fraud check
     * Returns.
     * @param request
     * @return
     */
    public TransactionResponse transfer(TransferRequest request) {



            log.info("SAGA START - Transfer: {} -> {} amount: {}",
                    request.getSenderAccountNumber(),
                    request.getReceiverAccountNumber(),
                    request.getAmount());

            // SAGA STEP 1: Deduct from sender
            accountServiceClient.deductBalance(
                    request.getSenderAccountNumber(),
                    request.getAmount());

            Transaction transaction = new Transaction();
            transaction.setSenderAccountNumber(request.getSenderAccountNumber());
            transaction.setReceiverAccountNumber(request.getReceiverAccountNumber());
            transaction.setAmount(request.getAmount());
            transaction.setType(TransactionType.TRANSFER);
            transaction.setStatus(TransactionStatus.PROCESSING);
            transaction.setDescription(request.getDescription());
            transaction.setReferenceNumber(UUID.randomUUID().toString());

            Transaction savedTransaction = transactionRepository.save(transaction);
            log.info("Transaction saved as Processing: {}",savedTransaction.getId());


    }


}
