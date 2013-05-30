package com.test.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import com.facebook.*;
import com.facebook.model.GraphUser;

public class LogInActivity extends FragmentActivity {

    TextView textView;
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

        textView = (TextView) findViewById(R.id.textView);
    }

    public void loginClickListener(View v){
        // start Facebook Login
        session = Session.openActiveSession(this, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(final Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    // make request to the /me API
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                Intent intent = new Intent(getBaseContext(), UserActivity.class);
                                UserActivity.user = user;
                                UserActivity.session = session;

                                LogInActivity.this.startActivity(intent);

                                textView.setText("Hello " + user.getName() + "!");
                            }
                        }

                    });
                }

                if(exception != null){
                    textView.setText(exception.getLocalizedMessage());
                }
            }
        });

        session.getAccessToken();
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
            textView.setText(exception.getLocalizedMessage());
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            textView.setText(user.getFirstName());
        }
    }

}
