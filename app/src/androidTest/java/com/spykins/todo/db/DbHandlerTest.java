package com.spykins.todo.db;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.spykins.todo.model.Todo;

import org.junit.runner.RunWith;


/**
 * Created by Spykins on 18/09/16.
 */
@RunWith(AndroidJUnit4.class)
public class DbHandlerTest extends AndroidTestCase {
    DbHandler dbHandler = DbHandler.getInstance(mContext);
    Todo todo1 = new Todo("Play Game", 203910, "Play Monopoly game", 2034, false);
    Todo todo2 = new Todo("Church", 34780, "Go to Redeem Church", 3248, true);

    public void testDropDB(){

    }

    public void testCreateDB(){
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
    }

    public void testInsertData(){
        //assertTrue(DbHelper.insertIntoDatabase() == -1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        dbHandler.insertTodoIntoDb(todo1);
        //assertTrue();
    }

    public void testReadData() {

    }

}