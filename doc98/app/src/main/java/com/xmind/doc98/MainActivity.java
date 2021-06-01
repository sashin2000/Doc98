package com.xmind.doc98;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView actvSpeciality, actvDoctor, actvHospital;
    Map<String, String> specialities = new HashMap<>();
    Map<String, String> doctors = new HashMap<>();
    Map<String, String> hospitals = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actvSpeciality = findViewById(R.id.speciality);
        actvDoctor = findViewById(R.id.doctor);
        actvHospital = findViewById(R.id.hospital);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSpecialities();
        loadDoctors();
        loadHospitals();
    }

    public void loadHospitals() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https:/beezzserver.com/sashin/channeling/hospital/";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.

                        /*System.out.println("Response" + response);
                        Toast.makeText(MainActivity.this, "Response : " + response, Toast.LENGTH_LONG).show();*/

                        setHospitals(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    public void setHospitals(JSONArray response){
        List<String> list = new ArrayList<>();
        for (int i=0; i< response.length(); i++){
            try {
                JSONObject obj = response.getJSONObject(i);
                list.add(obj.getString("name"));
                hospitals.put(obj.getString("name"), obj.getString("id"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter adapter = new ArrayAdapter(this, layout, list);
        actvHospital.setAdapter(adapter);
    }

    public void loadDoctors() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https:/beezzserver.com/sashin/channeling/doctor/";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.

                       /* System.out.println("Response" + response);
                        Toast.makeText(MainActivity.this, "Response : " + response, Toast.LENGTH_LONG).show();*/

                        setDoctors(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    public void setDoctors(JSONArray response){
        List<String> list = new ArrayList<>();
        for (int i=0; i< response.length(); i++){
            try {
                JSONObject obj = response.getJSONObject(i);
                list.add(obj.getString("name"));
                doctors.put(obj.getString("name"), obj.getString("id"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter adapter = new ArrayAdapter(this, layout, list);
        actvDoctor.setAdapter(adapter);
    }

    public void loadSpecialities(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https:/beezzserver.com/sashin/channeling/speciality/";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.

                       /* System.out.println("Response" + response);
                        Toast.makeText(MainActivity.this, "Response : " + response, Toast.LENGTH_LONG).show();*/

                       setSpecialities(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    public void setSpecialities(JSONArray response){
        List<String> list = new ArrayList<>();
        for (int i=0; i< response.length(); i++){
            try {
                JSONObject obj = response.getJSONObject(i);
                list.add(obj.getString("name"));
                specialities.put(obj.getString("name"), obj.getString("id"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter adapter = new ArrayAdapter(this, layout, list);
        actvSpeciality.setAdapter(adapter);
    }

    public void search(View v){
        String specilialityName = actvSpeciality.getText().toString();
        String doctorName = actvDoctor.getText().toString();
        String hospitalName = actvHospital.getText().toString();

        String sid = specialities.get(specilialityName);
        String did = doctors.get(doctorName);
        String hid = hospitals.get(hospitalName);

        if (sid == null){
            sid="0";
        }

        if (hid == null){
            hid="0";
        }

        if (did == null){
            did="0";
        }

        Intent intent = new Intent(this, SessionListActivity.class);
        intent.putExtra("SID",sid);
        intent.putExtra("DID",did);
        intent.putExtra("HID",hid);

        Toast.makeText(this, "DID="+did, Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    public void reset(View v){

    }
}