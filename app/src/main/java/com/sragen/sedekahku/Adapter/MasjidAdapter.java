package com.sragen.sedekahku.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
import com.sragen.sedekahku.DashmasjidActivity;
import com.sragen.sedekahku.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MasjidAdapter extends ArrayAdapter<MasjidData> {
    //Imageloader to load images
    private ImageLoader imageLoader;

    //Array List that would contain the urls and the titles for the images
//    private ArrayList<String> images;
//    private ArrayList<String> names;

    private ArrayList<MasjidData> listdata;
    private Context context;
    private int resource;

    public MasjidAdapter(Context context, int resource, ArrayList<MasjidData> listdata) {
        super(context, resource, listdata);
        //Getting all the values
        this.context = context;
        this.listdata = listdata;
        this.resource = resource;
    }


    @NotNull
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grid_masjid, null, true);
        }
        final MasjidData masjidData = getItem(position);
        TextView name = convertView.findViewById(R.id.name_masjid);
        TextView alamat = convertView.findViewById(R.id.alamat_masjid);
        ImageView img = convertView.findViewById(R.id.image_viewmasjid);
        Button pilih = convertView.findViewById(R.id.btn_masjid);

        name.setText(masjidData.getName());
        alamat.setText(masjidData.getAlamat_masjid());

        Picasso.with(context)
                .load(masjidData.getImageurl())
                .into(img);

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DashmasjidActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", masjidData.getName());
                intent.putExtra("alamat", masjidData.getAlamat_masjid());
                intent.putExtra("dec", masjidData.getDec());
                intent.putExtra("ttl_donatur", masjidData.getTtl_donatur());
                intent.putExtra("ttl_makanan", masjidData.getTtl_makanan());
                intent.putExtra("ttl_infaq", masjidData.getTtl_infaq());
                intent.putExtra("id", masjidData.getId());
                intent.putExtra("date", masjidData.getDate());
                context.startActivity(intent);
            }
        });


        return convertView;
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        //Creating a linear layout
//        LinearLayout linearLayout = new LinearLayout(context);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//        //NetworkImageView
//        NetworkImageView networkImageView = new NetworkImageView(context);
//
//        //Initializing ImageLoader
//        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
//        imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
//
//        //Setting the image url to load
//        networkImageView.setImageUrl(images.get(position), imageLoader);
//
//        //Creating a textview to show the title
//        TextView textView = new TextView(context);
//        textView.setText(names.get(position));
//
//        //Scaling the imageview
//        networkImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        networkImageView.setLayoutParams(new GridView.LayoutParams(200, 200));
//
//        //Adding views to the layout
//        linearLayout.addView(textView);
//        linearLayout.addView(networkImageView);
//
//        //Returnint the layout
//        return linearLayout;
//    }
}