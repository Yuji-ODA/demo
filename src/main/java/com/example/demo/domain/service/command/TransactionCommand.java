package com.example.demo.domain.service.command;

import com.example.demo.domain.service.TransactionService;

public interface TransactionCommand<T extends TransactionService> {
    void excute(T service);
}
