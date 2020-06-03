package com.sragen.sedekahku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sragen.sedekahku.app.CustomVolleyRequest;
import com.sragen.sedekahku.app.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sragen.sedekahku.LoginActivity.getData;

public class KonfirmasiActivity extends AppCompatActivity {

    Intent intent;
    private String url = Server.URL + "test.php";
    private SharedPreferences sharedPreferences;
    int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap, decoded;
    int bitmap_size = 60; // range 1 - 100
    ImageView imageView;

    Button buttonChoose;
    FloatingActionButton buttonUpload;
    Toolbar toolbar;
    EditText txt_name;
    int success;

    private static final String TAG = MainActivity.class.getSimpleName();

    /* 10.0.2.2 adalah IP Address localhost Emulator Android Studio. Ganti IP Address tersebut dengan
    IP Address Laptop jika di RUN di HP/Genymotion. HP/Genymotion dan Laptop harus 1 jaringan! */

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);
        intent = getIntent();

        final String username = intent.getStringExtra("username");
        final String pesanan = intent.getStringExtra("pesanan");
        final String Jumlah = intent.getStringExtra("Jumlah");
        final String bank = intent.getStringExtra("bank");
        final String norek = intent.getStringExtra("norek");
        final String namamasjid = intent.getStringExtra("namamasjid");
        String an = intent.getStringExtra("an");

        TextView txt_username, txt_pesanan, txt_jumlah, txt_norek, txt_an, time, txt_masjid;
        ImageView imgbank;

        txt_username = findViewById(R.id.usernamek);
        txt_pesanan = findViewById(R.id.pesanan);
        txt_jumlah = findViewById(R.id.jml_tagihan);
        txt_norek = findViewById(R.id.no_rekening);
        txt_an = findViewById(R.id.atas_namak);
        imgbank = findViewById(R.id.img_bank);
        time = findViewById(R.id.time);
        txt_masjid = findViewById(R.id.donasike);
        imageView = findViewById(R.id.imgtemp);

        txt_username.setText(username);
        txt_pesanan.setText(pesanan);
        txt_jumlah.setText(Jumlah);
        txt_norek.setText(norek);
        txt_an.setText(an);
        txt_masjid.setText(namamasjid);


        if (bank.equals("1")){
            imgbank.setImageResource(R.drawable.bni);
            Log.e("bank", "BNI");
        }else {
            imgbank.setImageResource(R.drawable.mandiri);
        }

        Log.e("Bank", "ghhy");
        String[] hari = new String[] { "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu" };
        String[] bulan = new String[] { "Jan", "Feb", "Mar", "April", "Mei", "Juni", "Juli", "Agust", "Sep", "Oct", "Nov", "Dec"};

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-d HH:MM:s");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:MM");
        Log.e("Waktu", "Sekarang " + c.get(Calendar.HOUR_OF_DAY) + " " +  " Besok " + c.getTimeZone());

        c.add(Calendar.HOUR, 24);
        final String sqlwaktu = sdf1.format(c.getTime());
        String tampil = sdf2.format(c.getTime());
        String tampil1 = hari[c.get(Calendar.DAY_OF_WEEK) - 1] + ", " + c.get(Calendar.DAY_OF_MONTH) + " " + bulan[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR) + " ," +  tampil;

        time.setText(tampil1);

//        c.add(Calendar.DATE, 1);

        Log.e("Waktu", "Sekarang " + hari[c.get(Calendar.DAY_OF_WEEK) - 1] + ", " + c.get(Calendar.DAY_OF_MONTH) + " " + bulan[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR) + " ," +  tampil + " Besok " + c.getTimeZone());

        Log.e("Tanggal", String.valueOf(c.get(Calendar.DATE)));
        Log.e("Masuk SQL", sqlwaktu);
        Log.e("Username", username);
        sharedPreferences = getSharedPreferences(getData, Context.MODE_PRIVATE);

        final String token = sharedPreferences.getString("token", null);

        Log.e("Token 1", token);

        Button bukti = findViewById(R.id.konfirmasi);
        final Button konfirmasi = findViewById(R.id.bukti);

        bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (imageView.getDrawable() == null){
                        Toast.makeText(getApplicationContext(), "Silahkan pilih gambar bukti",
                                Toast.LENGTH_LONG).show();
                    }else {
                        konfirmasi.setEnabled(false);
                        uploadImage(username, pesanan, token, sqlwaktu, Jumlah, bank, norek, namamasjid);
                    }

            }
        });

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(final String username, final String pesanan,final String token, final String waktu, final String total, final String bank, final String norek, final String namamasjid) {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(KonfirmasiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(KonfirmasiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(KonfirmasiActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put(KEY_IMAGE, getStringImage(decoded));
                params.put(KEY_NAME, "test");
                params.put("username", username);
                params.put("pesanan", pesanan);
                params.put("waktu", waktu);
                params.put("total", total);
                params.put("token", token);
                params.put("tfke", norek);
                params.put("bank", bank);
                params.put("status", "0");
                params.put("donasi", namamasjid);

                //kembali ke parameters
                Log.e(TAG, "s" + params);
                return params;
            }
        };

//        AppController.getInstance().addToRequestQueue(stringRequest, "tag_json_obj");
        CustomVolleyRequest.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void konfirmasiproses(final String username, final String pesanan, final String waktu, final String total, final String bank , final String namamasjid){
        Log.e("Response", "1");

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    Log.e("Response", String.valueOf(jObj));

                    if (success == 1){

                        Toast.makeText(KonfirmasiActivity.this, jObj.getString("message"), Toast.LENGTH_LONG).show();

                    }else {
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
                final BottomSheetDialog dialog = new BottomSheetDialog(KonfirmasiActivity.this);
                dialog.setContentView(noconection1);
                dialog.show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("pesanan", pesanan);
                params.put("waktu", waktu);
                params.put("total", total);
                params.put("bank", bank);
                params.put("namamasjid", namamasjid);

                return params;
            }
        };
        CustomVolleyRequest.getInstance(this).addToRequestQueue(strReq);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 1500));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void kosong() {
        imageView.setImageResource(0);
//        txt_name.setText(null);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageView.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
