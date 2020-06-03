package com.sragen.sedekahku.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
import com.sragen.sedekahku.R;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<SliderUtils> sliderImg;
    private ImageLoader imageLoader;


    public ViewPagerAdapter(List sliderImg,Context context) {
        this.sliderImg = sliderImg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, null);

        final SliderUtils utils = sliderImg.get(position);

        ImageView imageView = view.findViewById(R.id.imageView);

        Picasso.with(context)
                .load(utils.getSliderImageUrl())
                .into(imageView);

//        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
//        imageLoader.get(utils.getSliderImageUrl(), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));


//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(context, utils.getSliderImageUrl(), Toast.LENGTH_SHORT).show();
//
////                if(position == 0){
////                    Toast.makeText(context, utils.getSliderImageUrl(), Toast.LENGTH_SHORT).show();
////                } else if(position == 1){
////                    Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
////                }
//
//            }
//        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}