package it.unibo.inner.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    private final T[] array;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] array) {
        this(array, new Predicate<T>() {
            @Override
            public boolean test(T elem) {
                return true;
            }
        });
    }

    public IterableWithPolicyImpl(final T[] array, Predicate<T> predicate) {
        this.array = Arrays.copyOf(array, array.length);
        this.filter = predicate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    private class ArrayIterator implements Iterator<T> {

        private int index = 0;

        // Restituisce true se esiste un prossimo elemento valido
        @Override
        public boolean hasNext() {

            for (int current = this.index; current < IterableWithPolicyImpl.this.array.length; current++) {
                if (IterableWithPolicyImpl.this.filter
                        .test(IterableWithPolicyImpl.this.array[current])) {
                    return true;
                }
            }

            return false;
        }

        // Returns the next valid element
        @Override
        public T next() {
            for (int current = this.index; current < IterableWithPolicyImpl.this.array.length; current++) {
                if (IterableWithPolicyImpl.this.filter
                        .test(IterableWithPolicyImpl.this.array[current])) {
                    index = current + 1;
                    return IterableWithPolicyImpl.this.array[current];
                }
            }

            throw new NoSuchElementException();
        }

    }
}
