package com.example.demo.util;

public final class Range<T extends Comparable<? super T>> {
    private final Boundary<T> from;
    private final Boundary<T> to;

    private Range(Boundary<T> from, Boundary<T> to) {
        this.from = from;
        this.to = to;
    }

    public static <T extends Comparable<? super T>> Range<T> of(Boundary<T> from, Boundary<T> to) {
        return new Range<T>(from, to);
    }

    public boolean includes(T target) {
        return !from.greaterThan(target) && to.greaterThan(target);
    }

    public Boundary<T> getFrom() {
        return this.from;
    }

    public Boundary<T> getTo() {
        return this.to;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Range)) return false;
        final Range<?> other = (Range<?>) o;
        final Object this$from = this.getFrom();
        final Object other$from = other.getFrom();
        if (this$from == null ? other$from != null : !this$from.equals(other$from)) return false;
        final Object this$to = this.getTo();
        final Object other$to = other.getTo();
        if (this$to == null ? other$to != null : !this$to.equals(other$to)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $from = this.getFrom();
        result = result * PRIME + ($from == null ? 43 : $from.hashCode());
        final Object $to = this.getTo();
        result = result * PRIME + ($to == null ? 43 : $to.hashCode());
        return result;
    }

    public String toString() {
        return "Range(from=" + this.getFrom() + ", to=" + this.getTo() + ")";
    }

    public static final class Boundary<T extends Comparable<? super T>> {
        private final T value;
        private final boolean isInclusive;

        private Boundary(T value, boolean isInclusive) {
            this.value = value;
            this.isInclusive = isInclusive;
        }

        public static <T extends Comparable<? super T>> Boundary<T> inclusive(T value) {
            return new Boundary<>(value, true);
        }

        public static <T extends Comparable<? super T>> Boundary<T> exclusive(T value) {
            return new Boundary<>(value, false);
        }

        public static <T extends Comparable<? super T>> Boundary<T> of(T value, boolean isInclusive) {
            return new Boundary<>(value, isInclusive);
        }

        public boolean greaterThan(T target) {
            return isInclusive ? 0 <= value.compareTo(target) : 0 < value.compareTo(target);
        }

        public T getValue() {
            return this.value;
        }

        public boolean isInclusive() {
            return this.isInclusive;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof Boundary)) return false;
            final Boundary<?> other = (Boundary<?>) o;
            final Object this$value = this.getValue();
            final Object other$value = other.getValue();
            if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
            if (this.isInclusive() != other.isInclusive()) return false;
            return true;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $value = this.getValue();
            result = result * PRIME + ($value == null ? 43 : $value.hashCode());
            result = result * PRIME + (this.isInclusive() ? 79 : 97);
            return result;
        }

        public String toString() {
            return "Range.Boundary(value=" + this.getValue() + ", isInclusive=" + this.isInclusive() + ")";
        }
    }
}
