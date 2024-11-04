package it.unibo.inner.impl;

import java.util.Arrays;
import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    private final T[] array;

    public IterableWithPolicyImpl(final T[] array) {
        this.array = Arrays.copyOf(array, array.length);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator(this.array);
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        throw new UnsupportedOperationException("Unimplemented method 'setIterationPolicy'");
    }

    private class ArrayIterator implements Iterator<T> {

        private final T[] array;
        private int index = 0;

        public ArrayIterator(final T[] array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.array.length;
        }

        @Override
        public T next() {
            this.index++;
            return this.array[index - 1];
        }

    }

}
