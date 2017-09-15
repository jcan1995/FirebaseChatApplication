package com.example.bruhshua.firebasechatapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by bruhshua on 9/6/17.
 */

public class MessagesAdapter extends BaseAdapter {

    private ArrayList<String> messages;
    private Context mContext;

    public MessagesAdapter(ArrayList<String> messages, Context context){//Constructor which receives the read messages.
        this.messages = messages;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return messages.size();//Return the size of the ArrayList. Which is the number of how many messages are in the ArrayList.
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);//Get a certain message from the ArrayList depending on the position.
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//A function which we need to implement such that all of the messages populate on the ListView.

        //Get the LayoutInflater so that we can "Inflate" our layout view of each cell. (We'll make that in a bit)
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflate, or show, our layout that we want to show for each ListView cell.
        View v = inflater.inflate(R.layout.my_trips_list_view_item, parent, false);

        //Get a certain message from the ArrayList. Depends on the position number which starts from 0;
        String message = (String) getItem(position);//Get a single message from our ArrayList ("Bag").

        //Reference the TextView in our layout file.
        TextView tvMessage = (TextView) v.findViewById(R.id.tvMessage);
        //Set the text to the message we got from our ArrayList.
        tvMessage.setText(message);
        return v;
    }
}
