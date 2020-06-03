package com.sragen.sedekahku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.Locale;

import static com.sragen.sedekahku.LoginActivity.getData;

public class DonasiInfaqActivity extends AppCompatActivity {
    private SharedPreferences preferences,sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_infaq);
        String total = getIntent().getStringExtra("total");
        EditText ed = findViewById(R.id.ed_infaq);


        preferences = getSharedPreferences("Array", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(getData, Context.MODE_PRIVATE);
        final String namamasjid = sharedPreferences.getString("namamasjid", "Acak");
        final String username = sharedPreferences.getString("username", null);

        ed.setText(total);
        TextView hasil = findViewById(R.id.txt_total1);
        hasil.setText(total);

        final String pesanan1 = "Infaq " + total;

        Button donasi = findViewById(R.id.btn_p_donasi1);

        donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View donasi = getLayoutInflater().inflate(R.layout.bottomshet_donasi, null);

                final BottomSheetDialog dialog = new BottomSheetDialog(DonasiInfaqActivity.this);
                dialog.setContentView(donasi);
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
                String total3[] = total2.split(",");

                final int TotalPembayaran = Integer.parseInt(total1) + Integer.parseInt(total3[0]);
                Log.i("Total all", String.valueOf(TotalPembayaran));
                Locale localeID = new Locale("in", "ID");
                final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                total_pembayaran.setText(formatRupiah.format((double)TotalPembayaran).replaceAll(",00", ""));

                bayar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View bayar = getLayoutInflater().inflate(R.layout.bottomshet_bayar, null);
                        final BottomSheetDialog dialog1 = new BottomSheetDialog(DonasiInfaqActivity.this);
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

                        final String[] bank = new String[1];

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
                                atasnama.setText("Takmir Masjid Raya Alfalah Sragen");
                                bank[0] = "1";

                            }
                        });

                        mandiri.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bni.setBackgroundResource(R.drawable.raund_title);
                                mandiri.setBackgroundResource(R.drawable.raund_signin);
                                img_metode.setImageResource(R.drawable.mandiri);
                                nomerrek.setText("0906527857");
                                atasnama.setText("Lazismu Masjid Raya Al-Falah BUMM");
                                bank[0] = "0";


                            }
                        });
                        btn_bayar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), KonfirmasiActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("pesanan", pesanan1);
                                intent.putExtra("Jumlah", formatRupiah.format((double) TotalPembayaran).replaceAll(",00", ""));
                                intent.putExtra("bank", bank[0]);
                                intent.putExtra("norek", nomerrek.getText());
                                intent.putExtra("an", atasnama.getText());
                                intent.putExtra("namamasjid", namamasjid);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("namamasjid");
                                editor.commit();

                                Log.e("Bank 1", String.valueOf(bank[0]));
                                startActivity(intent);
                                finish();


                            }
                        });

                    }
                });

                dialog.show();
            }
        });
    }
}
