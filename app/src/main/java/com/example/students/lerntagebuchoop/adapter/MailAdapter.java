package com.example.students.lerntagebuchoop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.students.lerntagebuchoop.R;
import com.example.students.lerntagebuchoop.model.MailItem;

import java.util.ArrayList;

public class MailAdapter extends BaseAdapter {

    private ArrayList<MailItem> mailItems;
    private LayoutInflater layoutInflater;

    public MailAdapter(Context ctx, ArrayList<MailItem> _mailItems) {
        this.mailItems = _mailItems;
        layoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return mailItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mailItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MailAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.mailrow_layout, null);
            holder = new MailAdapter.ViewHolder();
            holder.question = (TextView) convertView.findViewById(R.id.mail_question);
            convertView.setTag(holder);
        } else {
            holder = (MailAdapter.ViewHolder) convertView.getTag();
        }

        holder.question.setText(mailItems.get(position).getQuestion());

        return convertView;
    }

    static class ViewHolder {
        TextView question;
    }
}
