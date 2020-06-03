package com.sragen.sedekahku;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sragen.sedekahku.Adapter.PayAdapter;
import com.sragen.sedekahku.Adapter.PayData;
import com.sragen.sedekahku.app.CustomVolleyRequest;
import com.sragen.sedekahku.app.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sragen.sedekahku.LoginActivity.getData;

public class PayActivity extends AppCompatActivity {
    String url = Server.URL + "pay.php";
    private ArrayList<PayData> datalist;
    private PayAdapter adapter;

    SharedPreferences sharedPreferences;

    String id, token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        sharedPreferences = getSharedPreferences(getData, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id", null);
        token = sharedPreferences.getString("token", null);
        addData();


    }

    void addData() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                final SwipeRefreshLayout swipeRefreshLayout;
                Log.e("Log ", "Login Response: " + response);
                String success = "1";

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("pay");
                    JSONObject cekjson = jsonArray.getJSONObject(0);
                    success = cekjson.getString("success");
                    int success1 = Integer.parseInt(success);
                    if (success1 == 0) {
                        Toast.makeText(getApplicationContext(),
                                cekjson.getString("massage"),
                                Toast.LENGTH_LONG).show();
                    } else {
                        datalist = new ArrayList<>();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject json = jsonArray.getJSONObject(a);

                            String name = json.getString("pesanan");
                            String tanggal = json.getString("tgl");
                            String biaya = json.getString("total");
                            String donasi = json.getString("donasi");
                            String username = json.getString("username");
                            String bank = json.getString("bank");
                            String tf_ke = json.getString("tf_ke");
                            String status = json.getString("status");

                            Log.e("Json Array", "Nama :" + name + " info :" + " biaya :" + tanggal + "tanggal" + donasi);


                            datalist.add(new PayData(name, biaya, tanggal, donasi, username, bank, tf_ke, status));
                            RecyclerView recyclerView = findViewById(R.id.rv_pay);

                            Log.e("Data response", String.valueOf(datalist));
//                            adapter = new riwayatAdapter(riwayatArrayList);
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//
//                            recyclerView.setLayoutManager(layoutManager);
//                            recyclerView.setAdapter(adapter);

//                            Context context = recyclerView.getContext();


                            adapter = new PayAdapter(datalist, getBaseContext());

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                            recyclerView.setLayoutManager(layoutManager);
//                            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(adapter);
//                            recyclerView.getAdapter().notifyDataSetChanged();
//                            recyclerView.scheduleLayoutAnimation();

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Nis ", "Null");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Eror = ", "Login Error : " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Periksa Koneksi Internet anda", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("token", token);
                return params;
            }
        };

        CustomVolleyRequest.getInstance(this).addToRequestQueue(strReq);
    }

}
