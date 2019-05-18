package edu.stts.quizmdp6541;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences pref;
    EditText etUser;
    EditText etPass;
    String url = "http://simpleservicesoa2018.000webhostapp.com/login.php";
//    String url = "http://simpleservicesoa2018.000webhostapp.com/login.php";
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getApplicationContext().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        etUser = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPass);
        rq = Volley.newRequestQueue(this);
    }

    public void loginClick(View v) {
        final String username = etUser.getText().toString();
        final String pass = etPass.getText().toString();

        if (!username.equals("") && !pass.equals("")) {
            StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject data = new JSONObject(response);
                        String message = data.getString("message");
                        String code = data.getString("code");
                        if (code.equals("1")) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("username", username);
                            saveUserLogin(username);
                            startActivity(i);
                            finish();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } else if (code.equals("-1")) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map getParams(){
                    Map params = new HashMap();
                    params.put("username", username);
                    params.put("password", pass);

                    return params;
                }

                @Override
                public Map getHeaders() throws AuthFailureError {
                    Map params = new HashMap();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };
            rq.add(req);
        }
    }

    private void saveUserLogin(String username){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username",username);
        editor.apply();
    }
}
