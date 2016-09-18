package com.spykins.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.spykins.todo.interactor.AddTodoPresenter;
import com.spykins.todo.view_interface.AddNewTodoInterface;

import java.util.Calendar;

public class AddNewTodoActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, AddNewTodoInterface {
    private Button dateButton, timeButton;
    private EditText titleText, descriptionText;
    private AddTodoPresenter presenter;
    private int hour, minute = 2000;
    private int day, month, year = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dd_new_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        titleText = (EditText) findViewById(R.id.titleText);
        descriptionText = (EditText) findViewById(R.id.descriptionText);
        presenter = new AddTodoPresenter(this);
        setSupportActionBar(toolbar);

    }

    public void onSaveButtonClicked(View view) {
        String title = titleText.getText().toString();
        String description = descriptionText.getText().toString();
        presenter.createTodo(title,description,year,month,day,hour,minute);
    }

    public void pickDateClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();

    }

    public void pickTimeClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if (presenter.isDateValid(year, month, dayOfMonth)) {
            displaySnackBar("The Date is in the past", "actionDate");
        } else {
            dateButton.setText(dayOfMonth + " / " + month + " / " + year);
            this.day = dayOfMonth;
            this.month = month;
            this.year = year;
        }

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        timeButton.setText(hour + " : " + min);
        this.hour = hour;
        this.minute = min;
    }

    @Override
    public void successfullyCreated() {
        finish();
    }


    @Override
    public void reportError(String displayText, String actionText) {
        displaySnackBar(displayText,actionText);
    }

    private void displaySnackBar(String displayText, String actionText) {
        Snackbar.make(findViewById(R.id.rootView), displayText, Snackbar.LENGTH_LONG)
                .setAction(actionText, actionText.contains("Date")
                        ? AddNewTodoActivity.this::pickDateClicked
                        : AddNewTodoActivity.this::pickTimeClicked)
                .show();
    }

}
