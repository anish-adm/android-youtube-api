package com.anish.youtubeapi.ui;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageBinder {

    @BindingAdapter({"imageURL"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(imageUrl).into(view);
    }
}
