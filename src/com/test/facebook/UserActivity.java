package com.test.facebook;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.test.facebook.data.Friend;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 5/30/13
 * Time: 12:00 PM
 */

public class UserActivity extends FragmentActivity {

    static GraphUser user;
    static Session session;

    TextView helloLabel;
    ListView friendsList;
    ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_info);

        helloLabel = (TextView) findViewById(R.id.hello_label);
        helloLabel.setText("Hello " + user.getName() + "!");

        friendsList = (ListView) findViewById(R.id.friends_list);

        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_pic);
        profilePictureView.setProfileId(user.getId());

        ArrayList<Friend> friends = createFriendsStub();
//        ArrayList<Friend> friends = fetchFriends();

        friendsList.setAdapter(new FriendsListAdapter(this, R.id.friends_list, friends));
    }

    private ArrayList<Friend> fetchFriends() {
//        user.getInnerJSONObject().getJSONArray("friends")
        return null;
    }

    private ArrayList<Friend> createFriendsStub() {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        for(int i = 0; i < 15; i++){
            Friend friend = new Friend();
            friend.name = user.getName() + "  " + i;
            friend.userId = user.getId();

            friends.add(friend);
        }
        return friends;
    }
}
