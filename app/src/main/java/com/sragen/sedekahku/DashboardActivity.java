package com.sragen.sedekahku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sragen.sedekahku.Adapter.SliderUtils;
import com.sragen.sedekahku.Adapter.ViewPagerAdapter;
import com.sragen.sedekahku.app.CustomVolleyRequest;
import com.sragen.sedekahku.app.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    RequestQueue rq;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    SharedPreferences sharedPreferences;

    String request_url = Server.URL + "slide.php";
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Log.e("eroro", "tayo1" );

        pDialog = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#1591F9"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        rq = CustomVolleyRequest.getInstance(this).getRequestQueue();
        sliderImg = new ArrayList<>();
        viewPager = findViewById(R.id.viewPager);
        sliderDotspanel = findViewById(R.id.SliderDots);
        sharedPreferences = getSharedPreferences(LoginActivity.getData, Context.MODE_PRIVATE);

        TextView name = findViewById(R.id.name_dash);
        String getname = sharedPreferences.getString("username", null);
        name.setText(getname);

        //setcal
        TextView status = findViewById(R.id.status_cal);
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (hour >= 4 && hour < 10){
            status.setText(R.string.spagi);
        }else if (hour >= 10 && hour <15){
            status.setText(R.string.ssiang);
        }else if (hour >= 15 && hour <= 18 ){
            status.setText(R.string.ssore);
        }else {
            status.setText(R.string.smalam);
        }

        ImageView pilih_masjid, pilih_acak, logout, pay;

        pilih_masjid = findViewById(R.id.pilih_masjid);
        pilih_acak = findViewById(R.id.pilih_acak);
        logout = findViewById(R.id.logout);
        pay = findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, PayActivity.class));
            }
        });

        pilih_acak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, DonasiActivity.class));
            }
        });

        pilih_masjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MasjidActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
//                FirebaseMessaging.getInstance().unsubscribeFromTopic(nis);
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        sendRequest();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void sendRequest(){
        Log.e("eroro", "tayo6" );

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("response", "sukses");

                for (int i = 0; i < response.length(); i++){
                    SliderUtils sliderUtils = new SliderUtils();
                    try{
                        JSONObject jsonObject = response.getJSONObject(i);
                        sliderUtils.setSliderImageUrl(jsonObject.getString("image_url"));
                        pDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        pDialog.dismiss();
                    }

                    sliderImg.add(sliderUtils);
                }

                viewPagerAdapter = new ViewPagerAdapter(sliderImg, DashboardActivity.this);

                viewPager.setAdapter(viewPagerAdapter);

                dotscount = viewPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(DashboardActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Eror = ", "Login Error : " + error.getMessage());
                try {
                    View noconection1 = getLayoutInflater().inflate(R.layout.activity_noconnection, null);

                    final BottomSheetDialog dialog = new BottomSheetDialog(DashboardActivity.this);
                    dialog.setContentView(noconection1);
                    pDialog.dismiss();
                    dialog.show();
                }catch (Exception e){

                }

            }
        });

        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequest);

    }

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.e("eroro", "tayo2" );
//
//                for(int i = 0; i < response.length(); i++){
//                    Log.e("eroro", "tayo3" );
//
//                    SliderUtils sliderUtils = new SliderUtils();
//
//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//
//                        sliderUtils.setSliderImageUrl(jsonObject.getString("image_url"));
//                        Log.e("eroro", "tayo" );
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    sliderImg.add(sliderUtils);
//
//                }
//
//                viewPagerAdapter = new ViewPagerAdapter(sliderImg, DashboardActivity.this);
//
//                viewPager.setAdapter(viewPagerAdapter);
//
//                dotscount = viewPagerAdapter.getCount();
//                dots = new ImageView[dotscount];
//
//                for(int i = 0; i < dotscount; i++){
//
//                    dots[i] = new ImageView(DashboardActivity.this);
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                    params.setMargins(8, 0, 8, 0);
//
//                    sliderDotspanel.addView(dots[i], params);
//
//                }
//
//                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequest);
//
//    }


}