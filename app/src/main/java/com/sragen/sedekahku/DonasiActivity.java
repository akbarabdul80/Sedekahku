package com.sragen.sedekahku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.sragen.sedekahku.Adapter.DonasiData;
import com.sragen.sedekahku.Adapter.PageAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DonasiActivity extends AppCompatActivity implements SendData{

    private TabLayout tabLayout;
    private Button btn_donasi1;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
//    private Button btn_donasi;
//    final private SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.getData, Context.MODE_PRIVATE);

//    public ArrayList<String> makananData2;
//
    @Override
    public void sendData(String total, final ArrayList<DonasiData> makananData1, int desertNumber){
        preferences = getSharedPreferences("Array", Context.MODE_PRIVATE);
        btn_donasi1 = findViewById(R.id.btn_p_donasi1);
        Toast.makeText(getApplicationContext(), total, Toast.LENGTH_LONG);
        Log.e("Hasil", total);
        TextView hasil = findViewById(R.id.txt_total1);
        int total1 = Integer.parseInt(total);

        String nama = getIntent().getStringExtra("namamasjid");

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        hasil.setText(formatRupiah.format((double)total1).replaceAll(",00", ""));

        if (makananData1.size() == 0){

        }else {
            for (int i = 0 ; i < makananData1.size(); i++){
                Log.i("Isi Array Send ", makananData1.get(i).getImg_url());
            }
        }

        btn_donasi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View donasi = getLayoutInflater().inflate(R.layout.bottomshet_donasi, null);

//                final BottomSheetDialog dialog = new BottomSheetDialog(DonasiActivity.this);
//                dialog.setContentView(donasi);
                Button bayar = donasi.findViewById(R.id.btn_bayar);
                TextView hasil = findViewById(R.id.txt_total1);
                TextView total_doansi = donasi.findViewById(R.id.total_donasi);
                TextView total_pembayaran = donasi.findViewById(R.id.totalpembayaran);
                TextView b_admin = donasi.findViewById(R.id.tv_admin);

                String total = (String) hasil.getText();
                String badmin = (String) b_admin.getText();
                total_doansi.setText(total);

                String split1[] = badmin.split("Rp");
                String total1 = split1[1].replace(".", "");

                String split[] = total.split("Rp");
                String total2 = split[1].replace(".", "");

//                final int TotalPembayaran = Integer.parseInt(total1) + Integer.parseInt(total2);
//
//                Locale localeID = new Locale("in", "ID");
//                final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                ArrayList<String> namamakanan = new ArrayList<>();
                ArrayList<String> decmakanan = new ArrayList<>();
                ArrayList<String> hargamakanan = new ArrayList<>();
                ArrayList<String> imgmakanan = new ArrayList<>();
                ArrayList<String> jumlah = new ArrayList<>();

                for (int i = 0; i < makananData1.size(); i++){
                    namamakanan.add(makananData1.get(i).getName_makanan());
                    decmakanan.add(makananData1.get(i).getDec_makanan());
                    hargamakanan.add(makananData1.get(i).getHarga_makanan());
                    imgmakanan.add(makananData1.get(i).getImg_url());
                    jumlah.add(makananData1.get(i).getJumlah());
                    Log.i("Lopping", String.valueOf(i));
                }

                Gson gson = new Gson();
                String json_n = gson.toJson(namamakanan);
                String json_d = gson.toJson(decmakanan);
                String json_h = gson.toJson(hargamakanan);
                String json_i = gson.toJson(imgmakanan);
                String json_j = gson.toJson(jumlah);
                Log.i("string", json_h);

                editor = preferences.edit();

                //Memasukan Data Pada Editor SharedPreferences dengan key (data_saya)
                editor.putString("nama_array", json_n);
                editor.putString("dec_array", json_d);
                editor.putString("harga_array", json_h);
                editor.putString("img_array", json_i);
                editor.putString("jumlah_array", json_j);
                editor.putString("total", (String) hasil.getText());

                //Menjalankan Operasi
                editor.apply();
//                total_pembayaran.setText(formatRupiah.format((double)TotalPembayaran));
//
//                adapter = new BottomDonasiAdapter(donasi.getContext(), makananData1);
//                RecyclerView.LayoutManager layoutManager;
//
//                layoutManager = new LinearLayoutManager(DonasiActivity.this);
//
//                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setAdapter(adapter);

                Intent intent = new Intent(DonasiActivity.this, DonasiMakanan.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("array_list", makananData1);
//                intent.putExtras(bundle);
//                intent.putExtra("makanan", makananData1);
                startActivity(intent);
                finish();


//                bayar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        View bayar = getLayoutInflater().inflate(R.layout.bottomshet_bayar, null);
//
//                        final BottomSheetDialog dialog1 = new BottomSheetDialog(DonasiActivity.this);
//                        dialog1.setContentView(bayar);
//
//                        TextView totalpembayaran = bayar.findViewById(R.id.totalpembayaran);
//                        Log.e("total" , String.valueOf(TotalPembayaran));
//                        totalpembayaran.setText(formatRupiah.format((double)TotalPembayaran));
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

//                dialog.show();
            }
        });

//        Gson gson = new Gson();
//        String json = gson.toJson(makananData1);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("ArrayBayar", json);
//        editor.commit();

//        Log.e("Makanan Data1", String.valueOf(makananData1.get(1).getImg_url()));
//        Log.e("Makanan Data1", String.valueOf(makananData1.get(3).getImg_url()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        this.tabLayout = findViewById(R.id.tabLayout);

//        TabItem tabMakanan = findViewById(R.id.tabmakanan);
//        TabItem tabInfaq = findViewById(R.id.tabinfaq);
//        TabItem tabLelang = findViewById(R.id.tablelang)
        final ViewPager viewPager = findViewById(R.id.ViewDonasi);
//        btn_donasi = findViewById(R.id.btn_p_donasi);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(pageAdapter);





//        tabMakanan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new MakananFragment();
//            }
//        });
//        tabInfaq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new InfaqFragment();
//            }
//        });
//        tabLelang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new LelangFragment();
//            }
//        });




//
//        tabLayout.addTab(tabLayout.newTab().setText("Makanan"));
//        tabLayout.addTab(tabLayout.newTab().setText("Infaq"));
//        tabLayout.addTab(tabLayout.newTab().setText("Lelang"));
//
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

//                if (tab.getPosition() == 1) {
//
//                } else if (tab.getPosition() == 2) {
//                    new PageAdapter(getSupportFragmentManager(), 1);
//                } else {
//                    new PageAdapter(getSupportFragmentManager(), 2);
//                }

//                String tabpos = String.valueOf(tabLayout.getSelectedTabPosition());
//
//                switch (Integer.parseInt(tabpos)){
//                    case 0:
//                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//                        Log.e("tab", String.valueOf(tabLayout.getSelectedTabPosition()));
//                        break;
//                    case 1:
//                        Log.e("tab", String.valueOf(tabLayout.getSelectedTabPosition()));
//                        break;
//                    case 2:
//                        Log.e("tab", String.valueOf(tabLayout.getSelectedTabPosition()));
//                        break;
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("ArrayBayar", null);
//        Type type = new TypeToken<List<String>>(){
//        }.getType();
//        List<String> makanan = gson.fromJson(json, type);
//
//        Log.e("TestArray", makanan.get(0));

//        btn_donasi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final View donasi = getLayoutInflater().inflate(R.layout.bottomshet_donasi, null);
//
//                final BottomSheetDialog dialog = new BottomSheetDialog(DonasiActivity.this);
//                dialog.setContentView(donasi);
//                Button bayar = donasi.findViewById(R.id.btn_bayar);
//                TextView hasil = findViewById(R.id.txt_total);
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
//
//                final int TotalPembayaran = Integer.parseInt(total1) + Integer.parseInt(total2);
//
//                Locale localeID = new Locale("in", "ID");
//                final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
//                total_pembayaran.setText(formatRupiah.format((double)TotalPembayaran));
//
//                bayar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        View bayar = getLayoutInflater().inflate(R.layout.bottomshet_bayar, null);
//
//                        final BottomSheetDialog dialog1 = new BottomSheetDialog(DonasiActivity.this);
//                        dialog1.setContentView(bayar);
//
//                        TextView totalpembayaran = bayar.findViewById(R.id.totalpembayaran);
//                        Log.e("total" , String.valueOf(TotalPembayaran));
//                        totalpembayaran.setText(formatRupiah.format((double)TotalPembayaran));
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

    }


}
