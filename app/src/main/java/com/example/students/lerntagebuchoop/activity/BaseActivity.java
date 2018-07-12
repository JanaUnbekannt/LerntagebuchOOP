package com.example.students.lerntagebuchoop.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.students.lerntagebuchoop.R;
import com.example.students.lerntagebuchoop.fragment.AllTopics;
import com.example.students.lerntagebuchoop.fragment.Chat;
import com.example.students.lerntagebuchoop.fragment.FeedbackQuestions;
import com.example.students.lerntagebuchoop.fragment.Settings;
import com.example.students.lerntagebuchoop.fragment.Tasks;
import com.example.students.lerntagebuchoop.fragment.Uncertainties;
import com.example.students.lerntagebuchoop.model.IntegrationData;

import org.json.JSONObject;

import java.util.HashMap;

public class BaseActivity extends AppCompatActivity implements AllTopics.OnFragmentInteractionListener,
                                                                Chat.OnFragmentInteractionListener,
                                                                FeedbackQuestions.OnFragmentInteractionListener,
                                                                Settings.OnFragmentInteractionListener,
                                                                Tasks.OnFragmentInteractionListener,
                                                                Uncertainties.OnFragmentInteractionListener{

    FragmentManager fragmentManager = getSupportFragmentManager();
    //Fragments
    AllTopics allTopics;
    Chat chat;
    FeedbackQuestions feedbackQuestions;
    Settings settings;
    Tasks currentTasks;
    Uncertainties uncertainties;

    TextView actionBarTopic = null;
    ImageButton actionBarNavigationButton;
    ImageButton actionBarSettingsButtonNotFinal;
    String actionBarTitle;
    String currentScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //Setze meine selbsterstellte ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();

        //TODO BackStack
        //Packe Tasks-Fragment in "fragment_layout"-Container des "activity_base.xml"-Layouts
        currentTasks = new Tasks();
        currentScreen = "Tasks";
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, currentTasks).commit();

        //ActionBar Titel anpassen
        actionBarTopic = (TextView) findViewById(R.id.action_bar_topic);
        actionBarTitle = getString(R.string.actionbar_topic_tasks);
        actionBarTopic.setText(actionBarTitle);


        //TODO BackStack
        //TODO Lizenz Icons im Impressum vermerken
        actionBarNavigationButton = (ImageButton) view.findViewById(R.id.action_bar_navigation);
        actionBarSettingsButtonNotFinal= (ImageButton)view.findViewById(R.id.action_bar_settings);

        actionBarNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentScreen){

                    case "AllTopics":

                        //Gehe zu Nachrichten
                        feedbackQuestions = new FeedbackQuestions();
                        currentScreen = "FeedbackQuestions";
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, feedbackQuestions).commit();

                        //Navigation-Button anpassen
                        actionBarNavigationButton.setImageResource(R.drawable._004_notebook);

                        //Add Settings-Button
                        actionBarSettingsButtonNotFinal.setImageResource(R.drawable._003_settings);

                        //ActionBar Titel anpassen
                        actionBarTitle = getString(R.string.actionbar_topic_feedback_questions);
                        actionBarTopic.setText(actionBarTitle);
                        break;

                    case "Chat":

                        //Gehe zu Nachrichten
                        feedbackQuestions = new FeedbackQuestions();
                        currentScreen = "FeedbackQuestions";
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, feedbackQuestions).commit();

                        //Navigation-Button anpassen
                        actionBarNavigationButton.setImageResource(R.drawable._004_notebook);

                        //Add Settings-Button
                        actionBarSettingsButtonNotFinal.setImageResource(R.drawable._003_settings);

                        //ActionBar Titel anpassen
                        actionBarTitle = getString(R.string.actionbar_topic_feedback_questions);
                        actionBarTopic.setText(actionBarTitle);
                        break;

                    case "FeedbackQuestions":

                        //Gehe zu Alle Themen
                        allTopics = new AllTopics();
                        currentScreen = "AllTopics";
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, allTopics).commit();

                        //Navigation-Button anpassen
                        actionBarNavigationButton.setImageResource(R.drawable._002_speech_bubble);

                        //Add Settings-Button
                        actionBarSettingsButtonNotFinal.setImageResource(R.drawable._003_settings);

                        //ActionBar Titel anpassen
                        actionBarTitle = getString(R.string.actionbar_topic_all_topics);
                        actionBarTopic.setText(actionBarTitle);
                        break;

                    case "Settings":

                        //TODO Gehe auf vorherigen Screen
                        allTopics = new AllTopics();
                        currentScreen = "AllTopics";
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, allTopics).commit();

                        //Navigation-Button anpassen
                        actionBarNavigationButton.setImageResource(R.drawable._002_speech_bubble);

                        //Add Settings-Button
                        actionBarSettingsButtonNotFinal.setImageResource(R.drawable._003_settings);

                        //ActionBar Titel anpassen
                        actionBarTitle = getString(R.string.actionbar_topic_all_topics);
                        actionBarTopic.setText(actionBarTitle);
                        break;

                    case "Tasks":

                        //Gehe zu Alle Themen
                        allTopics = new AllTopics();
                        currentScreen = "AllTopics";
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, allTopics).commit();

                        //Navigation-Button anpassen
                        actionBarNavigationButton.setImageResource(R.drawable._002_speech_bubble);

                        //Add Settings-Button
                        actionBarSettingsButtonNotFinal.setImageResource(R.drawable._003_settings);

                        //ActionBar Titel anpassen
                        actionBarTitle = getString(R.string.actionbar_topic_all_topics);
                        actionBarTopic.setText(actionBarTitle);
                        break;

                    case "Uncertainties":

                        //Gehe zu Aktuellen Aufgaben
                        currentTasks = new Tasks();
                        currentScreen = "Tasks";
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, currentTasks).commit();

                        //Navigation-Button anpassen
                        actionBarNavigationButton.setImageResource(R.drawable._001_left_arrow);

                        //Add Settings-Button
                        actionBarSettingsButtonNotFinal.setImageResource(R.drawable._003_settings);

                        //ActionBar Titel anpassen
                        actionBarTitle = getString(R.string.actionbar_topic_tasks);
                        actionBarTopic.setText(actionBarTitle);
                        break;


                }
            }
        });

        final ImageButton actionBarSettingsButton= (ImageButton)view.findViewById(R.id.action_bar_settings);

        actionBarSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gehe zu Einstellungen
                settings = new Settings();
                currentScreen = "Settings";
                fragmentManager.beginTransaction().replace(R.id.fragment_layout, settings).commit();

                //Navigation-Button anpassen
                actionBarNavigationButton.setImageResource(R.drawable._001_left_arrow);

                //Remove Settings-Button
                actionBarSettingsButton.setImageResource(android.R.color.transparent);

                //ActionBar Titel anpassen
                actionBarTitle = getString(R.string.actionbar_topic_settings);
                actionBarTopic.setText(actionBarTitle);
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
