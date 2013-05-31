package com.test.facebook;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.test.facebook.data.Friend;
import com.test.facebook.utils.FriendsParser;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 5/30/13
 * Time: 12:00 PM
 */

public class FriendsActivity extends FragmentActivity {

    static GraphUser user;
    static Session session;

    TextView helloLabel;
    ListView friendsList;
    ProfilePictureView profilePictureView;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_info);

        helloLabel = (TextView) findViewById(R.id.hello_label);
        helloLabel.setText("Hello " + user.getName() + "!");

        friendsList = (ListView) findViewById(R.id.friends_list);

        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_pic);
        profilePictureView.setProfileId(user.getId());

        progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.progress_dialog_message));

        fetchFriends();
    }

    private void fetchFriends() {
        progress.show();

        String fqlQuery = "select uid, name from user where uid in (select uid2 from friend where uid1 = me())";
        Bundle params = new Bundle();
        params.putString("q", fqlQuery);

        Request request = new Request(session, "/fql", params, HttpMethod.GET, new Request.Callback(){
            public void onCompleted(Response response) {
                progress.dismiss();
                try {
                    setFriendsListAdapter(FriendsParser.parseResponse(response));
                } catch (JSONException e) {
//  todo handle exception
                    e.printStackTrace();
                }
            }
        });
        Request.executeBatchAsync(request);
    }

    private void setFriendsListAdapter(ArrayList<Friend> friends) {
        friendsList.setAdapter(new FriendsListAdapter(this, R.id.friends_list, friends));
    }

    private ArrayList<Friend> createFriendsStub() {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        for(int i = 0; i < 15; i++){
            Friend friend = new Friend();
            friend.name = user.getName() + "  " + i;
            friend.friendId = user.getId();

            friends.add(friend);
        }
        return friends;
    }
}
