package com.example.students.lerntagebuchoop.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.lang.reflect.Field;

public class EditAnswer extends Fragment{
    private String question;
    private String answer;
    private String lectureName;
    private View mView;
    private TextView mQuest;
    private TextView mAnswer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = this.getArguments().getString("question");
        answer = this.getArguments().getString("answer");
        lectureName = this.getArguments().getString("lectureName");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View leeren
        if (container != null) {
            container.removeAllViews();
        }

        mView = inflater.inflate(R.layout.fragment_editanswer, container, false);
        mQuest = (TextView) mView.findViewById(R.id.questView);
        mAnswer = (EditText) mView.findViewById(R.id.editAnswer);

        mQuest.setText(question);
        mAnswer.setText(answer);

        Button done = (Button) mView.findViewById(R.id.submitEdittedAnswer);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                    saveToJSON(question, mAnswer.getText().toString(), lectureName);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                getFragmentManager().popBackStack();
            }
        });

        return mView;
    }

    private void saveToJSON(String question, String text, String lectureName) throws NoSuchFieldException {
        try {
            JSONObject lecture = (JSONObject)(IntegrationData.getInstance().resources.get(lectureName)).get("lecture");
            JSONArray ja = (JSONArray)((JSONObject)lecture.get("questions")).get("task");
            for(int i =0; i<ja.length(); i++) {
                JSONObject task = (JSONObject) ja.get(i);
                if(question.equals(task.getString("description"))){
                    task.put("text", text);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
