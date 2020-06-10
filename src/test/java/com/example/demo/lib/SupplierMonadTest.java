package com.example.demo.lib;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Supplier;

class SupplierMonadTest {
    @Test
    void unit() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);

        Supplier<LocalDateTime> localDateTimeSupplier = SupplierMonad.unit(LocalDate::now).map(LocalDate::atStartOfDay);

        System.out.println(localDateTimeSupplier.get());
    }

}