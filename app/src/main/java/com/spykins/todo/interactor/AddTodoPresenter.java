package com.spykins.todo.interactor;

import android.content.Context;

import com.spykins.todo.TodoApp;
import com.spykins.todo.model.Todo;
import com.spykins.todo.view_interface.AddNewTodoInterface;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Spykins on 18/09/16.
 */
public class AddTodoPresenter {
    private WeakReference<AddNewTodoInterface> addNewTodoInterface;
    private Context context;
    private boolean isTodayDate;



    public AddTodoPresenter(AddNewTodoInterface addNewTodoInterface) {
        this.addNewTodoInterface = new WeakReference<>(addNewTodoInterface);
        context = TodoApp.getContext();
    }

    public void createTodo(String title, String description, int year,
                           int month, int dayOfMonth, int hour, int minute) {

        long time = computeTimeInMillis(year, month, dayOfMonth, hour, minute);
        long currentTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        if(isDescriptionAndTitleEmpty(description,title)) {
            return;
        }

        if(!isTimeValid(time,currentTime)) {
            return;
        }

        if (!isTimeProperlySet(year,month,dayOfMonth,hour,minute)) {
            return;
        }

        Todo todo = new Todo(title, time, description, currentTime - time, false);
        TodoApp.getDbHandler().insertTodoIntoDb(todo);
        setUpInPreference(time);
        if (addNewTodoInterface.get() != null) {
            addNewTodoInterface.get().successfullyCreated();
        }

    }

    private boolean isTimeValid(long time, long currentTime) {
        if (addNewTodoInterface.get() != null) {
            if(isTodayDate) {
                if (time < currentTime) {
                    addNewTodoInterface.get().reportError("Time is in the past", "Reset Time");
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isDescriptionAndTitleEmpty(String description, String title) {
        if (addNewTodoInterface.get() != null) {
            if(description.isEmpty() || title.isEmpty()) {
                addNewTodoInterface.get().reportError("Please type in text","");
                return true;
            }
        }
        return false;
    }

    private boolean isTimeProperlySet(int year, int month, int dayOfMonth, int hour, int minute) {
        if(year == 2000 || month == 2000 || dayOfMonth ==2000 ) {
            if (addNewTodoInterface.get() != null) {
                addNewTodoInterface.get().reportError("Set The Date", "ResetDate");
            }
            return false;

        } else if (hour == 2000 || minute == 2000) {
            if (addNewTodoInterface.get() != null) {
                addNewTodoInterface.get().reportError("Set The Time", "ResetTime");
            }
            return false;
        }
        return true;
    }

    private void setUpInPreference(Long time) {
        Long savedInSharedPreference = TodoApp.getTimeInSharedPreference();
        //bug
        if(savedInSharedPreference < System.currentTimeMillis()) {
            TodoApp.setTimeInPreference(time);
        } else if( savedInSharedPreference.compareTo(time) > 0) {
            TodoApp.setTimeInPreference(time);
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

    public boolean isDateValid(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date date = calendar.getTime();
        Date date1 = new Date();
        isTodayDate = date.compareTo(date1) == 0;
        return date1.compareTo(date) == 1;
    }

}
