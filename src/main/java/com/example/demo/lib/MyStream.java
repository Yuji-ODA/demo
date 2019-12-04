package com.example.demo.lib;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

@RequiredArgsConstructor(staticName = "of")
public class MyStream<T> implements BaseStream<T, MyStream<T>> {

    private final Stream<T> stream;

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @NotNull
    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @NotNull
    @Override
    public MyStream<T> sequential() {
        return MyStream.of(stream.sequential());
    }

    @NotNull
    @Override
    public MyStream<T> parallel() {
        return MyStream.of(stream.parallel());
    }

    @NotNull
    @Override
    public MyStream<T> unordered() {
        return this;
    }

    @NotNull
    @Override
    public MyStream<T> onClose(Runnable closeHandler) {
        return this;
    }

    @Override
    public void close() {
        stream.close();
    }
}
