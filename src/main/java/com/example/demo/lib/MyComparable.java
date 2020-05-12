package com.example.demo.lib;

import org.jetbrains.annotations.NotNull;

public class MyComparable implements Comparable<MyComparable> {
    @Override
    public int compareTo(@NotNull MyComparable o) {
        return 0;
    }
}
