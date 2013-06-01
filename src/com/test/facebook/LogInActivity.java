package com.test.facebook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.facebook.*;
import com.facebook.model.GraphUser;
import com.test.facebook.menu.ActivityWithMenu;
import com.test.facebook.menu.UserState;
import com.test.facebook.menu.OnMenuItemClick;

public class LogInActivity extends ActivityWithMenu {

    TextView informationTextView;
    Button logInButton, logOutButton, showFriendsButton;

    Session session;
    private GraphUser user;

    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        this.setUserState(UserState.LoggedOut);

        this.provideOnMenuItemClickListener(new OnMenuItemClick() {
            @Override
            public boolean onOptionsMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.show_location) {
//todo show location
                    return true;
                }
                if (item.getItemId() == R.id.show_friends) {
                    showFriends(user);
                    return true;
                }
                if (item.getItemId() == R.id.log_in) {
                    logIn();
                    return true;
                }
                if (item.getItemId() == R.id.log_out) {
                    logOut();
                    return true;
                }
                return false;
            }
        });

        informationTextView = (TextView) findViewById(R.id.textView);
        logInButton = (Button) findViewById(R.id.log_in_button);
        logOutButton = (Button) findViewById(R.id.log_out_button);
        showFriendsButton = (Button) findViewById(R.id.show_friends_button);

        updateUI();
    }

    private void updateUI(){
        if(this.userState == UserState.LoggedOut){
            setInformationText("", false);
            logInButton.setVisibility(View.VISIBLE);
            logOutButton.setVisibility(View.GONE);
            showFriendsButton.setVisibility(View.GONE);
        }
        if(this.userState == UserState.LoggedIn){
            setInformationText(getString(R.string.hello) + " " + user.getName() + "!", false);
            logInButton.setVisibility(View.GONE);
            logOutButton.setVisibility(View.VISIBLE);
            showFriendsButton.setVisibility(View.VISIBLE);

        }
    }

    public void loginClickListener(View v){
        logIn();
    }

    private void logIn() {
        if (session == null || !session.isOpened()) {
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage(getString(R.string.progress_dialog_message));
            progress.show();

            session = Session.openActiveSession(this, true, new Session.StatusCallback() {

                // callback when session changes state
                @Override
                public void call(final Session session, SessionState state, Exception exception) {
                    if (exception != null) {
                        progress.dismiss();
                        setInformationText(exception.getLocalizedMessage(), true);
                    } else if (state == SessionState.OPENED || state == SessionState.OPENED_TOKEN_UPDATED) {
                        makeLoginRequest(progress);
                    }
                }
            });
            session.getAccessToken();
        }
    }

    private void makeLoginRequest(final ProgressDialog progress) {
        // make request to the /me API
        Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

            // callback after Graph API response with user object
            @Override
            public void onCompleted(GraphUser u, Response response) {
                progress.dismiss();

                if (u != null) {
                    user = u;
                    LogInActivity.this.setUserState(UserState.LoggedIn);
                    updateUI();
                } else {
                    setInformationText(getString(R.string.unknown_error), true);
                }
            }
        });
    }

    public void showFriendsClickListener(View v){
        showFriends(user);
    }

    private void showFriends(GraphUser user) {
       if(user != null){
            Intent intent = new Intent(getBaseContext(), FriendsActivity.class);
            FriendsActivity.user = user;
            FriendsActivity.session = session;

            this.startActivity(intent);
        } else
           setInformationText(getString(R.string.unknown_error), true);
    }

    private void setInformationText(String text, boolean error){
        if(error){
            informationTextView.setTextColor(Color.RED);
        } else {
            informationTextView.setTextColor(Color.GRAY);
        }
        informationTextView.setText(text);
    }

    public void logOutClickListener(View v){
        logOut();
    }

    private void logOut() {
        session.closeAndClearTokenInformation();
        this.setUserState(UserState.LoggedOut);
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if ((exception instanceof FacebookOperationCanceledException ||
                        exception instanceof FacebookAuthorizationException)) {
            informationTextView.setText(exception.getLocalizedMessage());
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            informationTextView.setText(user.getFirstName());
        }
    }

}
