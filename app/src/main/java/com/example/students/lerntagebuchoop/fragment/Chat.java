package com.example.students.lerntagebuchoop.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.students.lerntagebuchoop.R;
import com.example.students.lerntagebuchoop.activity.BaseActivity;
import com.example.students.lerntagebuchoop.adapter.ChatAdapter;
import com.example.students.lerntagebuchoop.model.ChatItem;
import com.example.students.lerntagebuchoop.model.IntegrationData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Chat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Chat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Chat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;
    private ListView listView;
    private EditText message;
    private ImageView send;
    private ChatAdapter adapter;
    private ArrayList<ChatItem> chatItems;
    private ChatItem chatItem;

    public Chat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chat.
     */
    // TODO: Rename and change types and number of parameters
    public static Chat newInstance(String param1, String param2) {
        Chat fragment = new Chat();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Get my TextViews and Images
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        listView = (ListView) view.findViewById(R.id.chat_list_view);
        message = (EditText)view.findViewById(R.id.chat_message_edit_text);
        send = (ImageView)view.findViewById(R.id.chat_send_button);
        chatItems = new ArrayList<ChatItem>();
        getChatItems();
        ((BaseActivity)getActivity()).setActionBarTopic("Chat");




        //Set imageButton on OnClickListener
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                chatItem = new ChatItem();
                chatItem.setMessage(message.getText().toString());
                chatItems.add(chatItem);
                adapter = null;
                adapter = new ChatAdapter(getActivity(), chatItems);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                message.getText().clear();
                // Frage an Tutor abspeichern
                try {
                    JSONObject tutormails = IntegrationData.getInstance().mailResources.get("tutormails");
                    JSONObject mails = (JSONObject) tutormails.get("mails");
                    JSONArray ja = (JSONArray) mails.get("mail");
                    JSONObject mail = new JSONObject();
                    mail.put("user", "0815");
                    mail.put("lecture", getArguments().get("lecture"));
                    mail.put("question", chatItem.getMessage()) ;
                    mail.put("answer", "");
                    ja.put(mail);
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


            }

        });
        // Inflate the layout for this fragment
        return view;
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
        chatItems = new ArrayList<ChatItem>();
        getChatItems();
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

    private void getChatItems(){
        try {
            JSONObject tutormails = IntegrationData.getInstance().mailResources.get("tutormails");
            JSONObject mails = (JSONObject) tutormails.get("mails");
            JSONArray ja = (JSONArray) mails.get("mail");
            for(int i =0; i<ja.length(); i++) {
                JSONObject mail = (JSONObject) ja.get(i);
                if("0815".equals(mail.getString("user")) &&
                        getArguments().get("lecture").equals(mail.getString("lecture"))){
                    chatItem = new ChatItem();
                    chatItem.setMessage(mail.getString("question"));
                    chatItems.add(chatItem);
                    chatItem = new ChatItem();
                    chatItem.setMessage(mail.getString("answer"));
                    chatItems.add(chatItem);
                    adapter = new ChatAdapter(getActivity(), chatItems);
                    listView.setAdapter(adapter);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
