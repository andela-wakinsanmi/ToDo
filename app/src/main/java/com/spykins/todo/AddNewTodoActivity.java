package com.spykins.todo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.spykins.todo.interactor.AddTodoPresenter;
import com.spykins.todo.view_interface.AddNewTodoInterface;

public class AddNewTodoActivity extends AppCompatActivity implements AddNewTodoInterface {
    private AddTodoPresenter addTodoPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dd_new_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        addTodoPresenter = new AddTodoPresenter(this);
        setSupportActionBar(toolbar);

    }

    @Override
    public void successfullyCreated() {
        finish();
    }

    @Override
    public void reportError(String error) {
        displaySnackBar(error);
    }

    private void displaySnackBar(String textToDisplay) {
        Snackbar.make(findViewById(R.id.rootView), textToDisplay, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
