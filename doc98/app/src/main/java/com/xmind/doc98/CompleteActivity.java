package com.xmind.doc98;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String msg = bundle.getString("msg");
        TextView tv = findViewById(R.id.message);
        tv.setText(msg.split("\"")[5]); //Not Recommended
    }

    public void goToHome(View v){
        startActivity(new Intent(this,MainActivity.class));
    }
}