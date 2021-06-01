package com.xmind.doc98;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BookActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    String sessionId = "";
    EditText etName, etEmail, etNic, etMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sessionId = bundle.getString("SID");

        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etNic = findViewById(R.id.nic);
        etMobile = findViewById(R.id.mobile);
    }

    public void confirm(View v){
        final String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        final String nic = etNic.getText().toString();
        final String mobile = etMobile.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://beezzserver.com/sashin/channeling/appointment/insert.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, this, this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("nic", nic);
                params.put("mobile", mobile);
                params.put("session_id", sessionId);
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onResponse(String response) {
        //Toast.makeText(this, "Success " + response, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,CompleteActivity.class);
        intent.putExtra("msg", response);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}