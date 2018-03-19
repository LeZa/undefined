package com.service.api.me.base.generic;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Test {


    /**
     * @Description  参数E必须继承Delayed
     * @param <E>
     */
    class DelayQueueGeneric< E extends Delayed>
                extends AbstractQueue<E> implements BlockingQueue<E>{


        @Override
        public Iterator<E> iterator() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void put(E e) throws InterruptedException {

        }

        @Override
        public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
            return false;
        }

        @Override
        public E take() throws InterruptedException {
            return null;
        }

        @Override
        public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
            return null;
        }

        @Override
        public int remainingCapacity() {
            return 0;
        }

        @Override
        public int drainTo(Collection<? super E> collection) {
            return 0;
        }

        @Override
        public int drainTo(Collection<? super E> collection, int i) {
            return 0;
        }

        @Override
        public boolean offer(E e) {
            return false;
        }

        @Override
        public E poll() {
            return null;
        }

        @Override
        public E peek() {
            return null;
        }
    }
}
