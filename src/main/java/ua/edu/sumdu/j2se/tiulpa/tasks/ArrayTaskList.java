package ua.edu.sumdu.j2se.tiulpa.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends AbstractTaskList {

    private final int INTERVAL = 10;

    static {
        type = ListTypes.types.ARRAY;
    }


    private Task[] arrTask;


    public ArrayTaskList() {
        arrTask = new Task[INTERVAL];
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException(
                    "Attempt to add null to ArrayTaskList!"
            );
        }

        if (arrTask.length == size) {
            Task[] temp = new Task[size + INTERVAL];

            System.arraycopy(arrTask, 0, temp, 0, size);
            arrTask = temp;
        }

        arrTask[size] = task;
        size++;
    }

    @Override
    public boolean remove(Task task) {
        if (task == null) {
            throw new NullPointerException(
                    "Attempt to remove null from ArrayTaskList!"
            );
        }

        boolean searchStat = false;
        int indexDel;

        for (indexDel = 0; indexDel < size; indexDel++) {
            if (arrTask[indexDel].equals(task)) {
                searchStat = true;
                break;
            }
        }

        if (!searchStat) {
            return false;
        }

        arrTask[indexDel] = null;
        size--;

        if (indexDel != size) {
            System.arraycopy(arrTask, indexDel + 1, arrTask, indexDel,
                    size - indexDel);
        }

        if (arrTask.length - INTERVAL == size &&
                size != 0) {
            Task[] temp = new Task[size];

            System.arraycopy(arrTask, 0, temp, 0, size);
            arrTask = temp;
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid ArrayTaskList index!"
            );
        }
        return arrTask[index];
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Task next() {
                if (index == size) {
                    throw new NoSuchElementException("Iterator reached last position!");
                }
                return arrTask[index++];
            }

            @Override
            public void remove() {
                if (index == 0) {
                    throw new IllegalStateException("Needs calling of next() iterator method!");
                }

                index--;
                arrTask[index] = null;
                size--;

                if (index != size) {
                    System.arraycopy(arrTask, index + 1, arrTask, index, size - index);
                }

                if (arrTask.length - INTERVAL == size && size != 0) {
                    Task[] temp = new Task[size];

                    System.arraycopy(arrTask, 0, temp, 0, size);
                    arrTask = temp;
                }
            }
        };
    }

    @Override
    public ArrayTaskList clone() {
        ArrayTaskList retObj = new ArrayTaskList();
        for (int counter = 0; counter < size; counter++) {
            retObj.add(arrTask[counter]);
        }
        return retObj;
    }

}