package com.sragen.sedekahku;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sragen.sedekahku.Adapter.MasjidAdapter;
import com.sragen.sedekahku.Adapter.MasjidData;
import com.sragen.sedekahku.app.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MasjidActivity extends AppCompatActivity {

    String request_url = Server.URL + "gridmasjid.php";

    //Web api url
//    public static final String DATA_URL = "http://192.168.43.73/sedekahku/gridmasjid.php";

    //Tag values to read from json
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";

    private ArrayList<MasjidData>Masjid_Data;
    private GridView gridView;
    MasjidAdapter adapter;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masjid);
        pDialog = new SweetAlertDialog(MasjidActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        gridView = findViewById(R.id.gridView);

        Masjid_Data = new ArrayList<>();

        //Calling the getData method
        getData();
    }

    private void getData(){
        //Showing a progress dialog while our app fetches the data from url
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#1591F9"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        //Creating a json array request to get the json from our api
        StringRequest stringRequest = new StringRequest(request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismissing the progressdialog on response

                        //Displaying our grid
//                        showGrid(response);

                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                JSONArray array=jsonObject.getJSONArray("data");
                                Log.e("Array" , array.toString());
                                for (int i=0; i<array.length(); i++){
                                JSONObject ob=array.getJSONObject(i);
                                MasjidData masjidData=new MasjidData(
                                        ob.getString("name"),
                                        ob.getString("imageurl"),
                                        ob.getString("alamat"),
                                        ob.getString("dec"),
                                        ob.getString("ttl_donatur"),
                                        ob.getString("ttl_makanan"),
                                        ob.getString("ttl_infaq"),
                                        ob.getString("id"),
                                        ob.getString("date")
                                        );
                                Masjid_Data.add(masjidData);
                                Log.e("dec" , ob.getString("dec"));
                            }
                            adapter = new MasjidAdapter(getApplicationContext(),R.layout.grid_masjid,Masjid_Data);
                            gridView.setAdapter(adapter);
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Eror = ", "Array Error : " + error.getMessage());
                        pDialog.dismiss();

                    }
                }
        );

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue
        requestQueue.add(stringRequest);
    }


//    private void showGrid(JSONArray jsonArray){
//        //Looping through all the elements of json array
//        for(int i = 0; i<jsonArray.length(); i++){
//            //Creating a json object of the current index
//            JSONObject obj = null;
//            try {
//                //getting json object from current index
//                obj = jsonArray.getJSONObject(i);
//
//                //getting image url and title from json object
//                images.add(obj.getString(TAG_IMAGE_URL));
//                names.add(obj.getString(TAG_NAME));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        //Creating GridViewAdapter Object
//        MasjidAdapter gridViewAdapter = new MasjidAdapter(this,images,names);
//
//        //Adding adapter to gridview
//        gridView.setAdapter(gridViewAdapter);
//    }
}
