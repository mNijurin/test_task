package com.test.facebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void loginClickListener(View v){
        Toast.makeText(getBaseContext(), "asdf", Toast.LENGTH_SHORT).show();
    }
}
