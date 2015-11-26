package com.example.danielgalarza.collabstation;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Singleton class: allows only one instance of iteself to be created
 *
 *
 * Created by dustin on 11/25/15.
 */
public class TodoLab {

    private static TodoLab sTodoLab; // s prefix designates a static variable

    private List<Todo> mTodos; // list of task items (supports ordered list of objects)

    // static accessor method (prevents other classes from bypassing get method)
    public static TodoLab get(Context context) {
        if (sTodoLab == null) {
            sTodoLab = new TodoLab(context);
        }
        return sTodoLab;
    }

    // private constructor
    private TodoLab(Context context) {
        mTodos = new ArrayList<>();

        // create 50 generic task items
        /** FOR TESTING PURPOSES **
        for (int i = 1; i < 51; i++) {
            Todo todo = new Todo();
            todo.setTitle("Todo Task #" + i);
            todo.setTodoComplete(i % 2 == 0); // Every other checked
            mTodos.add(todo);
        }
        **/
    }

    public void addTodo(Todo t) {
        mTodos.add(t);
    }

    public void removeTodo(Todo t) {
        mTodos.remove(t);
    }

    public List<Todo> getTodos() {
        return mTodos;
    }

    public Todo getTodo(UUID id) {
        for (Todo todo : mTodos) {
            if (todo.getId().equals(id)) {
                return todo;
            }
        }
        return null;
    }
}
