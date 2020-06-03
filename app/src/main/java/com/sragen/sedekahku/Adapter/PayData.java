package com.sragen.sedekahku.Adapter;

public class PayData {
    private String name;
    private String biaya;
    private String tanggal;
    private String donasi;
    private String username;
    private String bank;
    private String tf_ke;
    private String status;


    public PayData(String name, String biaya, String tanggal, String donasi, String username, String bank, String tf_ke, String status){
        this.name = name;
        this.biaya = biaya;
        this.tanggal = tanggal;
        this.donasi = donasi;
        this.username = username;
        this.bank = bank;
        this.tf_ke = tf_ke;
        this.status = status;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getTf_ke() {
        return tf_ke;
    }

    public void setTf_ke(String tf_ke) {
        this.tf_ke = tf_ke;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;

    public String getName() {
        return name;
    }

    public String getBiaya() {
        return biaya;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getDonasi() {
        return donasi;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setDonasi(String donasi) {
        this.donasi = donasi;
    }
}
