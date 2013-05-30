package com.test.facebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.facebook.widget.ProfilePictureView;
import com.test.facebook.data.Friend;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 5/30/13
 * Time: 12:46 PM
 */
public class FriendsListAdapter extends ArrayAdapter<Friend>{

    public FriendsListAdapter(Context context, int textViewResourceId, List<Friend> friends) {
        super(context, textViewResourceId, friends);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = vi.inflate(R.layout.friends_list_item, null);

        Friend friend = getItem(position);

        if(friend.friendId != null){
            ((ProfilePictureView)view.findViewById(R.id.friends_avatar)).setProfileId(friend.friendId);
        }
        ((TextView)view.findViewById(R.id.friends_name)).setText(friend.name);

        return view;
    }
}
