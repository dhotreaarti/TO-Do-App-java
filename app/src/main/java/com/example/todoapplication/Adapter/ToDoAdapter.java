package com.example.todoapplication.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.AddNewTask;
import com.example.todoapplication.Database.DBhelper;
import com.example.todoapplication.MainActivity;
import com.example.todoapplication.R;
import com.example.todoapplication.model.TODoModel;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewholder> {
    private List<TODoModel>list;
    private MainActivity activity;
    private DBhelper helper;

    public ToDoAdapter(DBhelper helper,MainActivity activity){
        this.activity=activity;
        this.helper=helper;

    }
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        final TODoModel item=list.get(position);
        holder.checkBox.setText(item.getTask());
        holder.checkBox.setChecked(toBoolean(item.getStatus()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    helper.updateStatus(item.getId(),0);
                }
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }
    public void setTasks(List<TODoModel>list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        TODoModel item=list.get(position);
        helper.deleteTask(item.getId());
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position){
        TODoModel item= list.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        AddNewTask task=new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
