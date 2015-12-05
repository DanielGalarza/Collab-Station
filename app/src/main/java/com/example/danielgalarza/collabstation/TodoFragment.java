package com.example.danielgalarza.collabstation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by danielgalarza on 12/4/15.
 */
public class TodoFragment extends android.support.v4.app.Fragment {

    private Firebase mFirebase;
    private ListView mTodosView;
    private FirebaseListAdapter mTodoAdapter;
    private EditText mTitle;
    private EditText mDate;
    private EditText mDescription;
    private Button mCreateButton;
    private CheckBox mTodoComplete;
    private CheckBox mTodoCompleteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        /** FIREBASE **/
        //List View for chat messages.
        mTodosView = (ListView) rootView.findViewById(R.id.todo_list_view);
        Firebase.setAndroidContext(getActivity());  //Giving the Firebase an Android context.

        //Our Firebase Reference.
        mFirebase = new Firebase("https://collaborationstation.firebaseio.com/todo");

        //Setting a Firebase List Adapter to facilitate the name and message from each user.
        mTodoAdapter = new FirebaseListAdapter<Todo>(getActivity(), Todo.class, R.layout.todo_item_layout, mFirebase) {

            @Override
            protected void populateView(View view, Todo t) {

                ((TextView)view.findViewById(R.id.todo_title)).setText(t.getTitle());
                ((TextView)view.findViewById(R.id.todo_description)).setText(t.getDescription());
                ((TextView)view.findViewById(R.id.todo_date)).setText(t.getDate());
                ((CheckBox)view.findViewById(R.id.todo_complete)).setChecked(t.isTodoComplete());

            }
        };

        //giving the List View the Firebase List Adapter.
        mTodosView.setAdapter(mTodoAdapter);

        /************************   SETTING UP THE VIEWS    ***************************************/

        mTitle = (EditText) rootView.findViewById(R.id.new_title);
        mDate = (EditText) rootView.findViewById(R.id.new_date);
        mDescription = (EditText) rootView.findViewById(R.id.new_description);
        mTodoComplete = (CheckBox) rootView.findViewById(R.id.is_complete);
        mTodoCompleteList = (CheckBox) rootView.findViewById(R.id.todo_complete);
        mCreateButton = (Button) rootView.findViewById(R.id.create_button);

        mTitle.setText("Title for new item");
        mDate.setText("Date");
        mDescription.setText("Short description");

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle.setText("");
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDate.setText("");
            }
        });

        mDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDescription.setText("");
            }
        });

        /**********************************************************/

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting the inputs from user
                String title = mTitle.getText().toString();
                String date = mDate.getText().toString();
                String description = mDescription.getText().toString();

                //Push to Firebase
                Todo t = new Todo(title, date, description, mTodoComplete.isChecked());
                mFirebase.push().setValue(t);

                //Resetting the fields
                mTitle.setText("Title for new item");
                mDate.setText("Date");
                mDescription.setText("Short description");
                mTodoComplete.setChecked(false);
            }
        });

        return rootView;

    }


}
