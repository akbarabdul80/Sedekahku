package com.sragen.sedekahku;

import android.os.Parcel;
import android.os.Parcelable;

public class MyData implements Parcelable {
    public String img_url;
    public String name_makanan;
    public String dec_makanan;
    public String harga_makanan;

    public MyData(final String img_url, final String name_makanan, final String dec_makanan, final String harga_makanan){
        this.img_url = img_url;
        this.name_makanan = name_makanan;
        this.dec_makanan = dec_makanan;
        this.harga_makanan = harga_makanan;
    }

    public static final Creator<MyData> CREATOR = new Creator<MyData>() {
        @Override
        public MyData createFromParcel(Parcel in) {
            return new MyData(in);
        }

        @Override
        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img_url);
        dest.writeString(name_makanan);
        dest.writeString(dec_makanan);
        dest.writeString(harga_makanan);
    }

    public MyData(Parcel in) {
        img_url = in.readString();
        name_makanan = in.readString();
        dec_makanan = in.readString();
        harga_makanan = in.readString();
    }
}
