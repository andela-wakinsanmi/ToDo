package com.spykins.todo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spykins.todo.adapter.TodoAdapter;
import com.spykins.todo.model.Todo;
import com.spykins.todo.recycler.DividerItemDecoration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private TextView textView;


    public AllListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.allListRecyclerView);
        textView = (TextView) view.findViewById(R.id.allListPlaceHolder);
        ArrayList<Todo> todoList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoList);
        recyclerView.setAdapter(todoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<Todo> todoList = TodoApp.getDbHandler().readAllTodoInDb(false);
        if (todoList.size() == 0) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
        todoAdapter.addNewUpdate(todoList);
    }

}
