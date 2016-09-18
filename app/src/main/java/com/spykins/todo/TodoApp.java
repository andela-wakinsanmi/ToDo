package com.spykins.todo;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.spykins.todo.db.DbHandler;
import com.spykins.todo.receiver.TodoAlarmReceiver;

/**
 * Created by Spykins on 18/09/16.
 */
public class TodoApp extends Application {
    private static DbHandler dbHandler;
    private static AlarmManager alarmManager;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        dbHandler = DbHandler.getInstance(this);
    }

    public static DbHandler getDbHandler() {
        return dbHandler;
    }

    private static void setAlarm(Context context, Long time) {
        Intent intent = new Intent(context, TodoAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public static Context getContext() {
        return context;
    }

}


