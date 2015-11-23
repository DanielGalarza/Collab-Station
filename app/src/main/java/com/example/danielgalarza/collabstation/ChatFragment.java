package com.example.danielgalarza.collabstation;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.util.Random;

/**
 * Created by danielgalarza on 11/22/15.
 */
public class ChatFragment extends android.support.v4.app.Fragment {


    private Firebase mFirebase;
    private ListView mMessagesView;
    private FirebaseListAdapter mAdapter;
    private EditText mMessageText;
    private Button mShootButton;
    private Random r = new Random();
    private String name = "User" + r.nextInt(9999); //userID during a chat.


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        /** FIREBASE **/
        //List View for chat messages.
        mMessagesView = (ListView) rootView.findViewById(R.id.message_list_view);
        Firebase.setAndroidContext(getActivity());  //Giving the Firebase an Android context.

        //Our Firebase Reference.
        mFirebase = new Firebase("https://collaborationstation.firebaseio.com/chat");

        //Setting a Firebase List Adapter to facilitate the name and message from each user.
        mAdapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class, android.R.layout.two_line_list_item, mFirebase) {
            @Override
            protected void populateView(View view, Chat c) {
                ((TextView)view.findViewById(android.R.id.text1)).setText(c.getName());
                ((TextView)view.findViewById(android.R.id.text2)).setText(c.getMessage());
            }
        };

        //giving the List View the Firebase List Adapter.
        mMessagesView.setAdapter(mAdapter);

        mMessageText = (EditText) rootView.findViewById(R.id.text_edit);  //Input for chat messages.
        mShootButton = (Button) rootView.findViewById(R.id.shoot_button); //Button to send message.

        mShootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = mMessageText.getText().toString();
                Chat c = new Chat(name, msg);
                mFirebase.push().setValue(c);
                mMessageText.setText("");

            }
        });

        return rootView;
    }

    //TEST
    public void createMessages() {

        //TEST MESSAGES
        Chat msg = new Chat("user1", "message 1");
        mFirebase.push().setValue(msg);

        Chat msg2 = new Chat("user2", "message 2");
        mFirebase.push().setValue(msg2);

        Chat msg3 = new Chat("user3", "message 3");
        mFirebase.push().setValue(msg3);

        Chat msg4 = new Chat("user4", "message 4");
        mFirebase.push().setValue(msg4);

        Chat msg5 = new Chat("user5", "message 5");
        mFirebase.push().setValue(msg5);

        Chat msg6 = new Chat("user6", "message 6");
        mFirebase.push().setValue(msg6);
    }


    /** FOR TESTING PURPOSES **/
    @Override
    public void onStart() {

        super.onStart();



        //FOR DEBUGGING PURPOSES
        mFirebase.limitToLast(2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {

                    Log.d("onDataChange: ", "I'm in the foreach loop");
                    Chat msg = msgSnapshot.getValue(Chat.class);
                    Log.d("Chat", msg.getName() + ": " + msg.getMessage());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        //Creating hard coded messages.
        //createMessages();

    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        //Cleans up the message board when app is destroyed.
        mAdapter.cleanup();
    }


}
