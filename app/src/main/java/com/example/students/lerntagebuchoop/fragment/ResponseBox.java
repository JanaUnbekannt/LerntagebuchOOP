package com.example.students.lerntagebuchoop.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.students.lerntagebuchoop.R;
import com.example.students.lerntagebuchoop.model.IntegrationData;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseBox extends Fragment {

    private View mView;
    private TextView mQuest;
    private EditText mAnswer;
    private String question;
    private String answer;
    private String userName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getArguments() != null){
            question = this.getArguments().getString("question");
            answer = this.getArguments().getString("answer");
            userName = this.getArguments().getString("userName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View leeren
        if (container != null) {
            container.removeAllViews();
        }
        mView = inflater.inflate(R.layout.fragment_responsebox_layout, container, false);
        mQuest = (TextView) mView.findViewById(R.id.tutorQuestion);
        mAnswer = (EditText) mView.findViewById(R.id.tutorResponseEdit);

        if(this.getArguments() != null) {
            mQuest.setText(question);
            mAnswer.setText(answer);

            Button done = (Button) mView.findViewById(R.id.tutorSubmit);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        saveToJSON(question, mAnswer.getText().toString(), userName);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return mView;
    }

    private void saveToJSON(String question, String text, String userName) throws NoSuchFieldException {
        try {
            JSONObject tutormails = IntegrationData.getInstance().mailResources.get("tutormails");
            JSONObject mails = (JSONObject)tutormails.get("mails");
            JSONArray ja = (JSONArray)mails.get("mail");

            for(int i =0; i<ja.length(); i++) {
                JSONObject mail = (JSONObject) ja.get(i);
                if(userName.equals(mail.getString("user")) &&
                        question.equals(mail.getString("question"))){
                    mail.put("answer", text);
                }
            }
            Fragment frg = getFragmentManager().findFragmentById(R.id.tutorList);
            getFragmentManager().beginTransaction().replace(R.id.tutorList, frg).commit();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
