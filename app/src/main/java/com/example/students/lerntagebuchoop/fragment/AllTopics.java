package com.example.students.lerntagebuchoop.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.students.lerntagebuchoop.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllTopics.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllTopics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllTopics extends Fragment {
    View mView;
    ListView mListView;
    ArrayAdapter<String> mAdapter;
    String [] parsedXml;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AllTopics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllTopics.
     */
    // TODO: Rename and change types and number of parameters
    public static AllTopics newInstance(String param1, String param2) {
        AllTopics fragment = new AllTopics();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parsedXml = getResources().getStringArray(R.array.topics);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View leeren
        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_all_topics, container, false);
        mListView = (ListView) mView.findViewById(R.id.allTopicsListView);
        mAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_dropdown_item_1line, android.R.id.text1, parsedXml);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                String nextTasks = ((TextView) view).getText().toString();
                nextTasks = nextTasks.toLowerCase();
                nextTasks = nextTasks.replaceAll("\\s+",""); //XML entspricht lowerCase ohne whitespace
                Tasks tasks = new Tasks();
                Bundle args = new Bundle();
                args.putString("taskName", nextTasks);
                tasks.setArguments(args); // hier wird der Name weitergegebn
                ft.replace(R.id.fragment_layout, tasks).commit();
            }

        });

        return mView;
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
