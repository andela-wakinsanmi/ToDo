package com.spykins.todo.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Spykins on 18/09/16.
 */
public class TodoTest {
    Todo todo1 = new Todo("Play Game", 203910, "Play Monopoly game", 2034, false);
    Todo todo2 = new Todo("Church", 34780, "Go to Redeem Church", 3248, true);

    @Test
    public void testCompareTo() throws Exception {
        assertTrue(todo1.compareTo(todo2) == 1);
        assertTrue(todo2.compareTo(todo1) == -1);
    }

}