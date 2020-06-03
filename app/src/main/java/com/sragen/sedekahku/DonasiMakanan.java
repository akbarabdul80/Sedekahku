package com.sragen.sedekahku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sragen.sedekahku.Adapter.BottomDonasiAdapter;
import com.sragen.sedekahku.Adapter.DonasiData;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static com.sragen.sedekahku.LoginActivity.getData;

public class DonasiMakanan extends AppCompatActivity implements SendData{

    private SharedPreferences preferences,sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<DonasiData> makananData;
    private BottomDonasiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_makanan);
        Log.i("Gagal", "Yes");

        preferences = getSharedPreferences("Array", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(getData, Context.MODE_PRIVATE);
        String json_n = preferences.getString("nama_array", null);
        String json_d = preferences.getString("dec_array", null);
        String json_h = preferences.getString("harga_array", null);
        String json_i = preferences.getString("img_array", null);
        String json_j = preferences.getString("jumlah_array", null);
        String total = preferences.getString("total", null);
        final String namamasjid = sharedPreferences.getString("namamasjid", "Acak");
        final String username = sharedPreferences.getString("username", null);

        Log.e("Username", String.valueOf(username));

        TextView tv_total = findViewById(R.id.txt_total1);
        tv_total.setText(total);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> nama_m = gson.fromJson(json_n, type);
        ArrayList<String> dec_m = gson.fromJson(json_d, type);
        ArrayList<String> har_m = gson.fromJson(json_h, type);
        ArrayList<String> img_m = gson.fromJson(json_i, type);
        ArrayList<String> jml = gson.fromJson(json_j, type);

        Log.i("Makanan Gson", String.valueOf(nama_m.get(0)));

        makananData = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_makanan1);

        String pesanan = "";

        for (int i = 0; i < nama_m.size(); i++){
            assert img_m != null;
            makananData.add(new DonasiData(img_m.get(i),nama_m.get(i), dec_m.get(i), har_m.get(i), jml.get(i)));
            if (i == nama_m.size()-1){
                pesanan += nama_m.get(i) + " Jumlah " + jml.get(i) + ".";
            }else {
                pesanan += nama_m.get(i) + " Jumlah " + jml.get(i) + ", ";
            }
        }

        final String pesanan1 = pesanan;

        Log.e("Pesanan = ", pesanan);

        adapter = new BottomDonasiAdapter(this, makananData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Button btn_donasi = findViewById(R.id.btn_p_donasi1);

        btn_donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View donasi = getLayoutInflater().inflate(R.layout.bottomshet_donasi, null);

                final BottomSheetDialog dialog = new BottomSheetDialog(DonasiMakanan.this);
                dialog.setContentView(donasi);
                Button bayar = donasi.findViewById(R.id.btn_bayar);
                TextView hasil = findViewById(R.id.txt_total1);
                TextView total_doansi = donasi.findViewById(R.id.total_donasi);
                TextView total_pembayaran = donasi.findViewById(R.id.totalpembayaran);
                TextView b_admin = donasi.findViewById(R.id.tv_admin);

                final String total = (String) hasil.getText();
                String badmin = (String) b_admin.getText();
                total_doansi.setText(total);

                String split1[] = badmin.split("Rp");
                String total1 = split1[1].replace(".", "");

                String split[] = total.split("Rp");
                String total2 = split[1].replace(".", "");
                String total3[] = total2.split(",");

                final int TotalPembayaran = Integer.parseInt(total1) + Integer.parseInt(total3[0]);
                Log.i("Total all", String.valueOf(TotalPembayaran));
                Locale localeID = new Locale("in", "ID");
                final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                total_pembayaran.setText(formatRupiah.format((double)TotalPembayaran).replaceAll(",00", ""));

                final String[] bank = new String[1];

                bayar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View bayar = getLayoutInflater().inflate(R.layout.bottomshet_bayar, null);
                        final BottomSheetDialog dialog1 = new BottomSheetDialog(DonasiMakanan.this);
                        dialog1.setContentView(bayar);

                        TextView totalpembayaran = bayar.findViewById(R.id.totalpembayaran);
                        Log.e("total" , String.valueOf(TotalPembayaran));
                        totalpembayaran.setText(formatRupiah.format((double)TotalPembayaran).replaceAll(",00", ""));
                        dialog.dismiss();
                        dialog1.show();

                        final Button bni = bayar.findViewById(R.id.btn_bni);
                        final Button mandiri = bayar.findViewById(R.id.btn_mandiri);
                        final ImageView img_metode = bayar.findViewById(R.id.img_metode);
                        final TextView nomerrek = bayar.findViewById(R.id.nomerRekening);
                        final TextView atasnama = bayar.findViewById(R.id.atas_nama);
                        final TextView btn_bayar = bayar.findViewById(R.id.btn_bayar);
                        nomerrek.setText("0445482919");
                        atasnama.setText("a/n Takmir Masjid Raya Alfalah Sragen");
                        bank[0] = "1";

                        bni.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mandiri.setBackgroundResource(R.drawable.raund_title);
                                bni.setBackgroundResource(R.drawable.raund_signin);
                                img_metode.setImageResource(R.drawable.bni);
                                nomerrek.setText("0445482919");
                                atasnama.setText("a/n Takmir Masjid Raya Alfalah Sragen");
                                bank[0] = "1";
                                Log.e("Bank 1" , String.valueOf(bank[0]));

                            }
                        });

                        mandiri.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bni.setBackgroundResource(R.drawable.raund_title);
                                mandiri.setBackgroundResource(R.drawable.raund_signin);
                                img_metode.setImageResource(R.drawable.mandiri);
                                nomerrek.setText("0906527857");
                                atasnama.setText("a/n Lazismu Masjid Raya Al-Falah BUMM");
                                bank[0] = "0";
                                Log.e("Bank 1" , String.valueOf(bank[0]));


                            }
                        });

                        Log.e("Bank 1" , String.valueOf(bank[0]));


                        btn_bayar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), KonfirmasiActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("pesanan", pesanan1);
                                intent.putExtra("Jumlah", formatRupiah.format((double)TotalPembayaran).replaceAll(",00", ""));
                                intent.putExtra("bank", bank[0]);
                                intent.putExtra("norek", nomerrek.getText());
                                intent.putExtra("an", atasnama.getText());
                                intent.putExtra("namamasjid", namamasjid);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("namamasjid");
                                editor.commit();

                                Log.e("Bank 1" , String.valueOf(bank[0]));
                                startActivity(intent);
                                finish();


                            }
                        });



                    }
                });

                dialog.show();
            }
        });




//        btn_donasi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.i("Klik", "Sukses");
//                final View donasi = getLayoutInflater().inflate(R.layout.bottomshet_donasi, null);
//
//                final BottomSheetDialog dialog = new BottomSheetDialog(getBaseContext());
//                dialog.setContentView(donasi);
//                Button bayar = donasi.findViewById(R.id.btn_bayar);
//                TextView hasil = findViewById(R.id.txt_total1);
//                TextView total_doansi = donasi.findViewById(R.id.total_donasi);
//                TextView total_pembayaran = donasi.findViewById(R.id.totalpembayaran);
//                TextView b_admin = donasi.findViewById(R.id.tv_admin);
//
//                String total = (String) hasil.getText();
//                String badmin = (String) b_admin.getText();
//                total_doansi.setText(total);
//
//                String split1[] = badmin.split("Rp");
//                String total1 = split1[1].replace(".", "");
//
//                String split[] = total.split("Rp");
//                String total2 = split[1].replace(".", "");

//                final int TotalPembayaran = Integer.parseInt(total1) + Integer.parseInt(total2);
//
//                Locale localeID = new Locale("in", "ID");
//                final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
//                total_pembayaran.setText(formatRupiah.format((double)TotalPembayaran));

//                bayar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        View bayar = getLayoutInflater().inflate(R.layout.bottomshet_bayar, null);
//                        final View bayar = View.inflate(getBaseContext(), R.layout.bottomshet_bayar, null);
//
//                        final BottomSheetDialog dialog1 = new BottomSheetDialog(getApplicationContext());
//                        dialog1.setContentView(bayar);
//
//                        TextView totalpembayaran = bayar.findViewById(R.id.totalpembayaran);
////                        Log.e("total" , String.valueOf(TotalPembayaran));
////                        totalpembayaran.setText(formatRupiah.format((double)TotalPembayaran));
//                        dialog.dismiss();
//                        dialog1.show();
//
//                        final Button bni = bayar.findViewById(R.id.btn_bni);
//                        final Button mandiri = bayar.findViewById(R.id.btn_mandiri);
//                        final ImageView img_metode = bayar.findViewById(R.id.img_metode);
//                        final TextView nomerrek = bayar.findViewById(R.id.nomerRekening);
//                        bni.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mandiri.setBackgroundResource(R.drawable.raund_title);
//                                bni.setBackgroundResource(R.drawable.raund_signin);
//                                img_metode.setImageResource(R.drawable.bni);
//                                nomerrek.setText("55002312456");
//
//                            }
//                        });
//
//                        mandiri.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                bni.setBackgroundResource(R.drawable.raund_title);
//                                mandiri.setBackgroundResource(R.drawable.raund_signin);
//                                img_metode.setImageResource(R.drawable.mandiri);
//                                nomerrek.setText("673642343283");
//
//
//                            }
//                        });
//
//                    }
//                });
//
//                dialog.show();
//            }
//        });



//        makananData = gson.fromJson(json, type);
//        makananData = new ArrayList<>();


//        makananData = new ArrayList<>();
//
//        makananData.add(new MakananData("http://192.168.43.73/sedekahku/makanan/1.jpg", "Nasi Box Besar", "Nasi 2x, Ayam 2x, Ikan 2x, Sayuran", "Rp. 24.000"));
//        makananData.add(new MakananData("http://192.168.43.73/sedekahku/makanan/2.jpg", "Nasi Box Kecil", "Nasi 2x, Ayam 1x, Ikan 1x, Sayuran", "Rp. 19.000"));
//        makananData.add(new MakananData("http://192.168.43.73/sedekahku/makanan/3.jpg", "Nasi Box Kecil", "Nasi 1x, Ikan 2x, Sayuran", "Rp. 15.000"));
//        adapter = new MakananAdapter(this, makananData, this);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

        Log.i("Array Makanan Bos", Arrays.toString(new ArrayList[]{makananData}));

//        Bundle b = getIntent().getExtras();
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null && bundle.containsKey("array_list")){
//            makananData = bundle.getParcelableArrayList("array_list");
//            Log.i("Array Boyyy" , makananData.get(0).getImg_url());
//        }

//        if (null != b) {
////            ArrayList<MakananData> makanan = b.getStringArrayList("array_list");
//            ArrayList<String> arr = b.getStringArrayList("array_list");
////            arr = ArrayList<MakananData>;
//            Log.i("List", "Passed Array List :: " + arr.);
//            Log.i("List", "Passed Array List :: " + String.valueOf(arr.get(0).toString()));
//        }else {
//            Log.i("Gagal", "Yes");
//        }
    }

    @Override
    public void sendData(String data, ArrayList<DonasiData> makananData1, int desertNumber) {
    }

}
