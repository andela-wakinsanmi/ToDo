package com.spykins.todo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spykins.todo.constant.DbConstant;
import com.spykins.todo.model.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Spykins on 18/09/16.
 */
public class DbHandler extends SQLiteOpenHelper {
    private static DbHandler dbHandler;
    private static Set<Long> allTheTimeInDb;

    private DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DbHandler getInstance(Context context) {
        if(dbHandler == null) {
            dbHandler = new DbHandler(context, DbConstant.DB_NAME, null, 1);
            allTheTimeInDb = new HashSet<>();
        }
        return dbHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + DbConstant.TODO_TABLE +
                "( "
                + DbConstant.TODO_COLUMN_ID  + " INT , "
                + DbConstant.TODO_COLUMN_TODO_TITLE + " TEXT , "
                + DbConstant.TODO_COLUMN_TODO_DESCRIPTION  + " TEXT , "
                + DbConstant.TODO_COLUMN_TODO_TIME  + " LONG , "
                + DbConstant.TODO_COLUMN_ALERT_TIME_BEFORE + " LONG , "
                + DbConstant.TODO_COLUMN_HAS_SHOWN + " INT , "
                + " PRIMARY KEY ( " + DbConstant.TODO_COLUMN_ID + " )"
                + " )";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + DbConstant.TODO_TABLE);
        onCreate(sqLiteDatabase);

    }

    public void insertTodoIntoDb(Todo todo) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstant.TODO_COLUMN_TODO_TITLE, todo.getTodoTitle().replaceAll("'", "\'"));
        values.put(DbConstant.TODO_COLUMN_TODO_DESCRIPTION, todo.getTodoDescription().replaceAll("'", "\'"));
        values.put(DbConstant.TODO_COLUMN_TODO_TIME, todo.getTodoTime());
        values.put(DbConstant.TODO_COLUMN_ALERT_TIME_BEFORE, todo.getTodoAlertTimeBefore());
        values.put(DbConstant.TODO_COLUMN_HAS_SHOWN, todo.isShown() ? 1 : 0);
        allTheTimeInDb.add(todo.getTodoTime());

        db.insert(DbConstant.TODO_TABLE, null, values);

    }

    public ArrayList<Todo> readAllTodoInDb(boolean isPendingTodo) {
        ArrayList<Todo> todoList;
        SQLiteDatabase db = getReadableDatabase();
        String query = "";

        if(isPendingTodo) {
            query = "SELECT * FROM " + DbConstant.TODO_TABLE + " WHERE "
                    + DbConstant.TODO_COLUMN_TODO_TIME + " >= " + System.currentTimeMillis()
                    + " ORDER BY " + DbConstant.TODO_COLUMN_TODO_TIME + " ASC";

        } else {
            query = "SELECT * FROM " + DbConstant.TODO_TABLE + " ORDER BY " +
                    DbConstant.TODO_COLUMN_TODO_TIME + " ASC";
        }
        Cursor cursorHandle = db.rawQuery(query, null);
        todoList = loopThroughCursor(cursorHandle);
        cursorHandle.close();
        if(todoList.size() >1) Collections.sort(todoList);
        return todoList;

    }

    private ArrayList<Todo> loopThroughCursor(Cursor cursorHandle) {
        ArrayList<Todo> todoList = new ArrayList<>();
        cursorHandle.moveToFirst();
        while (!cursorHandle.isAfterLast()) {

            String todoTitle = cursorHandle.getString(cursorHandle.getColumnIndex( DbConstant.TODO_COLUMN_TODO_TITLE));
            String todoDescription = cursorHandle.getString(cursorHandle.getColumnIndex(DbConstant.TODO_COLUMN_TODO_DESCRIPTION));
            long todoTime = cursorHandle.getLong(cursorHandle.getColumnIndex(DbConstant.TODO_COLUMN_TODO_TIME));
            long todoBefore = cursorHandle.getLong(cursorHandle.getColumnIndex(DbConstant.TODO_COLUMN_ALERT_TIME_BEFORE));
            boolean hasShown = cursorHandle.getInt(cursorHandle.getColumnIndex(DbConstant.TODO_COLUMN_HAS_SHOWN)) == 1;
            allTheTimeInDb.add(todoTime);
            todoList.add(new Todo(todoTitle,todoTime,todoDescription,todoBefore,hasShown));
            cursorHandle.moveToNext();
        }
        return todoList;
    }

}
