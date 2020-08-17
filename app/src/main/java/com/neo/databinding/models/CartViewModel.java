package com.neo.databinding.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.neo.databinding.BR;
import com.neo.databinding.util.BigDecimalUtil;
import com.neo.databinding.util.Prices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseObservable class for list of cartItems
 */
public class CartViewModel extends BaseObservable {
    private List<CartItem> cart = new ArrayList<>();        // list of cartItems
    private boolean isCartVisible;                          // when true show cartTool bar else hide

    @Bindable
    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
        notifyPropertyChanged(BR.cart);
    }

    @Bindable
    public boolean isCartVisible() {
        return isCartVisible;
    }

    public void setCartVisible(boolean cartVisible) {
        isCartVisible = cartVisible;
        notifyPropertyChanged(BR.cartVisible);
    }

    /**
     * method to show total number of items in the toolbar tv
     */
    public String getProductQuantitiesString() {
        int totalItems = 0;
        for (CartItem cartItem : cart) {
            totalItems += cartItem.getQuantity();       // gets all quantity associated with each cartItem
        }

        // logic to choose whether to use items or item based on num of item
        String s = "";
        if (totalItems > 1) {
            s = "items";
        } else {
            s = "item";
        }
        return ("(" + String.valueOf(totalItems) + " " + s + ")");
    }


    /**
     * gets total cost of all items based on quantities
     */
    public String getTotalCostString(){
        double totalCost = 0;       // stores cost of all items in cart

        for(CartItem cartItem : cart){
            int productQuantity = cartItem.getQuantity();     // get quantity of each item

            // gets cost of based on the quantity
            double cost = productQuantity * (Prices.getPrices().get(String.valueOf(cartItem.getProduct().getSerial_number()))).doubleValue();
            totalCost += cost;
        }
        return "$" + BigDecimalUtil.getValue(new BigDecimal(totalCost));
    }
}
