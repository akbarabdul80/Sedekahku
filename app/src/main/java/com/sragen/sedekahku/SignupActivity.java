package com.sragen.sedekahku;

import android.content.Context;
import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {

    ConnectivityManager conMgr;
    private String url = Server.URL + "register.php";
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        pDialog = new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.PROGRESS_TYPE);

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

        Button signin, signup;
        signin = findViewById(R.id.sign_in_register);
        signup = findViewById(R.id.sign_up_register);

        final EditText ed_username, ed_email, ed_password;

        ed_username = findViewById(R.id.input_name);
        ed_email = findViewById(R.id.username_sign_up);
        ed_password = findViewById(R.id.password_sign_up);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username, email, password, emailpattern;

                username = ed_username.getText().toString();
                email = ed_email.getText().toString();
                password = ed_password.getText().toString();
                emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#1591F9"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();

                //cek kolom kosong
                if (username.trim().length() == 0) {
                    ed_username.setError("Username tidak boleh kosong");
                    pDialog.dismissWithAnimation();
                    pDialog.dismiss();
                }else if (email.trim().length() == 0) {
                    ed_email.setError("Email tidak boleh kosong");
                    pDialog.dismissWithAnimation();
                    pDialog.dismiss();
                }else if (!email.trim().matches(emailpattern)){
                    ed_email.setError("Periksa Email anda");
                    pDialog.dismissWithAnimation();
                }else if (password.trim().length() < 8){
                    ed_password.setError("Min Password 8 Karakter");
                    pDialog.dismissWithAnimation();
                    pDialog.dismiss();
                }else {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        registerproses(username, email, password);
                    } else {
                        View noconection1 = getLayoutInflater().inflate(R.layout.activity_noconnection, null);
                        final BottomSheetDialog dialog = new BottomSheetDialog(SignupActivity.this);
                        pDialog.dismissWithAnimation();
                        pDialog.dismiss();
                        dialog.setContentView(noconection1);
                        dialog.show();
                    }
                }

//                startActivity(new Intent(SignupActivity.this, DonasiMakanan.class));
//                finish();
            }
        });

    }
    private void registerproses(final String username,final String email, final String password){
        Log.e("Response", "1");

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    Log.e("Response", String.valueOf(jObj));

                    if (success == 1){
                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(SignupActivity.this, jObj.getString("message"), Toast.LENGTH_LONG).show();

                        //Berpindah Dashboard
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }else {
                        pDialog.dismissWithAnimation();
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), jObj.getString("message"), Toast.LENGTH_LONG).show();
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
                final BottomSheetDialog dialog = new BottomSheetDialog(SignupActivity.this);
                pDialog.dismissWithAnimation();
                pDialog.dismiss();
                dialog.setContentView(noconection1);
                dialog.show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        CustomVolleyRequest.getInstance(this).addToRequestQueue(strReq);
    }
}
