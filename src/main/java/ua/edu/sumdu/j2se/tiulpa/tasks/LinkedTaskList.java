package ua.edu.sumdu.j2se.tiulpa.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList {

    private static class ListElement {
        Task data;
        ListElement next;

        public ListElement(Task task) {
            this.data = task;
            next = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListElement node = (ListElement) o;
            return Objects.equals(data, node.data) && Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, next);
        }
    }

    static {
        type = ListTypes.types.LINKED;
    }

    private int size;
    private ListElement head;

    public LinkedTaskList() {
        size = 0;
        head = null;
    }

    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException("немає значень task!");
        }

        ListElement temp = new ListElement(task);
        ListElement curTemp = head;

        if (size == 0) {
            head = temp;
        }
        else {
            while (curTemp.next != null) {
                curTemp = curTemp.next;
            }
            curTemp.next = temp;
        }
        size++;

    }

    public boolean remove(Task task) {

        if (task == null) {
            throw new NullPointerException("немає значень task!");
        }

        ListElement curTemp = head;

        if (size == 0) {
            return false;
        }

        if(curTemp.data.equals(task)){
            head = curTemp.next;
            size--;
            return true;
        }

        while(curTemp.next != null) {
            if(curTemp.next.data.equals(task)) {
                curTemp.next = curTemp.next.next;
                size--;
                return true;
            }

            curTemp = curTemp.next;
        }

        return false;
    }

    public int size() {
        return size;
    }

    public Task getTask(int num) {
        if (num < 0 || num > size) {
            throw new ArrayIndexOutOfBoundsException("Індекс не входить у масив!");
        }

        ListElement curTemp = head;
        int counter = 0;

        while(curTemp.next != null){

            if(num == counter){
                break;
            }
            counter++;
            curTemp = curTemp.next;
        }

        return curTemp.data;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {

            private int currentIndex = 0;
            private int lastRemoved = -1;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public Task next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastRemoved = currentIndex;
                return getTask(currentIndex++);
            }

            @Override
            public void remove() {
                if (currentIndex < 1) {
                    throw new IllegalStateException();
                }
                LinkedTaskList.this.remove(getTask(lastRemoved));
                currentIndex--;
            }
        };
    }

    private ListElement task(int index) {
        ListElement t = null;
        if (index < (size >> 1)) {
            t = head;
            for (int i = 0; i < index; i++) {
                t = t.next;
            }
        }
        return t;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
