package com.test.facebook.utils;

import com.facebook.Response;
import com.test.facebook.data.Friend;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 5/30/13
 * Time: 7:11 PM
 */
public class FriendsParser {
    public static ArrayList<Friend> parseResponse(Response response) throws JSONException {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        JSONObject jsonResponse = response.getGraphObject().getInnerJSONObject();
        JSONArray jsonResponseArray = jsonResponse.getJSONArray("data");

        for(int i = 0; i < jsonResponseArray.length(); i++){
            JSONObject jsonFriend = jsonResponseArray.getJSONObject(i);

            Friend friend = new Friend();
            friend.friendId = jsonFriend.getString("uid");
            friend.name = jsonFriend.getString("name");
            friends.add(friend);
        }

        return friends;
    }
}
