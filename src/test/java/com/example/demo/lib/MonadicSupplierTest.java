package com.example.demo.lib;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Supplier;

class MonadicSupplierTest {
    @Test
    void testOfSupplier() {
        Supplier<LocalDateTime> localDateTimeSupplier = MonadicSupplier.of(LocalDate.now()).map(LocalDate::atStartOfDay);

        System.out.println(localDateTimeSupplier.get());
    }

}