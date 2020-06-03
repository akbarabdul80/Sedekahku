package com.sragen.sedekahku.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.sragen.sedekahku.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BottomDonasiAdapter extends RecyclerView.Adapter<BottomDonasiAdapter.BottomViewHolder> {

    private Context context;
    private ArrayList<DonasiData> makanandata;
    final int[] total_all = {0};

    public BottomDonasiAdapter(Context context, ArrayList<DonasiData> makananData){
        this.makanandata = makananData;
        this.context = context;
    }

    @NonNull
    @Override
    public BottomDonasiAdapter.BottomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.row_d_makanan, parent, false);
        View view1 =layoutInflater.inflate(R.layout.activity_donasi_makanan, parent, false);
        return new BottomViewHolder(view, view1);

    }

    @Override
    public void onBindViewHolder(@NonNull BottomDonasiAdapter.BottomViewHolder holder, int position) {
        final LayoutInflater bottom = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        holder.txtname.setText(makanandata.get(position).getName_makanan());
        holder.txtdes.setText(makanandata.get(position).getDec_makanan());
        holder.txtharga.setText(makanandata.get(position).getHarga_makanan());
        holder.txtjml_m.setText(makanandata.get(position).getJumlah());

        Log.i("Jangkrik", makanandata.get(position).getName_makanan());

        Picasso.with(context)
                .load(makanandata.get(position).getImg_url())
                .into(holder.imgView);

        holder.btn_donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Klik", "Sukses");
                final View donasi = bottom.inflate(R.layout.bottomshet_donasi, null);

                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(donasi);
                Button bayar = donasi.findViewById(R.id.btn_bayar);
                TextView hasil = donasi.findViewById(R.id.txt_total1);
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

                final int TotalPembayaran = Integer.parseInt(total1) + Integer.parseInt(total2);

                Locale localeID = new Locale("in", "ID");
                final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                total_pembayaran.setText(formatRupiah.format((double)TotalPembayaran));

                bayar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View bayar = bottom.inflate(R.layout.bottomshet_bayar, null);

                        final BottomSheetDialog dialog1 = new BottomSheetDialog(context);
                        dialog1.setContentView(bayar);

                        TextView totalpembayaran = bayar.findViewById(R.id.totalpembayaran);
                        Log.e("total" , String.valueOf(TotalPembayaran));
                        totalpembayaran.setText(formatRupiah.format((double)TotalPembayaran));
                        dialog.dismiss();
                        dialog1.show();

                        final Button bni = bayar.findViewById(R.id.btn_bni);
                        final Button mandiri = bayar.findViewById(R.id.btn_mandiri);
                        final ImageView img_metode = bayar.findViewById(R.id.img_metode);
                        final TextView nomerrek = bayar.findViewById(R.id.nomerRekening);
                        bni.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mandiri.setBackgroundResource(R.drawable.raund_title);
                                bni.setBackgroundResource(R.drawable.raund_signin);
                                img_metode.setImageResource(R.drawable.bni);
                                nomerrek.setText("55002312456");
                            }
                        });

                        mandiri.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bni.setBackgroundResource(R.drawable.raund_title);
                                mandiri.setBackgroundResource(R.drawable.raund_signin);
                                img_metode.setImageResource(R.drawable.mandiri);
                                nomerrek.setText("673642343283");
                            }
                        });
                    }
                });

                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return (makanandata != null)? makanandata.size() :0;
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder {
        private TextView txtname, txtdes, txtharga, txtjml_m, txt_total,hasil;
        private ImageView imgView;
        private Button btn_min, btn_plus,btn_donasi;
        public BottomViewHolder(@NonNull View itemView, View view) {
            super(itemView);
            txtname = itemView.findViewById(R.id.name_m);
            txtdes = itemView.findViewById(R.id.des_m);
            txtharga = itemView.findViewById(R.id.harga);
            txtjml_m = itemView.findViewById(R.id.jml_m);
            btn_donasi = view.findViewById(R.id.btn_p_donasi1);

            imgView = itemView.findViewById(R.id.image_viewmasjid);
        }
    }
}
