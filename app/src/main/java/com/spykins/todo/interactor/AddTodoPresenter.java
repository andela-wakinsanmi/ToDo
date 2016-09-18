package com.spykins.todo.interactor;

import android.content.Context;

import com.spykins.todo.TodoApp;
import com.spykins.todo.model.Todo;
import com.spykins.todo.view_interface.AddNewTodoInterface;

import java.lang.ref.WeakReference;
import java.util.Calendar;

/**
 * Created by Spykins on 18/09/16.
 */
public class AddTodoPresenter {
    private WeakReference<AddNewTodoInterface> addNewTodoInterface;
    private Context context;


    public AddTodoPresenter(AddNewTodoInterface addNewTodoInterface) {
        this.addNewTodoInterface = new WeakReference<>(addNewTodoInterface);
        context = TodoApp.getContext();
    }

    public void createTodo(String title, String description, int year,
                           int month, int dayOfMonth, int hour, int minute) {

        long time = computeTimeInMillis(year, month, dayOfMonth, hour, minute);
        long currentTime = System.currentTimeMillis();

        //delete
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if (description.isEmpty() || title.isEmpty()) {
            addNewTodoInterface.get().reportError("Please type in text");
            return;
        }

        Todo todo = new Todo(title, time, description, currentTime - time, false);
        TodoApp.getDbHandler().insertTodoIntoDb(todo);
        if (addNewTodoInterface.get() != null) {
            addNewTodoInterface.get().successfullyCreated();
        }

    }

    private long computeTimeInMillis(int year, int month, int dayOfMonth, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTimeInMillis();
    }

}
