package com.spykins.todo.view_interface;

/**
 * Created by Spykins on 18/09/16.
 */
public interface AddNewTodoInterface {
    void successfullyCreated();
    void reportError(String displayText, String actionText);
}
