package com.example.demo.domain.service;

import com.example.demo.domain.repository.BookRepository;
import com.example.demo.domain.service.command.TransactionCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Getter
@RequiredArgsConstructor
public class BookService implements TransactionService {

    private final BookRepository bookRepository;

    @Transactional
    public void run(TransactionCommand command) {
        // pre

        command.excute(this);

        // post
    }
}
