package com.neo.databinding;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.neo.databinding.models.Product;


/**
 * class for implementing observable features on objects
 */
public class ProductViewModel extends BaseObservable {

    private Product product;
    private int quantity;
    private boolean imageVisibility = false;             // set to true when image is done loading


    @Bindable
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        notifyPropertyChanged(BR.product);    // notifies that the object value has changed
    }

    @Bindable
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        notifyPropertyChanged(BR.quantity);
    }

    @Bindable
    public boolean isImageVisibility() {
        return imageVisibility;
    }

    public void setImageVisibility(boolean imageVisibility) {
        this.imageVisibility = imageVisibility;
        notifyPropertyChanged(BR.imageVisibility);
    }

    /**
     * Request listener interface for glide to tell when image is done loading
     */
    public RequestListener getCustomRequestListener(){
        return new RequestListener() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target target, boolean isFirstResource) {
                setImageVisibility(true);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                setImageVisibility(true);
                return false;
            }
        };
    }
}
