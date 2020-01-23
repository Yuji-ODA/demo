package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Function;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {

		Option<Integer> huga = unit(10);

		Option<Integer> foo = huga.map(i -> i * 2);

		System.out.println(foo);

		Math.random();

//		SpringApplication.run(DemoApplication.class, args);
	}

	interface BaseMonad<T, S extends Monad<T>> {
		S unit(T value);
	}

	interface Monad<T> extends BaseMonad<T, Monad<T>> {
		<R> Monad<? extends R> bind(Function<? super T, ? extends Monad<? extends R>> op);
	}

	public static <T> Option<T> unit(T t) {
		return t == null ? Option.Nothing.instance() : new Option.Some<>(t);
	}

	public interface Option<T> {
		<R> Option<R> unit(R r);
		<R> Option<R> bind(Function<? super T, ? extends Option<R>> op);
		<R> Option<R> map(Function<? super T, ? extends R> op);

		class Some<T> implements Option<T> {

			private final T t;

			public Some(T t) {
				this.t = t;
			}

			@Override
			public <R> Option<R> unit(R r) {
				return new Some<>(r);
			}

			@Override
			public <R> Option<R> bind(Function<? super T, ? extends Option<R>> op) {
				return op.apply(t);
			}

			@Override
			public <R> Option<R> map(Function<? super T, ? extends R> op) {
				return unit(op.apply(t));
			}
		}

		class Nothing<T> implements Option<T> {

			private static Nothing<?> _instance;

			private Nothing() {}

			public static <T> Nothing<T> instance() {
				if (_instance == null) {
					_instance = new Nothing<>();
				}

				//noinspection unchecked
				return (Nothing<T>)_instance;
			}

			@SuppressWarnings("unchecked")
			@Override
			public <R> Option<R> unit(R r) {
				return (Option<R>)this;
			}

			@SuppressWarnings("unchecked")
			@Override
			public <R> Option<R> bind(Function<? super T, ? extends Option<R>> op) {
				return (Nothing<R>)this;
			}

			@SuppressWarnings("unchecked")
			@Override
			public <R> Option<R> map(Function<? super T, ? extends R> op) {
				return (Nothing<R>)this;
			}
		}
	}
}
