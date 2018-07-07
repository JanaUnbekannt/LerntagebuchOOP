package com.example.students.lerntagebuchoop.fragment;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.students.lerntagebuchoop.R;
import com.example.students.lerntagebuchoop.adapter.TaskListAdapter;
import com.example.students.lerntagebuchoop.model.TaskItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tasks.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tasks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tasks extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String taskName;
    private List<String> questionList;
    View mView;
    String title;
    ListView mListView;
    //ArrayAdapter<String> mAdapter;
    TaskListAdapter mAdapter;
    ArrayList<TaskItem> taskItems;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView mtitle;

    public Tasks() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tasks.
     */
    // TODO: Rename and change types and number of parameters
    public static Tasks newInstance(String param1, String param2) {
        Tasks fragment = new Tasks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(this.getArguments() == null){
            try {
                this.taskName = findCurrentLecture();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            this.taskName = (String) this.getArguments().get("taskName");
        }

        int id = getResources().getIdentifier(this.taskName, "xml", getContext().getPackageName());
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        taskItems = new ArrayList<>();

        //finde Titel und Fragen für aktuelles Thema
        XmlPullParser rp = getResources().getXml(id);
        TaskItem ti = null;
        try {
            while (rp.next() != XmlResourceParser.END_DOCUMENT) {
                switch(rp.getEventType()) {
                    case XmlResourceParser.START_TAG:
                        switch (rp.getName()) {
                            case "description":
                                ti = new TaskItem();
                                ti.setDescription(rp.nextText());
                                break;
                            case "text":
                                if(ti != null) {
                                    ti.setText(rp.nextText());
                                    taskItems.add(ti);
                                }
                                break;
                            case "topic":
                            title = rp.nextText();
                            break;
                        }
                        break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String findCurrentLecture() throws IllegalAccessException, ParseException {
        // Alle XMLs nach DATE-Tag durchsuchen
        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
        String currentLecture = null;
        Date today = sdf.parse(sdf.format(new Date()));
        Date currentLectureDate = null;
        Date tempDate = null;

        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        Field[] xmlFields = R.xml.class.getFields();

        for(int i=0; i<xmlFields.length; i++){
            // finde VL mit aktuellem Datum
            XmlPullParser rp = getResources().getXml(xmlFields[i].getInt(null));
            try {
                while (rp.next() != XmlResourceParser.END_DOCUMENT) {
                    switch (rp.getEventType()) {
                        case XmlResourceParser.START_TAG:
                            if("date".equals(rp.getName())){
                                tempDate = sdf.parse(rp.nextText());
                                if(i == 0){
                                    currentLectureDate = tempDate;
                                    currentLecture = xmlFields[i].getName();
                                }
                                else if(tempDate.after(currentLectureDate) &&
                                        tempDate.before(today)){
                                    currentLectureDate = tempDate;
                                    currentLecture = xmlFields[i].getName();
                                }
                            }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        return currentLecture;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_tasks, container, false);
        mtitle = (TextView) mView.findViewById(R.id.lectureTitle);
        mtitle.setText(title);
        mListView = (ListView) mView.findViewById(R.id.taskList);
        mAdapter = new TaskListAdapter(getContext(), taskItems);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
