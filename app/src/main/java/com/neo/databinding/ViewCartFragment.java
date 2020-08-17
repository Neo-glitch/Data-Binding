package com.neo.databinding;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neo.databinding.databinding.FragmentViewCartBinding;
import com.neo.databinding.models.CartItem;
import com.neo.databinding.models.CartViewModel;
import com.neo.databinding.util.PreferenceKeys;
import com.neo.databinding.util.Products;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by User on 2/8/2018.
 */

public class ViewCartFragment extends Fragment {

    private static final String TAG = "ViewCartFragment";

    //data binding
    FragmentViewCartBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewCartBinding.inflate(inflater);
		mBinding.setIMainActivity((IMainActivity)getActivity());
		mBinding.getIMainActivity().setCartVisibility(true);

		getShoppingCartList();

        return mBinding.getRoot();             // must be done since a fragment
    }

    private void getShoppingCartList(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());

        Products products = new Products();
        List<CartItem> cartItems = new ArrayList<>();
        for(String serialNumber : serialNumbers){
            int quantity = preferences.getInt(serialNumber, 0);
            cartItems.add(new CartItem(products.PRODUCT_MAP.get(serialNumber), quantity));         // gets list of cart items
        }
        CartViewModel cartViewModel = new CartViewModel();
        cartViewModel.setCart(cartItems);
        mBinding.setCartView(cartViewModel);                                                       // set the ViewModel var to the binding
    }

    public void updateCartItems(){
        getShoppingCartList();
    }

    @Override
    public void onDestroy() {
        mBinding.getIMainActivity().setCartVisibility(false);       // set added toolbar visibility to false was fragment is closed
        super.onDestroy();
    }
}
















