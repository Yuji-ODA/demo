package com.example.demo.lib;

public interface Composable {
    String apply(String line, int lineNo);

    default Composable succeeds(Composable that) {
        return (line, lineNo) -> this.apply(that.apply(line, lineNo), lineNo);
    }

    default Composable precedes(Composable that) {
        return (line, lineNo) -> that.apply(this.apply(line, lineNo), lineNo);
    }
}
