package com.example.bruhshua.firebasechatapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;//Create a instance of "FirebaseDatabase"
    private ArrayList<String> messages = new ArrayList<>();//Create an ArrayList, or our "Bag", which will consist of our messages(String).

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The following 2 lines pushes the page up to make room for the keyboard.
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //Initialize the database variable with an instance of your Firebase.
        database = FirebaseDatabase.getInstance();
        getMessages();//Call this method. It's implemented below.

        ListView messagesListView = (ListView) findViewById(R.id.lvChatRoom);//Reference the ListView that's in your activity_main.xml
        MessagesAdapter adapter = new MessagesAdapter(messages,this);//Create a new adapter. Pass in the read messages along with the current context.
        messagesListView.setAdapter(adapter);//Set the created adapter to the listview.

        //From that instance, create a DatabaseReference object and reference a point in the database.
        final DatabaseReference chatRoomRef = database.getReference("chat_room");
        final EditText etMessage = (EditText) findViewById(R.id.etMessage);//Reference your EditText from "activity_main.xml"

        Button bSend = (Button) findViewById(R.id.bSend);//Reference your Button from "activity_main.xml"
        bSend.setOnClickListener(new View.OnClickListener() { //Set a listener, or, what happens when user clicks button.
            @Override
            public void onClick(View v) {//Following code executes when button is pressed.

                if(!etMessage.getText().toString().equals("")) {//If the user typed something into the EditText continue.

                    String message = etMessage.getText().toString();//Store what the user typed into a String.
                    chatRoomRef.push().child("message").setValue(message);//Add the new message to Firebase.
                    etMessage.setText("");//Reset the EditText to blank.

                    //The following 2 lines hides the keyboard.
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }else{//If the user didn't type anything, show them this message.
                    Toast.makeText(getApplicationContext(),"Enter a message.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void getMessages(){
        messages.clear();//If there are already messages in our bag, clear them out. We don't want duplicate messages.
        DatabaseReference chatRoomRef = database.getReference("chat_room");//Reference this point in your database.
        chatRoomRef.addValueEventListener(new ValueEventListener() {//Add a valuelistener. This is so that the "onDataChange" below executes whenever a new value is added to our database.
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){//Go through each message in the database.
                    String message = messageSnapshot.child("message").getValue().toString();//Grab that message
                    Log.d("getMessages", "message: " + message);//Display the message on our console in Android Studio.
                    messages.add(message);//Add the message to our ArrayList.("Bag")
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
