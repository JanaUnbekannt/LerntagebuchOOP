package com.example.students.lerntagebuchoop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.students.lerntagebuchoop.R;
import com.example.students.lerntagebuchoop.model.ChatItem;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {

    private ArrayList<ChatItem> chatItems;
    private LayoutInflater layoutInflater;

    public ChatAdapter(Context ctx, ArrayList<ChatItem> chatItems) {
        this.chatItems = chatItems;
        layoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return chatItems.size();
    }

    @Override
    public Object getItem(int position) {
        return chatItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.message_item, null);
            holder = new ChatAdapter.ViewHolder();
            holder.message = (TextView) convertView.findViewById(R.id.chat_textView);
            convertView.setTag(holder);
        } else {
            holder = (ChatAdapter.ViewHolder) convertView.getTag();
        }

        holder.message.setText(chatItems.get(position).getMessage());

        return convertView;
    }

    static class ViewHolder {
        TextView message;
    }
}
