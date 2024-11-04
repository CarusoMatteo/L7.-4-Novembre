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

        @Override
        public boolean hasNext() {
            int current = this.index;
            while (current < array.length) {
                if (filter.test(IterableWithPolicyImpl.this.array[current])) {
                    return true;
                }
                current++;
            }

            return false;
        }

        @Override
        public T next() {
            int current = this.index;
            while (current < array.length) {
                if (IterableWithPolicyImpl.this.filter.test(IterableWithPolicyImpl.this.array[current])) {
                    this.index = current + 1;
                    return IterableWithPolicyImpl.this.array[current];
                }
                current++;
            }

            throw new NoSuchElementException();
        }
    }
}
