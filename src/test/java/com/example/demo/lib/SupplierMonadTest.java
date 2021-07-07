package com.example.demo.lib;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Supplier;

class SupplierMonadTest {
    @Test
    void testOfSupplier() {
        Supplier<LocalDateTime> localDateTimeSupplier = SupplierMonad.of(LocalDate::now).map(LocalDate::atStartOfDay);

        System.out.println(localDateTimeSupplier.get());
    }

}