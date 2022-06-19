package ua.edu.sumdu.j2se.tiulpa.tasks;

import java.util.Iterator;
import java.util.Arrays;
import java.util.stream.Stream;
import java.time.LocalDateTime;
import java.io.Serializable;

public abstract class AbstractTaskList implements Iterable<Task>, Serializable{

    protected int size;
    protected static ListTypes.types type;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index);

    public abstract Iterator<Task> iterator();

    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) {
        if(from.isAfter(to)) {
            throw new IllegalArgumentException("Invalid interval!");
        }

        AbstractTaskList returnArr = TaskListFactory.createTaskList(type);

        getStream().filter(task -> task.nextTimeAfter(from).isAfter(from) &&
                task.nextTimeAfter(to).isBefore(to)).forEach(returnArr::add);
        return returnArr;
    }

    @Override
    public int hashCode() {
        int result = size;

        for (Task task : this) {
            result ^= task.hashCode();
        }

        if (type == ListTypes.types.ARRAY) {
            result = ~result;
        }

        return result;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) {
            return false;
        }
        if (this == otherObject) {
            return true;
        }
        if (getClass() != otherObject.getClass()) {
            return false;
        }
        if (size != ((AbstractTaskList) otherObject).size) {
            return false;
        }

        Iterator otherIt = ((AbstractTaskList) otherObject).iterator();
        for (Task thisIt : this) {
            if (!thisIt.equals(otherIt.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        AbstractTaskList atl = TaskListFactory.createTaskList(type);
        for (Task t : this) {
            atl.add(t);
        }
        return atl;
    }

    @Override
    public String toString() {
        Iterator<Task> strIterator = this.iterator();
        StringBuilder finalString = new StringBuilder();
        int number = 0;

        if (type == ListTypes.types.ARRAY) {
            finalString.append("ArrayTaskList.class | ");
        } else {
            finalString.append("LinkedTaskList.class | ");
        }
        finalString.append(size);

        while (strIterator.hasNext()) {
            finalString.append(" | Object");
            finalString.append(number);
            finalString.append(" | ");
            finalString.append(strIterator.next().toString());
            number++;
        }
        return new String(finalString);
    }

    public Stream<Task> getStream() {
        Task[] tasks = new Task[this.size()];
        for (int i = 0; i < tasks.length; ++i) {
            tasks[i] = getTask(i);
        }
        return Arrays.stream(tasks);
    }

}
