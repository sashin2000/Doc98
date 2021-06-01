package com.xmind.doc98;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionListActivity extends AppCompatActivity {

    String sid = "0";
    String did = "0";
    String hid = "0";

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        sid = bundle.getString("SID");
        did = bundle.getString("DID");
        hid = bundle.getString("HID");

        lv  = findViewById(R.id.session_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSessions();
    }

    public void loadSessions(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://beezzserver.com/sashin/channeling/session/index.php?did=" + did +"&hid="+ hid +"&sid="+ sid + "";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        setSessions(response);
                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(request);
    }

    public void setSessions(JSONArray response){
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        try{
            for (int i=0; i < response.length();i++){
                JSONObject obj = response.getJSONObject(i);
                HashMap<String,String> map = new HashMap<>();
                map.put("id",obj.getString("id"));
                map.put("doctor_name",obj.getString("doctor_name"));
                map.put("speciality_name",obj.getString("speciality_name"));
                map.put("date_time",obj.getString("date_time"));
                map.put("hospital_name",obj.getString("hospital_name") + " - " + obj.getString("hospital_place"));
                map.put("next",obj.getString("next"));
                list.add(map);
            }

            //1. Layout
            int layout = R.layout.single_session;
            //2. Views
            int[] views = {R.id.session_id, R.id.doctor_name, R.id.speciality_name, R.id.hospital_name, R.id.date, R.id.no};
            //3. Columns
            String[] columns = {"id", "doctor_name", "speciality_name", "hospital_name", "date_time", "next"};

            SimpleAdapter adapter = new SimpleAdapter(this, list, layout, columns, views);
            lv.setAdapter(adapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void book(View v){
        LinearLayout ll = (LinearLayout) v.getParent();
        TextView tv = ll.findViewById(R.id.session_id);

        String sessionId = tv.getText().toString();

        Toast.makeText(this, "Session Id - "+ sessionId, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("SID",sessionId);
        startActivity(intent);
    }
}