package solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {
    private List<E> data;

    public MinHeap() {
        this.data = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public void add(E element) {
        this.data.add(element);
        this.heapifyUp(this.data.size()-1);
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.data.get(0);
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        Collections.swap(this.data, 0, this.data.size() - 1);
        E toReturn = this.data.remove(this.data.size() - 1);
        this.heapifyDown();
        return toReturn;
    }

    private void heapifyDown() {
        int index = 0;
        int leftChildIndex = getLeftChildIndex(index);
        while (leftChildIndex < this.data.size()) {
            int swapIndex = leftChildIndex;
            int rightChildIndex = this.getRightChildIndex(index);

            if (rightChildIndex < this.data.size()) {
                swapIndex = isLess(leftChildIndex, rightChildIndex) ? leftChildIndex : rightChildIndex;
            }
            if(isLess(index, swapIndex)){break;}
            Collections.swap(this.data, index, swapIndex);
            index=swapIndex;
            leftChildIndex=getLeftChildIndex(index);
        }
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    @Override
    public void decrease(E element) {
        int elementIndex=this.data.indexOf(element);
        E heapElement = this.data.get(elementIndex);
        heapElement.decrease();

        this.heapifyUp(elementIndex);
    }

    private void heapifyUp(int index) {
        int parentIndex = this.getParentIndexFor(index);

        while (index > 0 && isLess(index, parentIndex)) {
            Collections.swap(this.data, index, parentIndex);
            index = parentIndex;
            parentIndex = this.getParentIndexFor(index);
        }
    }

    private boolean isLess(int firstIndex, int secondIndex) {
        return this.data.get(firstIndex).compareTo(this.data.get(secondIndex)) < 0;
    }

    private int getParentIndexFor(int index) {
        return (index - 1) / 2;
    }

    private void ensureNonEmpty() {
        if (this.data.isEmpty()) {
            throw new IllegalStateException();
        }
    }
}