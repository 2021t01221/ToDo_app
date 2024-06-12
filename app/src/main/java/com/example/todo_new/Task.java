package com.example.todo_new;

public class Task {
    private int id;
    private String title;
    private String deadline;
    private String startTime;
    private String endTime;
    private String remind;
    private String repeat;
    private String category;

    public Task(int id, String title, String deadline, String startTime, String endTime, String remind, String repeat, String category) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remind = remind;
        this.repeat = repeat;
        this.category = category;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(int id) {
    }
}
