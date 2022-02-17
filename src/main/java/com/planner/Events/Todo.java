package com.planner.Events;

import java.util.ArrayList;
import java.util.List;

public class Todo extends Event {
    private List<String> todo = new ArrayList<>();

    public Todo(Event event) {
        super(event);
        setTitleHeader("Quick Description:");
        setDescriptionHeader("Todo:");
    }

    public void addTodo(String todo) {
        if (this.todo.size() > 7) return;

        this.todo.add(todo);
        super.setDescription(getTodoAsString());
    }

    public void removeTodo(int index) {
        this.todo.remove(index);
        super.setDescription(getTodoAsString());
    }
    
    public List<String> getTodo() {
        return todo;
    }

    public String getTodoAsString() {
        var ret = new StringBuilder();
        for (int i = 0; i < todo.size(); i++) {
            ret.append(i).append(". ").append(todo.get(i)).append("\n");
        }
        return ret.toString();
    }
}
