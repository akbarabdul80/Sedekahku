package com.sragen.sedekahku.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sragen.sedekahku.DonasiInfaqActivity;
import com.sragen.sedekahku.R;

import java.text.NumberFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfaqFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_infaq, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText ed_infaq = view.findViewById(R.id.ed_infaq);
        final TextView tv_total = view.findViewById(R.id.txt_total2);
        Button btn_donasi = view.findViewById(R.id.btn_p_donasi2);

        ed_infaq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ed_infaq.getText().toString().length() == 0){
                    String a = "0";
                    Double b = Double.parseDouble(a);
                    Locale localeID = new Locale("in", "ID");
                    final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    tv_total.setText(formatRupiah.format((double)b).replaceAll(",00", ""));
                }else {
                    double hasil = Double.parseDouble(ed_infaq.getText().toString());
                    Locale localeID = new Locale("in", "ID");
                    final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    tv_total.setText(formatRupiah.format((double)hasil).replaceAll(",00", ""));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DonasiInfaqActivity.class);
                intent.putExtra("total", tv_total.getText());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
}