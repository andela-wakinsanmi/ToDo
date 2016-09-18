package com.spykins.todo.model;

/**
 * Created by Spykins on 18/09/16.
 */
public class Todo implements Comparable<Todo>{
    private String todoTitle;
    private String todoDescription;
    private long todoTime;
    private long todoAlertTimeBefore;
    private boolean hasShown;


    public Todo(String todoTitle, long todoTime, String todoDescription, long todoAlertTimeBefore, boolean hasShown) {
        this.hasShown = hasShown;
        this.todoAlertTimeBefore = todoAlertTimeBefore;
        this.todoDescription = todoDescription;
        this.todoTitle = todoTitle;
        this.todoTime = todoTime;
    }

    @Override
    public int compareTo(Todo todo) {
        return this.todoTime > todo.todoTime ? 1 : -1;
    }

    public boolean isShown() {
        return hasShown;
    }

    public long getTodoAlertTimeBefore() {
        return todoAlertTimeBefore;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public long getTodoTime() {
        return todoTime;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    @Override
    public String toString() {
        return "Title : " + getTodoTitle()
                + " Description " + getTodoDescription()
                + " TodoTime " + getTodoTime()
                + " TodoAlertBefore " + getTodoAlertTimeBefore()
                + " has Shown " + hasShown;
    }
}
