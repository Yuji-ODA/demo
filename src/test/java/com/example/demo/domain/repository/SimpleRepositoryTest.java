package com.example.demo.domain.repository;

import com.example.demo.domain.model.Simple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SimpleRepositoryTest {

    @Autowired
    SimpleRepository simpleRepository;

    @Test
    @Sql(statements = {"INSERT INTO Simple(id, name, age) VALUES(1, 'hoge', 100001)"})
    void test() throws Exception {
        Optional<Simple> simple = simpleRepository.findById(1);

        assertThat(simple.isPresent()).isTrue();
    }
}
