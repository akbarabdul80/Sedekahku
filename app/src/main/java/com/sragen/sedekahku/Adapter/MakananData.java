package com.sragen.sedekahku.Adapter;

import java.util.ArrayList;

public class MakananData extends ArrayList<MakananData> {

    private String img_url, name_makanan, dec_makanan, harga_makanan;

    public MakananData(String img_url, String name_makanan, String dec_makanan, String harga_makanan){
//        MakananData makananData = new MakananData();
        this.img_url = img_url;
        this.name_makanan = name_makanan;
        this.dec_makanan = dec_makanan;
        this.harga_makanan = harga_makanan;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getName_makanan() {
        return name_makanan;
    }

    public String getDec_makanan() {
        return dec_makanan;
    }

    public String getHarga_makanan() {
        return harga_makanan;
    }
}
