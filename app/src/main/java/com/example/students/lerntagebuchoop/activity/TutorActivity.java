package com.example.students.lerntagebuchoop.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.example.students.lerntagebuchoop.R;

public class TutorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = "Tutor View";
        setContentView(R.layout.activity_tutor_login);
        getSupportActionBar().setTitle(title);
    }

}
