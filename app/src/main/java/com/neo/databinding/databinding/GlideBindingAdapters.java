package com.neo.databinding.databinding;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.neo.databinding.R;

/**
 * binding adapter for setting image
 */
public class GlideBindingAdapters {


    @BindingAdapter("imageUrl")
    public static void setImage(ImageView view, int imageUrl) {
        Context context = view.getContext();

        // for customization if needed before using glide
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
    }

    @BindingAdapter("imageUrl")
    public static void setImage(ImageView view, String imageUrl) {     // overload of string ImageUrl
        Context context = view.getContext();

        // for customization if needed before using glide
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
    }

    @BindingAdapter({"requestListener", "imageResource"})                              // done this way since receives two params apart from View
    public static void bindRequestListener(ImageView view, RequestListener requestListener, int imageResource) {     // overload of string ImageUrl
        Context context = view.getContext();

        // for customization if needed before using glide
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(imageResource)
                .listener(requestListener)
                .into(view);
    }

}
