package com.neo.databinding.databinding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.databinding.adapters.CartItemAdapter;
import com.neo.databinding.models.CartItem;

import java.util.List;

public class ViewCartFragmentBindingAdapters {
    private static final String TAG = "ViewCartFragmentBinding";

    @BindingAdapter("cartItems")
    public static void setCartItems(RecyclerView view, List<CartItem> cartItems) {
        if (cartItems == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if(layoutManager == null){
            view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
        CartItemAdapter adapter = (CartItemAdapter) view.getAdapter();
        if(adapter == null){
            adapter = new CartItemAdapter(view.getContext(), cartItems);
            view.setAdapter(adapter);
        } else{      // updates cart items if an adapter is already assigned to the RV
            adapter.updateCartItems(cartItems);
        }
    }
}
