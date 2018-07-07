package com.example.students.lerntagebuchoop.activity;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.students.lerntagebuchoop.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Setze meine selbsterstellte ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view =getSupportActionBar().getCustomView();

        //Suche meine Button-Elemente über ihre ID
        ImageButton actionBarNavigationButton= (ImageButton)view.findViewById(R.id.action_bar_navigation);
        ImageButton actionBarSettingsButton= (ImageButton)view.findViewById(R.id.action_bar_settings);

        //Button sind im Login-Screen inaktiv
        actionBarNavigationButton.setVisibility(View.INVISIBLE);
        actionBarSettingsButton.setVisibility(View.INVISIBLE);

        //ActionBar Titel setzen
        TextView actionBarTopic = (TextView) findViewById(R.id.action_bar_topic);
        String actionBarTitle = getString(R.string.actionbar_topic_login);
        actionBarTopic.setText(actionBarTitle);


        //Logik für Login-Button
        Button buttonLogin= (Button)findViewById(R.id.login_button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText username = findViewById(R.id.username);
                String usernameValue = username.getText().toString();
                TextInputEditText password = findViewById(R.id.password);
                String passwordValue = password.getText().toString();
                if(!"".equals(usernameValue) && !"".equals(passwordValue)){
                    Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
                    LoginActivity.this.startActivity(intent);
                }
                else{
                    if("".equals(usernameValue)) {
                        username.setError("Bitte Nutzernamen eingeben!");
                    }
                    if("".equals(passwordValue)){
                        password.setError("Bitte Passwort eingeben!");
                    }
                }

            }
        });


    }
}
