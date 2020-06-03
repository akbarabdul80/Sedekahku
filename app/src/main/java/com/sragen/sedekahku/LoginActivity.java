package com.sragen.sedekahku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sragen.sedekahku.app.CustomVolleyRequest;
import com.sragen.sedekahku.app.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    public static final String getData = "getData";
    private String url = Server.URL + "login.php";
    String tag_json_obj = "json_obj_reg";
    ConnectivityManager conMgr;
    Boolean session = false;
    String id, username, email, token;
    SharedPreferences sharedPreferences;

    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);


        sharedPreferences = getSharedPreferences(getData, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean("session_status", false);
        id = sharedPreferences.getString("id",null);
        username = sharedPreferences.getString("username", null);
        email = sharedPreferences.getString("email", null);
        token = sharedPreferences.getString("token", null);

        if (session){
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            finish();
            startActivity(intent);
        }

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            }else {
                Toast.makeText(getApplicationContext(), "Periksa Koneksi Internet anda",
                        Toast.LENGTH_LONG).show();

            }
        }

        Button signup, signin;
        final EditText txt_username, txt_password;

        signin = findViewById(R.id.sign_in_login);
        signup = findViewById(R.id.sign_up_login);

        txt_username = findViewById(R.id.username);
        txt_password = findViewById(R.id.password);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                pDialog.getProgressHelper().setBarColor(Color.parseColor("#1591F9"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();

                //cek kolom kosong
                if (username.trim().length() == 0) {
                    txt_username.setError("Username tidak boleh kosong");
                    pDialog.dismissWithAnimation();
                    pDialog.dismiss();
                }else if (password.trim().length() < 8){
                    txt_password.setError("Min Password 8 Karakter");
                    pDialog.dismissWithAnimation();
                    pDialog.dismiss();
                }else {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        loginproses(username, password);
                    } else {
                        View noconection1 = getLayoutInflater().inflate(R.layout.activity_noconnection, null);
                        final BottomSheetDialog dialog = new BottomSheetDialog(LoginActivity.this);
                        pDialog.dismissWithAnimation();
                        pDialog.dismiss();
                        dialog.setContentView(noconection1);
                        dialog.show();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void loginproses(final String username, final String password){
        Log.e("Response", "1");

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    Log.e("Response", String.valueOf(jObj));

                    if (success == 1){
                        String id =jObj.getString("id");
                        String username =jObj.getString("username");
                        String email =jObj.getString("email");
                        String token = jObj.getString("token");
                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(LoginActivity.this, jObj.getString("message"), Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("session_status", true);
                        editor.putString("id", id);
                        editor.putString("username", username);
                        editor.putString("email", email);
                        editor.putString("token", token);
                        editor.commit();
                        pDialog.dismissWithAnimation();
                        pDialog.dismiss();


                        //Berpindah Dashboard
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("token", token);
                        finish();
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), jObj.getString("message"), Toast.LENGTH_LONG).show();
                        pDialog.dismissWithAnimation();
                        pDialog.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", "Login Error : " + error.getMessage());
                View noconection1 = getLayoutInflater().inflate(R.layout.activity_noconnection, null);
                final BottomSheetDialog dialog = new BottomSheetDialog(LoginActivity.this);
                dialog.setContentView(noconection1);
                dialog.show();
                pDialog.dismissWithAnimation();
                pDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        CustomVolleyRequest.getInstance(this).addToRequestQueue(strReq);
    }
}
