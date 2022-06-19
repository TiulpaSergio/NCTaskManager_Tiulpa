package ua.edu.sumdu.j2se.tiulpa.tasks;

import java.time.LocalDateTime;
import java.util.Objects;
import java.io.Serializable;

public class Task implements Cloneable, Serializable{
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active, repeat;

    public Task(String title, LocalDateTime time) {
        if(time == null){
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.time = time;
        repeat = false;
        active = false;
    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
        if (interval <= 0 || start == null || end == null) {
            throw new IllegalArgumentException();
        }

        this.title = title;
        setTime(start, end, interval);
        repeat = true;
        active = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getTime() {
        if (repeat) {
            return start;
        }
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
        repeat = false;
    }

    public LocalDateTime getStartTime() {
        if (repeat) {
            return start;
        }
        return time;
    }

    public LocalDateTime getEndTime() {
        if (repeat) {
            return end;
        }
        return time;
    }

    public int getRepeatInterval() {
        if (repeat) {
            return interval;
        }
        return 0;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.time = start;

        repeat = true;

    }

    public boolean isRepeated() {
        return this.repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (isActive()) {
            if (!isRepeated()) {
                if (time.isAfter(current)) {
                    return time;
                }
                else {
                    return null;
                }
            }
            for (LocalDateTime i = start; i.isBefore(end) || i.equals(end); i = i.plusSeconds(interval)) {
                if (current.isBefore(i)) {
                    return i;
                }
            }
        }

        return null;

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
        return title.equals(((Task) otherObject).title) &&
                start == ((Task) otherObject).start &&
                end == ((Task) otherObject).end &&
                interval == ((Task) otherObject).interval &&
                active == ((Task) otherObject).active &&
                repeat == ((Task) otherObject).repeat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), isActive(), start, end, interval, repeat);
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

}
