package com.sragen.sedekahku.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sragen.sedekahku.R;

import java.util.ArrayList;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.payViewHolder> {
    private ArrayList<PayData> datalist;
    private Context context;

    public PayAdapter(ArrayList<PayData> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public payViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_pay, parent, false);
        return new payViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull payViewHolder holder, final int position) {
        holder.txtName.setText(datalist.get(position).getName());
        holder.txtTanggal.setText(datalist.get(position).getTanggal());
        holder.txtBiaya.setText(datalist.get(position).getBiaya());
        holder.txtDonasi.setText(datalist.get(position).getDonasi());

        if (datalist.get(position).getStatus().equals("1")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.status.setBackgroundTintList(context.getResources().getColorStateList(R.color.verifikasi));
            }
        }

        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tayooo", "sadds");
                Toast.makeText(context, datalist.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (datalist != null)? datalist.size() :0;
    }

    public class payViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtDonasi, txtBiaya, txtTanggal;
        private RelativeLayout status;
        private View rv;
        public payViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_b_name);
            txtTanggal = itemView.findViewById(R.id.txt_b_info);
            txtBiaya = itemView.findViewById(R.id.txt_b_biaya);
            txtDonasi = itemView.findViewById(R.id.donasi);
            status = itemView.findViewById(R.id.status);
            rv = itemView;
        }
    }
}
