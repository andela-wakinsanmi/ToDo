package com.spykins.todo.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.spykins.todo.MainActivity;
import com.spykins.todo.TodoApp;
import com.spykins.todo.model.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Spykins on 18/09/16.
 */
public class TodoAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ArrayList<Todo> todoList = TodoApp.getDbHandler().getAllTodoInTIme(TodoApp.getTimeInSharedPreference());
        String titles = "";
        for (Todo todo : todoList) {
            titles = titles + todo.getTodoTitle() + "  \n";
        }
        showNotification(context, titles);
        Set<Long> allTimeInDb = TodoApp.getDbHandler().getAllTheTimeInDb();
        loopThroughAndGetNextTime(allTimeInDb);
    }

    private void loopThroughAndGetNextTime(Set<Long> allTimeInDb) {
        ArrayList<Long> timeInList = new ArrayList<>();
        timeInList.addAll(allTimeInDb);
        Collections.sort(timeInList);
        long currentTime = TodoApp.getTimeInSharedPreference();
        long newTime = 0L;

        if(timeInList.size() > 0) {
            for(int i = 0; i< timeInList.size(); i++) {
                if (timeInList.get(i) > currentTime) {
                    if(newTime == 0) {
                        newTime = timeInList.get(i);
                    } else if (timeInList.get(i) < newTime) {
                        newTime = timeInList.get(i);
                    }
                } else {
                    allTimeInDb.remove(timeInList.get(i));
                }
            }
        }

        if(newTime != 0L) {
            TodoApp.setTimeInPreference(newTime);
        }

    }

    private void showNotification(Context context, String titles) {
        NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().
                getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context.getApplicationContext(), MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),
                100, repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Todo Reminder")
                .setContentText(titles)
                .setAutoCancel(true);
        notificationManager.notify(100, builder.build());

    }

}
