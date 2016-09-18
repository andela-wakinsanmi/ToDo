package com.spykins.todo;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.spykins.todo.db.DbHandler;
import com.spykins.todo.receiver.TodoAlarmReceiver;

/**
 * Created by Spykins on 18/09/16.
 */
public class TodoApp extends Application {
    private static DbHandler dbHandler;
    private static AlarmManager alarmManager;
    private static SharedPreferences sharedPreferences;
    private static final String LEAST_TIME_IN_TODO = "LEAST_TIME_IN_TODO";
    private static final String TODO_PREF = "TODO_PREF";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHandler = DbHandler.getInstance(this);
        sharedPreferences = getSharedPreferences(TODO_PREF, MODE_PRIVATE);
        context = this;

    }

    public static DbHandler getDbHandler() {
        return dbHandler;
    }

    private static void setAlarm(Context context, Long time) {
        Intent intent = new Intent(context, TodoAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(time > System.currentTimeMillis()) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
    }

    public static void setTimeInPreference(long time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(LEAST_TIME_IN_TODO,time);
        editor.apply();
        updateAlarmToNewTime(time);
    }

    public static long getTimeInSharedPreference() {
        long time = sharedPreferences.getLong(LEAST_TIME_IN_TODO, 0L);
        return time;
    }

    public static Context getContext() {
        return context;
    }
    public static void updateAlarmToNewTime(long time) {
        cancelAlarm();
        setAlarm(context,time);
    }

    //error... fix
    public static void cancelAlarm() {
        if(alarmManager != null) {
            Intent intent = new Intent(context, TodoAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
    }

}


