package com.sragen.sedekahku.Adapter;

public  class MasjidData {

    public MasjidData(String name, String imageurl, String alamat_masjid, String dec, String ttl_donatur, String ttl_makanan, String ttl_infaq, String id, String date) {
        this.name = name;
        this.imageurl = imageurl;
        this.alamat_masjid = alamat_masjid;
        this.dec = dec;
        this.ttl_donatur = ttl_donatur;
        this.ttl_makanan = ttl_makanan;
        this.ttl_infaq = ttl_infaq;
        this.date = date;
        this.id = id;
    }

    private String id;
    private String name;
    private String imageurl;
    private String alamat_masjid;
    private String dec;
    private String ttl_donatur;
    private String ttl_makanan;
    private String ttl_infaq;
    private String date;

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getTtl_donatur() {
        return ttl_donatur;
    }

    public String getTtl_makanan() {
        return ttl_makanan;
    }

    public String getTtl_infaq() {
        return ttl_infaq;
    }

    public String getDec() {
        return dec;
    }

    public String getAlamat_masjid() {
        return alamat_masjid;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }
}

//class MasjidData {
//
//    public MasjidData(String name, String imageurl) {
//        this.nama = name;
//        this.url_img = imageurl;
//    }
//
//    public String getUrl_img() {
//        return url_img;
//    }
//
//    public void setUrl_img(String url_img) {
//        this.url_img = url_img;
//    }
//
//    private String url_img;
//
//    public String getWaktureset() {
//        return waktureset;
//    }
//
//    public void setWaktureset(String waktureset) {
//        this.waktureset = waktureset;
//    }
//
//    private String waktureset;
//
//    private String nama;
//
//    public String getNama() {
//        return nama;
//    }
//
//    public void setNama(String nama) {
//        this.nama = nama;
//    }
//
//    public String getAlamat() {
//        return alamat;
//    }
//
//    public void setAlamat(String alamat) {
//        this.alamat = alamat;
//    }
//
//    public String getDeskripsi() {
//        return deskripsi;
//    }
//
//    public void setDeskripsi(String deskripsi) {
//        this.deskripsi = deskripsi;
//    }
//
//    public String getTotaldonatur() {
//        return totaldonatur;
//    }
//
//    public void setTotaldonatur(String totaldonatur) {
//        this.totaldonatur = totaldonatur;
//    }
//
//    public String getTotalmakana() {
//        return totalmakana;
//    }
//
//    public void setTotalmakana(String totalmakana) {
//        this.totalmakana = totalmakana;
//    }
//
//    public String getTotalinfak() {
//        return totalinfak;
//    }
//
//    public void setTotalinfak(String totalinfak) {
//        this.totalinfak = totalinfak;
//    }
//
//    private String alamat;
//    private String deskripsi;
//    private String totaldonatur;
//    private String totalmakana;
//    private String totalinfak;
//
//
//}
