package com.example.students.lerntagebuchoop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.students.lerntagebuchoop.R;
import com.example.students.lerntagebuchoop.model.TaskItem;

import java.util.ArrayList;

public class TaskListAdapter extends BaseAdapter {

    private ArrayList<TaskItem> taskItems;
    private LayoutInflater layoutInflater;

    public TaskListAdapter(Context ctx, ArrayList<TaskItem> _taskItems){
      this.taskItems = _taskItems;
      layoutInflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return taskItems.size();
    }

    @Override
    public Object getItem(int position) {
        return taskItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.taskrow_layout, null);
            holder = new ViewHolder();
            holder.description = (TextView) convertView.findViewById(R.id.task_description);
            holder.task_text = (TextView) convertView.findViewById(R.id.task_text);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.description.setText(taskItems.get(position).getDescription());
        holder.task_text.setText(taskItems.get(position).getText());

        return convertView;
    }

    static class ViewHolder{
        TextView description;
        TextView task_text;
    }
}
