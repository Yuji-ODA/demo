package com.example.demo.domain.service;

import com.example.demo.domain.service.command.TransactionCommand;

public interface TransactionService {
    void run(TransactionCommand command);
}
