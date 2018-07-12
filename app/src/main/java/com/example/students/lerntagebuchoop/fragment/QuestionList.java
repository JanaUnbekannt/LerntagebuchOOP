package com.example.students.lerntagebuchoop.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.students.lerntagebuchoop.R;
import com.example.students.lerntagebuchoop.adapter.MailAdapter;
import com.example.students.lerntagebuchoop.adapter.TaskListAdapter;
import com.example.students.lerntagebuchoop.model.IntegrationData;
import com.example.students.lerntagebuchoop.model.MailItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionList extends Fragment {

    private View mView;
    private TextView mtitle;
    private ListView mListView;
    private String question;
    private MailAdapter mAdapter;
    private ArrayList<MailItem> mailList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mailList = new ArrayList<>();

        try {
            Object mails = IntegrationData.getInstance().mailResources.get("tutormails").get("mails");
            if(mails instanceof JSONObject){
                MailItem m = new MailItem();
                JSONObject mail = (JSONObject)((JSONObject)mails).get("mail");
                m.setAnswer(mail.getString("answer"));
                m.setQuestion(mail.getString("question"));
                m.setUserName(mail.getString("user"));
                mailList.add(m);
            }
            else if(mails instanceof JSONArray){
                JSONArray mailArray = (JSONArray) ((JSONObject)mails).get("mail");
                for(int i = 0; i<mailArray.length(); i++){
                    MailItem m = new MailItem();
                    JSONObject mail = (JSONObject)((JSONObject)mailArray.get(i)).get("mail");
                    m.setAnswer(mail.getString("answer"));
                    m.setQuestion(mail.getString("question"));
                    m.setUserName(mail.getString("user"));
                    mailList.add(m);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       
        //View leeren
        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_questionlist, container, false);
        mListView = (ListView) mView.findViewById(R.id.questionList);
        mAdapter = new MailAdapter(getContext(), mailList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                String question = mailList.get(position).getQuestion();
                String answer = mailList.get(position).getAnswer();
                String userName = mailList.get(position).getUserName();
                ResponseBox resp = new ResponseBox();
                Bundle args = new Bundle();
                args.putString("question", question);
                args.putString("answer", answer);
                args.putString("userName", userName);
                resp.setArguments(args);
                ft.add(R.id.responseBox, resp).commit();
            }
        });
        return mView;
    }

}
