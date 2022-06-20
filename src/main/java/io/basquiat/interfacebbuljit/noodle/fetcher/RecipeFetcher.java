package io.basquiat.interfacebbuljit.noodle.fetcher;

/**
 * Function<T, R>
 * @param <T>
 * @param <R>
 */
public interface RecipeFetcher<T, R> {

    R apply(T t);

}
