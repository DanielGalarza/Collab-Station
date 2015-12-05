package com.example.danielgalarza.collabstation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
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
    private FirebaseListAdapter mChatAdapter;
    private EditText mMessageText;
    private Button mShootButton;
    private Button mShootLocation;
    private Random r = new Random();
    String name = "User" + r.nextInt(9999); //userID during a chat.


    private double lat;      //Latitude
    private double lon;      //Longitude
    private LocationManager locationManager;
    private Criteria criteria;
    private Location myLocation;
    private String provider;        //Name of the best location provider: GPS, WIFI, CELLULAR.
    String mapsURL;
    LocationListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        setUpLocStuff();

        /** FIREBASE **/
        //List View for chat messages.
        mMessagesView = (ListView) rootView.findViewById(R.id.message_list_view);
        Firebase.setAndroidContext(getActivity());  //Giving the Firebase an Android context.

        //Our Firebase Reference.
        mFirebase = new Firebase("https://collaborationstation.firebaseio.com/chat");

        //Setting a Firebase List Adapter to facilitate the name and message from each user.
        mChatAdapter = new FirebaseListAdapter<Chat>(getActivity(), Chat.class, R.layout.message_item_layout, mFirebase) {

            @Override
            protected void populateView(View view, Chat c) {

                ((TextView)view.findViewById(R.id.user)).setText(c.getName());
                ((TextView)view.findViewById(R.id.message)).setText(c.getMessage() + "    " + c.getMapURL());

            }
        };

        //giving the List View the Firebase List Adapter.
        mMessagesView.setAdapter(mChatAdapter);

        /***** VIEW STUFF *****/
        mMessageText = (EditText) rootView.findViewById(R.id.text_edit);  //Input for chat messages.
        mShootButton = (Button) rootView.findViewById(R.id.shoot_button); //Button to send message.
        mShootLocation = (Button) rootView.findViewById(R.id.send_location);



        mShootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = mMessageText.getText().toString();
                Chat c = new Chat(name, msg, "");
                mFirebase.push().setValue(c);
                mMessageText.setText("");

            }
        });


        mShootLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg;
                Chat c;

                if(mapsURL == null) {
                    c  = new Chat(name,"Please wait for app to pin point location", "");
                    mFirebase.push().setValue(c);
                    mMessageText.setText("");
                    Log.d("shoot button", "mapsURL is null");

                } else {
                    msg = mMessageText.getText().toString();
                    c = new Chat(name, msg, mapsURL);
                    mFirebase.push().setValue(c);
                    mMessageText.setText("");
                    Log.d("shoot button", "mapsURL not null");
                }
            }
        });

        return rootView;

    }//End of onCreateView

    /*********************   GET LOCATION AS URL   ************************************************/

    public void getLocation(Location loc){
        Log.d("LOCATION", "LAT: " + loc.getLatitude() + "     LONG: " + loc.getLongitude());
        mapsURL = "https://maps.google.com/?q=" + loc.getLatitude() + "," + loc.getLongitude();
    }

    /*********************   REQUESTING LOCATION UPDATES   ****************************************/

    private void reqLocUpdates(String provider, int time, int minDistance, LocationListener listener) {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(provider, time, 0, listener);
        }
    }

    /*********************   SETTING UP LOCATION SENSORS  *****************************************/

    private void setUpLocStuff() {

        //Prompt the user to enable location if location isn't enabled.

        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        // New criteria object to retrieve provider
        criteria = new Criteria();

        //name of the best provider
        provider = locationManager.getBestProvider(criteria, true);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            // Get last known location
            myLocation = locationManager.getLastKnownLocation(provider);
        }

        if(myLocation != null) {

            // Get the lat and lng of current location
            lat = myLocation.getLatitude();
            lon = myLocation.getLongitude();
        }

        /***********************    LOCATION LISTENER    ***************************************/
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                getLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                //
            }

            @Override
            public void onProviderEnabled(String s) {
                //
            }

            @Override
            public void onProviderDisabled(String s) {
                //
            }
        };

        reqLocUpdates(provider, 1, 0, listener);

    }// End of setUpMap()

    /****************************  TEST MESSAGES  *************************************************/


    public void createMessages() {

        //TEST MESSAGES
        Chat msg = new Chat("user1", "message 1", "https://maps.google.com/?q=39.73279097979197,-104.98643552741697");
        mFirebase.push().setValue(msg);

        Chat msg2 = new Chat("user2", "message 2", "https://maps.google.com/?q=39.73279097979197,-104.98643552741697");
        mFirebase.push().setValue(msg2);

        Chat msg3 = new Chat("user3", "message 3", "");
        mFirebase.push().setValue(msg3);

        Chat msg4 = new Chat("user4", "message 4", "https://maps.google.com/?q=39.73279097979197,-104.98643552741697");
        mFirebase.push().setValue(msg4);

        Chat msg5 = new Chat("user5", "message 5", "");
        mFirebase.push().setValue(msg5);

        Chat msg6 = new Chat("user6", "message 6", "https://maps.google.com/?q=39.73279097979197,-104.98643552741697");
        mFirebase.push().setValue(msg6);
    }

    /**************************   FRAGMENT LIFE CYCLE       ***************************************/

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
        mChatAdapter.cleanup();

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            // Get last known location
            locationManager.removeUpdates(listener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            // resume updates
            reqLocUpdates(provider, 1, 0, listener);
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        // Remove updates
        locationManager.removeUpdates(listener);

        }
    }

}
