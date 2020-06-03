package com.sragen.sedekahku.Adapter;

public class DonasiData {


    private String img_url;
    private String name_makanan;
    private String dec_makanan;
    private String harga_makanan;
    private String jumlah;


    public DonasiData(String img_url, String name_makanan, String dec_makanan, String harga_makanan, String jumlah){
        this.img_url = img_url;
        this.name_makanan = name_makanan;
        this.dec_makanan = dec_makanan;
        this.harga_makanan = harga_makanan;
        this.harga_makanan = harga_makanan;
        this.jumlah = jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }


    public String getJumlah() {
        return jumlah;
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
