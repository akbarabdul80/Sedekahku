package com.sragen.sedekahku.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.sragen.sedekahku.R;
import com.sragen.sedekahku.SendData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.MakananViewHolder> {

    private Context context;
    private ArrayList<MakananData> makanandata;
    private ArrayList<DonasiData> makananData1;
    private final int[] total_all = {0};
    private SendData totalData;
//    private LayoutInflater bottom = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    public MakananAdapter(Context context, ArrayList<MakananData> makananData, Context sendData){
        this.makanandata = makananData;
        this.context = context;
        this.totalData = (SendData) sendData;
    }

    @NonNull
    @Override
    public MakananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.row_makanan, parent, false);
//        View view1 =layoutInflater.inflate(R.layout.fragment_makanan, parent, false);
        return new MakananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MakananViewHolder holder, final int position) {

        makananData1 = new ArrayList<>();

        final int[] sudah = {0};

        Log.e("makanan", makanandata.toString());

        holder.txtname.setText(makanandata.get(position).getName_makanan());
        holder.txtdes.setText(makanandata.get(position).getDec_makanan());
        holder.txtharga.setText(makanandata.get(position).getHarga_makanan());

        Picasso.with(context)
                .load(makanandata.get(position).getImg_url())
                .into(holder.imgView);


        holder.btn_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityString = holder.txtjml_m.getText().toString();
                int desertNumber = Integer.parseInt(quantityString);
                desertNumber -= 1;



                if (desertNumber < 0) {
                    Toast.makeText(context, "Tidak Bisa Kurang dari 0",
                            Toast.LENGTH_SHORT).show();

                }else if (desertNumber == 0){
                    holder.txtjml_m.setText(String.valueOf(desertNumber));
                    String jumlah = makanandata.get(position).getHarga_makanan();
                    String split[] = jumlah.split("Rp. ");
                    String total = split[1].replace(".", "");
                    int total_k = Integer.parseInt(total); //15 .000
                    int total_all1 = total_all[0] - total_k; //15.000
                    total_all[0] = total_all1; //15.000
                    Log.e("test", Integer.toString(total_all1));
                    Log.e("hasil", Integer.toString(total_all[0]));

                    for (int i = 0; i < makananData1.size(); i++ ){
                        String tempName = makananData1.get(i).getImg_url();
                        if (tempName.equals(makanandata.get(position).getImg_url())){
                            makananData1.remove(i);
                        }
                    }
                    sudah[0] --;

                    int total1 = Integer.parseInt(total);

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
//                    holder.hasil.setText(formatRupiah.format((double)total1));

                    totalData.sendData(Integer.toString(total_all[0]), makananData1, desertNumber);
                }else {
                    holder.txtjml_m.setText(String.valueOf(desertNumber));
                    String jumlah = makanandata.get(position).getHarga_makanan();
                    String split[] = jumlah.split("Rp. ");
                    String total = split[1].replace(".", "");
                    int total_k = Integer.parseInt(total); //15 .000
                    int total_all1 = total_all[0] - total_k; //15.000
                    total_all[0] = total_all1; //15.000
                    Log.i("test", Integer.toString(total_all1));
                    Log.i("hasil", Integer.toString(total_all[0]));
//                    holder.hasil.setText(total_all[0]);



                }
            }
        });

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityString = holder.txtjml_m.getText().toString();
                int desertNumber = Integer.parseInt(quantityString);
                desertNumber += 1;

                if (desertNumber == 1){
                    holder.txtjml_m.setText(String.valueOf(desertNumber));
                    String jumlah = makanandata.get(position).getHarga_makanan();
                    String split[] = jumlah.split("Rp. ");
                    String total = split[1].replace(".", "");
                    int total_k = Integer.parseInt(total); //15 .000
//                  int hasil = desertNumber * total_k; // 1 * 15.000
                    int total_all1 = total_all[0] + total_k; //15.000
                    total_all[0] = total_all1; //15.000
                    Log.i("test", Integer.toString(total_all1));
                    Log.i("hasil", Integer.toString(total_all[0]));

                    if (sudah[0] == 0){
                        makananData1.add(new DonasiData(makanandata.get(position).getImg_url(), makanandata.get(position).getName_makanan(), makanandata.get(position).getDec_makanan(), makanandata.get(position).getHarga_makanan(), String.valueOf(desertNumber)));
                        Log.i("Array Makanan", String.valueOf(makanandata.get(position).getImg_url()));
                        sudah[0]++ ;
                    }

                    Log.i("Sudah", String.valueOf(sudah[0]));
//                    holder.hasil.setText(total_all[0]);

                    totalData.sendData(Integer.toString(total_all[0]), makananData1, desertNumber);
                }else {
                    holder.txtjml_m.setText(String.valueOf(desertNumber));
                    String jumlah = makanandata.get(position).getHarga_makanan();
                    String split[] = jumlah.split("Rp. ");
                    String total = split[1].replace(".", "");
                    int total_k = Integer.parseInt(total); //15 .000
//                int hasil = desertNumber * total_k; // 1 * 15.000
                    int total_all1 = total_all[0] + total_k; //15.000
                    total_all[0] = total_all1; //15.000
                    Log.i("test", Integer.toString(total_all1));
                    Log.i("hasil", Integer.toString(total_all[0]));
                    totalData.sendData(Integer.toString(total_all[0]), makananData1, desertNumber);

                    Log.i("Sudah", String.valueOf(sudah[0]));

                    for (int i = 0; i < makananData1.size(); i++ ){
                        String tempName = makananData1.get(i).getImg_url();
                        if (tempName.equals(makanandata.get(position).getImg_url())){
                            makananData1.set(i, new DonasiData(makanandata.get(position).getImg_url(), makanandata.get(position).getName_makanan(), makanandata.get(position).getDec_makanan(), makanandata.get(position).getHarga_makanan(), String.valueOf(desertNumber)));
                        }
                    }

                }

//                holder.txt_total.setText(total_all[0]);

            }
        });

//        holder.btn_donasi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final View donasi = bottom.inflate(R.layout.bottomshet_donasi, null);
//
//                final BottomSheetDialog dialog = new BottomSheetDialog(context);
//                dialog.setContentView(donasi);
//                Button bayar = donasi.findViewById(R.id.btn_bayar);
//                TextView hasil = donasi.findViewById(R.id.txt_total);
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
//                        View bayar = bottom.inflate(R.layout.bottomshet_bayar, null);
//
//                        final BottomSheetDialog dialog1 = new BottomSheetDialog(context);
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

    @Override
    public int getItemCount() {
        return (makanandata != null)? makanandata.size() :0;
    }

    public class MakananViewHolder extends RecyclerView.ViewHolder {
        private TextView txtname, txtdes, txtharga, txtjml_m, txt_total,hasil;
        private ImageView imgView;
        private Button btn_min, btn_plus,btn_donasi;
        public MakananViewHolder(@NonNull View itemView) {
            super(itemView);

            txtname = itemView.findViewById(R.id.name_m);
            txtdes = itemView.findViewById(R.id.des_m);
            txtharga = itemView.findViewById(R.id.harga);
            txtjml_m = itemView.findViewById(R.id.jml_m);
            imgView = itemView.findViewById(R.id.image_viewmasjid);
            btn_min = itemView.findViewById(R.id.btn_min);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_donasi = itemView.findViewById(R.id.btn_p_donasi1);
//            hasil = itemView1.findViewById(R.id.txt_total1);

        }

    }
}
