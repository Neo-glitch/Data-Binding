package com.neo.databinding.models;


import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.neo.databinding.BR;
import com.neo.databinding.IMainActivity;



/**
 * obeservable class for each item in the cartItem List RecylerView
 */
public class CartItemViewModel extends BaseObservable {
    private static final String TAG = "CartItemViewModel";
    private CartItem cartItem;                      // cart item(Product and quantity of the product)

    @Bindable
    public CartItem getCartItem(){
        return cartItem;
    }

    public void setCartItem(CartItem cartItem){
        Log.d(TAG, "setQuantity: updating cart");
        this.cartItem = cartItem;
        notifyPropertyChanged(BR.cartItem);
    }


    /**
     * displays quantity in format needed
     */
    public String getQuantityString(CartItem cartItem){
        return ("Qty: " + String.valueOf(cartItem.getQuantity()));
    }

    /**
     * method to increase the quantity by 1 for each item in ViewCartFragment]
     * when using method in layout for context param just pass keyword context
     */
    public void increaseQuantity(Context context){
        CartItem cartItem = getCartItem();                                          // gets cart item in focus
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        setCartItem(cartItem);                                                      // updates the cartItem to the updated one
        IMainActivity iMainActivity = (IMainActivity) context;
        iMainActivity.updateQuantity(cartItem.getProduct(), 1);             // passed one since, method call increase quantity by 1
    }

    public void decreaseQuantity(Context context){
        CartItem cartItem = getCartItem();
        IMainActivity iMainActivity = (IMainActivity) context;
        if(cartItem.getQuantity() > 1){                                             // to avoid getting negative values
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            setCartItem(cartItem);
            iMainActivity.updateQuantity(cartItem.getProduct(), -1);         // passed -one since, method call decrease quantity by 1
        } else if (cartItem.getQuantity() == 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            setCartItem(cartItem);
            iMainActivity.removeCartItem(cartItem);

        }
    }
}



























