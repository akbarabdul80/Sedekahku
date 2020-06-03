package com.sragen.sedekahku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sragen.sedekahku.Adapter.SliderUtils;
import com.sragen.sedekahku.Adapter.ViewPagerMAdapter;
import com.sragen.sedekahku.app.CustomVolleyRequest;
import com.sragen.sedekahku.app.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.sragen.sedekahku.LoginActivity.getData;

public class DashmasjidActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    RequestQueue rq;
    List<SliderUtils> sliderImg;
    ViewPagerMAdapter viewPagerMAdapter;

    String request_url = Server.URL + "slide_masjid.php";
    String nama, alamat, deskripsi, ttl_donatur, ttl_makanan, ttl_infaq, id , date;

    private SweetAlertDialog pDialog;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashmasjid);

        TextView title, tv_alamat, tv_des, tv_donatur, tv_makanan, tv_infaq, tv_reset;
        sharedPreferences = getSharedPreferences(getData, Context.MODE_PRIVATE);

        Intent intent = getIntent();

        nama = intent.getStringExtra("name");
        alamat = intent.getStringExtra("alamat");
        deskripsi = intent.getStringExtra("dec");
        ttl_donatur = intent.getStringExtra("ttl_donatur");
        ttl_makanan = intent.getStringExtra("ttl_makanan");
        ttl_infaq = intent.getStringExtra("ttl_infaq");
        id = intent.getStringExtra("id");
        date = intent.getStringExtra("date");

        Log.e("id yang masuk", id);
        Log.e("ttl Donatur", ttl_donatur + ttl_makanan + ttl_infaq);

        Log.e("dec", deskripsi);

        title = findViewById(R.id.nama_masjiddash);
        tv_alamat = findViewById(R.id.alamat_masjid_dash);
        tv_des = findViewById(R.id.deskripsi_dash);
        tv_donatur = findViewById(R.id.ttl_donatur);
        tv_makanan = findViewById(R.id.ttl_makanan);
        tv_infaq = findViewById(R.id.ttl_infaq);
        tv_reset = findViewById(R.id.reset);

        title.setText(nama);
        tv_alamat.setText(alamat);
        tv_des.setText(deskripsi);
        tv_donatur.setText(ttl_donatur);
        tv_makanan.setText(ttl_makanan);
        tv_infaq.setText(ttl_infaq);

        RoundCornerProgressBar progress1 = findViewById(R.id.progress_1);
        progress1.setProgressColor(Color.parseColor("#1591F9"));
        progress1.setProgressBackgroundColor(Color.parseColor("#E0E0E0"));
        progress1.setMax(70);

        Log.e("Tanggal Server", date);
        if (date.equals("Saturday")){
            progress1.setProgress(10);
            tv_reset.setText("6 Hari Lagi");
        }else if(date.equals("Sunday")){
            progress1.setProgress(20);
            tv_reset.setText("5 Hari Lagi");
        }else if(date.equals("Monday")){
            progress1.setProgress(30);
            tv_reset.setText("4 Hari Lagi");
        }else if(date.equals("Tuesday")){
            progress1.setProgress(40);
            tv_reset.setText("3 Hari Lagi");
        }else if(date.equals("Wednesday")){
            progress1.setProgress(50);
            tv_reset.setText("2 Hari Lagi");
        }else if(date.equals("Thursday")){
            progress1.setProgress(60);
            tv_reset.setText("1 Hari Lagi");
        }else if(date.equals("Friday")){
            progress1.setProgress(60);
            tv_reset.setText("Kurang dari 1 Hari");
        }

        rq = CustomVolleyRequest.getInstance(this).getRequestQueue();
        sliderImg = new ArrayList<>();
        viewPager = findViewById(R.id.viewPager1);
        sliderDotspanel = findViewById(R.id.SliderDots1);

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

        Button donasi = findViewById(R.id.btn_donasi);

        donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DonasiActivity.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("namamasjid", nama);
                editor.commit();
                startActivity(intent);
            }
        });


    }

    private void sendRequest(){
        Log.e("Response", "1");

        StringRequest strReq = new StringRequest(Request.Method.POST, request_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Log ", "Login Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("slide");

                    for (int i = 0; i < jsonArray.length(); i++){
                    SliderUtils sliderUtils = new SliderUtils();
                    try{
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        sliderUtils.setSliderImageUrl(jsonObject.getString("image_url"));

                        Log.e("json", jsonObject.getString("image_url"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sliderImg.add(sliderUtils);
                }

                viewPagerMAdapter = new ViewPagerMAdapter(sliderImg, DashmasjidActivity.this);

                viewPager.setAdapter(viewPagerMAdapter);

                dotscount = viewPagerMAdapter.getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(DashmasjidActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", "Login Error : " + error.getMessage());
                View noconection1 = getLayoutInflater().inflate(R.layout.activity_noconnection, null);
                final BottomSheetDialog dialog = new BottomSheetDialog(getApplicationContext());
                dialog.setContentView(noconection1);
                dialog.show();
                pDialog.dismissWithAnimation();
                pDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        CustomVolleyRequest.getInstance(this).addToRequestQueue(strReq);
    }

//    public void sendRequest(){
//        Log.e("eroro", "tayo6" );
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, request_url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.e("response", "sukses");;
//
//                for (int i = 0; i < response.length(); i++){
//                    SliderUtils sliderUtils = new SliderUtils();
//                    try{
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        sliderUtils.setSliderImageUrl(jsonObject.getString("image_url"));
//
//                        Log.e("json", jsonObject.getString("image_url"));
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    sliderImg.add(sliderUtils);
//                }
//
//                viewPagerAdapter = new ViewPagerAdapter(sliderImg, DashmasjidActivity.this);
//
//                viewPager.setAdapter(viewPagerAdapter);
//
//                dotscount = viewPagerAdapter.getCount();
//                dots = new ImageView[dotscount];
//
//                for(int i = 0; i < dotscount; i++){
//
//                    dots[i] = new ImageView(DashmasjidActivity.this);
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
//                Log.e("Eror = ", "Login Error : " + error.getMessage());
//                try {
//                    View noconection1 = getLayoutInflater().inflate(R.layout.activity_noconnection, null);
//                    final BottomSheetDialog dialog = new BottomSheetDialog(DashmasjidActivity.this);
//                    dialog.setContentView(noconection1);
//                    dialog.show();
//                }catch (Exception e){
//
//                }
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", id);
//                Log.e("Id", id);
//                return params;
//            }
//        };
//
//        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequest);
//
//    }
}
