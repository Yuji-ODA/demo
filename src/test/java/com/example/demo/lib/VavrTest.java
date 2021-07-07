package com.example.demo.lib;

import com.example.demo.Person;
import io.vavr.Tuple2;
import io.vavr.collection.CharSeq;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import io.vavr.match.annotation.Unapply;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;

import static io.vavr.API.*;
import static io.vavr.Patterns.*;
import static io.vavr.Predicates.is;
import static org.assertj.core.api.Assertions.assertThat;

public class VavrTest {

    @Test
    void test() {
        Option.of("null exp")
                .flatMap(nullable(this::nullableFunction))
                .peek(System.out::println)
                .onEmpty(() -> System.out.println("Empty"));
    }

    @Test
    void testIsEmpty() {
        boolean isEmpty = Option.of("init")
                .map(x -> null)
                .isEmpty();

        System.out.println(isEmpty);
    }

    @Test
    void testToJavaOptional() {
        Option.of("init")
                .map(x -> null)
                .toJavaOptional()
                .ifPresentOrElse(System.out::println, () -> System.out.println("Empty"));
    }

    @Test
    void testTransform() {
        var result = Option.of(createInt())
                .map(i -> i - 1)
                .transform(o -> Match(o).of(
                        Case($Some($(positive().or(is(0)))), "positive or zero"),
                        Case($Some($(is(0))), "zero"),
                        Case($Some($(negative())), "negative"),
                        Case($None(), () -> "None"))
                );

        System.out.print(result);
    }

    static Predicate<Integer> greaterThan(int thresh) {
        return x -> thresh < x;
    }

    static Predicate<Integer> lessThan(int thresh) {
        return x -> x < thresh;
    }

    static Predicate<Integer> positive() {
        return greaterThan(0);
    }

    static Predicate<Integer> negative() {
        return lessThan(0);
    }

    @Unapply
    static <T> T optionUnapplier(Option<T> option) {
        return option.get();
    }

    int createInt() {
        return 0;
    }

    @Nullable
    String nullableFunction(@NonNull String input) {
        return input.equals("null exp") ? null : input.concat(" is not null exp");
    }

    static <T, U> Function<T, Option<U>> nullable(Function<? super T, ? extends U> f) {
        return t -> Option.of(f.apply(t));
    }

    static class PersonValidator {
        private static final String VALID_NAME_CHARS = "[a-zA-Z ]";
        private static final int MIN_AGE = 0;

        public Validation<Seq<String>, Person> validatePerson(String name, int age) {
            return Validation.combine(validateName(name), validateAge(age)).ap(Person::new);
        }

        private Validation<String, String> validateName(String name) {
            return CharSeq.of(name).replaceAll(VALID_NAME_CHARS, "").transform(seq -> seq.isEmpty()
                    ? Validation.valid(name)
                    : Validation.invalid("Name contains invalid characters: '"
                    + seq.distinct().sorted() + "'"));
        }

        private Validation<String, Integer> validateAge(int age) {
            return age < MIN_AGE
                    ? Validation.invalid("Age must be at least " + MIN_AGE)
                    : Validation.valid(age);
        }
    }

    @Test
    void testPersonValidator() {
        PersonValidator validator = new PersonValidator();
        Validation<Seq<String>, Person> valid = validator.validatePerson("John Doe", 30);
        Option<Person> p1 = valid.toOption();
        System.out.println(p1);
        if (valid.isInvalid()) System.out.println(valid.getError());

        Validation<Seq<String>, Person> invalid = validator.validatePerson("John? Doe!4", -1);
        Option<Person> p2 = invalid.toOption();
        System.out.println(p2);
        if (invalid.isInvalid()) System.out.println(invalid.getError());
    }

    @Test
    void testSequence() {
        Seq<Option<Integer>> given = io.vavr.collection.List.of(Option.of(3), Option.none(), Option.of(5));
        Option<Seq<Integer>> actual = Option.sequence(given);

        System.out.println(actual);

        System.out.println(actual.map(io.vavr.collection.Seq::sum));

        Validation.valid(true).toEither("ERROR!!").swap();
    }

    static final Either<String, Boolean> VALID = Either.right(true);

    static Either<String, Boolean> valid() {
        return VALID;
    }

    static Either<String, Boolean> invalid(String msg) {
        return Either.left(msg);
    }

    @Test
    void testValidation() {
        String l = Match(new Tuple2<>("hoge", 1)).of(
                Case($Tuple2($(), $()), (line, lineNo) -> line + lineNo)
        );
        System.out.println(l);
    }

    @Test
    void testMkString() {
        Stream<String> given = Stream.of("Hoge", "Huga", "Foo", "Bar");

        String expected = "pref_Hoge,Huga,Foo,Bar_suf";

        String actual = given.mkString("pref_", ",","_suf");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testIntersperse() {
       List.of(1,2,3).intersperse(999999999).forEach(System.out::println);
    }

    @Test
    void testScan() {
        List.of("hoge", "huga", "foo", "bar")
                .scan("init", (a, b) -> a + b)
                .forEach(System.out::println);
    }
}
