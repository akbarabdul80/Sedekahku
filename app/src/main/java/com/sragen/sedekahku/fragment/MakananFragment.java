package com.sragen.sedekahku.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sragen.sedekahku.Adapter.DonasiData;
import com.sragen.sedekahku.Adapter.MakananAdapter;
import com.sragen.sedekahku.Adapter.MakananData;
import com.sragen.sedekahku.R;
import com.sragen.sedekahku.SendData;
import com.sragen.sedekahku.app.CustomVolleyRequest;
import com.sragen.sedekahku.app.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakananFragment extends Fragment implements SendData {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_makanan, container, false);


    }

    private ArrayList<MakananData> makananData;
    private RecyclerView recyclerView;
    private MakananAdapter adapter;
    private String url = Server.URL + "makanan.php";
    SharedPreferences sharedPreferences;
    public static final String getData = "getData";
    private SweetAlertDialog pDialog;



    int desertNumber;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = this.getActivity().getSharedPreferences(getData, Context.MODE_PRIVATE);
        pDialog = new SweetAlertDialog(this.getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        recyclerView = view.findViewById(R.id.makanan_rv);

        makananData = new ArrayList<>();

//        makananData.add(new MakananData("http://192.168.43.73/sedekahku/makanan/1.jpg", "Nasi Box Besar", "Nasi 2x, Ayam 2x, Ikan 2x, Sayuran", "Rp. 24.000"));
//        makananData.add(new MakananData("http://192.168.43.73/sedekahku/makanan/2.jpg", "Nasi Box Kecil", "Nasi 2x, Ayam 1x, Ikan 1x, Sayuran", "Rp. 19.000"));
//        makananData.add(new MakananData("http://192.168.43.73/sedekahku/makanan/3.jpg", "Nasi Box Kecil", "Nasi 1x, Ikan 2x, Sayuran", "Rp. 15.000"));
//        adapter = new MakananAdapter(getContext(), makananData, getContext());
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#1591F9"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ambilMakanan();

    }

    @Override
    public void sendData(String data, ArrayList<DonasiData> makananData1, int desertNumber) {
        Log.i("Data", data);
    }

    private void ambilMakanan(){
        Log.e("Response", "1");

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Log ", "Login Response: " + response);


                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("makanan");
                    JSONObject cekjson = jsonArray.getJSONObject(0);
                    String success = cekjson.getString("success");
                    int success1 = Integer.parseInt(success);
                    Log.e("Response", String.valueOf(jObj));

                    if (success1 == 1){
//                        String id =jObj.getString("id");
//                        String nama =jObj.getString("nama");
//                        String dec =jObj.getString("dec");
//                        String harga = jObj.getString("harga");
//                        String img_url = jObj.getString("img_url");
                        Log.e("Successfully!", jObj.toString());

                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject json = jsonArray.getJSONObject(a);
                            String id =json.getString("id");
                            String nama =json.getString("nama");
                            String dec =json.getString("dec");
                            String harga = json.getString("harga");
                            String img_url = json.getString("img_url");

                            makananData.add(new MakananData(img_url, nama, dec, harga));

                        }

                        adapter = new MakananAdapter(getContext(), makananData, getContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);

//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("id", id);
//                        editor.putString("nama", nama);
//                        editor.putString("dec", dec);
//                        editor.putString("harga", harga);
//                        editor.putString("img_url", img_url);
//                        editor.commit();
                        pDialog.dismissWithAnimation();
                        pDialog.dismiss();

                    }else {
                        Toast.makeText(getContext(), jObj.getString("message"), Toast.LENGTH_LONG).show();
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
                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(noconection1);
                dialog.show();
                pDialog.dismissWithAnimation();
                pDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        CustomVolleyRequest.getInstance(this.getActivity()).addToRequestQueue(strReq);
    }
}
