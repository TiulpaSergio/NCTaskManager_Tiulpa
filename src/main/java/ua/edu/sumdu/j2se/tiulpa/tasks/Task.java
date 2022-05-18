package ua.edu.sumdu.j2se.tiulpa.tasks;

public class Task {
    private String title;
    private int time, start, end, interval;
    private boolean active, repeat;

    //конструтор для неповторюваної задачі

    public Task(String title, int time) {
        this.title = title;
        this.time = time;
        repeat = false;
        active = false;
    }

    //конструтор для повторюваної задачі

    public Task(String title, int start, int end, int interval) {
        this.title = title;
        setTime(start, end, interval);
        repeat = true;
        active = false;
    }

    //метод для повернення назви задачі

    public String getTitle() {
        return title;
    }

    //метод для встановлення назви задачі

    public void setTitle(String title) {
        this.title = title;
    }

    //метод на перевірку чи є задача активною чи ні

    public boolean isActive() {
        return active;
    }
    //метод для встановлення стану активності задачі

    public void setActive(boolean active) {
        this.active = active;
    }

    //метод для зчитування стану неповторюваної задачі

    public int getTime() {
        if (repeat) {
            return start;
        }
        return time;
    }

    //метод для заміни стану неповторюваної задачі

    public void setTime(int time) {
        this.time = time;
        repeat = false;
    }

    //метод для зчитування початкового часу повторюваної задачі

    public int getStartTime() {
        if (repeat) {
            return start;
        }
        return time;
    }

    //метод для зчитування кінечного часу повторюваної задачі

    public int getEndTime() {
        if (repeat) {
            return end;
        }
        return time;
    }

    //метод для перевірки повторюваності і передачі інтервалу
    // повторюваної задачі

    public int getRepeatInterval() {
        if (repeat) {
            return interval;
        }
        return 0;
    }

    //метод для заміни стану повторюваної задачі

    public void setTime(int start, int end, int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.time = start;

        repeat = true;

    }

    //метод для перевірки повторюваності

    public boolean isRepeated() {
        return repeat;
    }

    //Метод перевірки наступного виконання задачі

    public int nextTimeAfter(int current) {
        if (!active) {
            return -1;
        }

        else {
            if (!repeat) {
                if (time <= current) {
                    return -1;
                }

                else {
                    return time;
                }

            }

            else {
                int promizh = start;

                while (promizh <= current) {
                    promizh += interval;

                    if (promizh > end) {
                        return -1;
                    }
                }

                return promizh;
            }

        }
    }
}

