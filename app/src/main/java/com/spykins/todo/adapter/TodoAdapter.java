package com.spykins.todo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spykins.todo.R;
import com.spykins.todo.model.Todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Spykins on 18/09/16.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> todoList;

    public TodoAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View todoView = inflater.inflate(R.layout.recycler_view_layout, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(TodoAdapter.ViewHolder viewHolder, int position) {
        Todo todo = todoList.get(position);
        TextView detailTextView = viewHolder.detailTextView;
        TextView titleTextView = viewHolder.titleTextView;
        TextView timeTextView = viewHolder.timeTextView;

        detailTextView.setText(todo.getTodoDescription());
        titleTextView.setText(todo.getTodoTitle());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(todo.getTodoTime());
        timeTextView.setText(calendar.getTime().toString());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void addNewUpdate(ArrayList<Todo> todoList) {
        // Log.d("lagata", "size before clear " + to)
        this.todoList.clear();
        this.todoList.addAll(todoList);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView detailTextView;
        public TextView titleTextView;
        public TextView timeTextView;


        public ViewHolder(View itemView) {
            super(itemView);

            detailTextView = (TextView) itemView.findViewById(R.id.recyclerDetailText);
            titleTextView = (TextView) itemView.findViewById(R.id.recyclerTitleText);
            timeTextView = (TextView) itemView.findViewById(R.id.recyclerTimeForEvent);

        }
    }
}
