package com.neo.databinding;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.neo.databinding.databinding.ActivityMainBinding;
import com.neo.databinding.models.CartItem;
import com.neo.databinding.models.CartViewModel;
import com.neo.databinding.models.Product;
import com.neo.databinding.util.PreferenceKeys;
import com.neo.databinding.util.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String TAG = "MainActivity";

    //data binding
    ActivityMainBinding mBinding;

    //vars
    private boolean mClickToExit = false;

    // vars for checkout Simulation
    private Runnable mCheckoutRunnable;
    private Handler mCheckoutHandler;
    private int mCheckoutTimer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);       // same as setContentView()
        mBinding.cart.setOnTouchListener(new CartTouchListener());
        mBinding.proceedToCheckout.setOnClickListener(mCheckOutListener);
        init();
        getShoppingCart();
    }

    private void init() {
        MainFragment fragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment, getString(R.string.fragment_main));
        transaction.commit();
    }


    /**
     * gets num of items in the shopping cart, i.e via sharedPreferences and populate the tv onTop cart icon
     */
    private void getShoppingCart() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());

        Products products = new Products();
        ArrayList<CartItem> cartItems = new ArrayList<>();                                          // list of cartItems
        for (String serialNumber : serialNumbers) {                                                 // iters through SN in set where SN matches to a product
            int quantity = preferences.getInt(serialNumber, 0);                            // get quantity of product in focus, using its SN key
            cartItems.add(new CartItem(products.PRODUCT_MAP.get(serialNumber), quantity));          // adds product and quantity to cartItems lis
        }

        CartViewModel viewModel = new CartViewModel();
        viewModel.setCart(cartItems);
        try {
            viewModel.setCartVisible(mBinding.getCartView().isCartVisible());
        } catch (NullPointerException e) {   // avoid crash when cartViewFragment has never been shown making the cartView to be null in .getCartView
            Log.e(TAG, "getShoppingCart: NullPointerException" + e.getMessage());
        }
        mBinding.setCartView(viewModel);
    }


    /**
     * method to simulate checking out feature
     */
    public void checkout(){
        mBinding.progressBar.setVisibility(View.VISIBLE);
        mCheckoutHandler = new Handler(getMainLooper());
        mCheckoutRunnable = new Runnable() {
            @Override
            public void run() {
                mCheckoutHandler.postDelayed(mCheckoutRunnable, 200);
                mCheckoutTimer += 200;                                      // done since we want time to match the postDelayed time
                if(mCheckoutTimer >= 1600){
                    emptyCart();                                            // empty cart and reset the sharedPreferences
                    mBinding.progressBar.setVisibility(View.GONE);
                    mCheckoutHandler.removeCallbacks(mCheckoutRunnable);     // rem callbacks from handler
                    mCheckoutTimer = 0;                                      // reset checkOut Timer
                }
            }
        };
        mCheckoutRunnable.run();
    }

    private void emptyCart() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        for(String serialNumber : serialNumbers){               // loops through set and removes the itemQuantities
            editor.remove(serialNumber);
        }
        // remove shopping cart set
        editor.remove(PreferenceKeys.shopping_cart);
        editor.commit();

        Toast.makeText(this, "Thanks for doing business with us", Toast.LENGTH_LONG).show();
        removeViewCartFragment();
        getShoppingCart();                                      //
    }

    public View.OnClickListener mCheckOutListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkout();
        }
    };

    /**
     * custom onTouch listener to make cartIcon background darker when pressed and normal when released
     */
    public static class CartTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(v.getContext().getResources().getColor(R.color.blue6));
                v.performClick();

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(v.getContext().getResources().getColor(R.color.blue4));
                v.performClick();

                IMainActivity iMainActivity = (IMainActivity)v.getContext();
                iMainActivity.inflateViewCartFragment();
            }
            return true;
        }
    }



    public void removeViewCartFragment(){
        getSupportFragmentManager().popBackStack();
        ViewCartFragment fragment = (ViewCartFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_cart));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragment != null){
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    @Override
    public void inflateViewProductFragment(Product product) {
        Log.d(TAG, "inflateViewProductFragment: called.");

        ViewProductFragment fragment = new ViewProductFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.intent_product), product);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment, getString(R.string.fragment_view_product))
                .addToBackStack(getString(R.string.fragment_view_product));
        transaction.commit();

    }


    @Override
    public void showQuantityDialog() {               // show the dialog for choosing item quantity
        Log.d(TAG, "showQuantityDialog: showing Quantity Dialog.");
        ChooseQuantityDialog dialog = new ChooseQuantityDialog();
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_choose_quantity));
    }


    @Override
    public void setQuantity(int quantity) {     // passes the quantity to ViewProductFragment
        Log.d(TAG, "selectQuantity: selected quantity: " + quantity);

        ViewProductFragment fragment = (ViewProductFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_product));
        if (fragment != null) {
            // n.b done without destroying and recreating fragment to get bundle since done with dataBinding, since productView is observable
            fragment.mBinding.getProductView().setQuantity(quantity);
        }
    }

    @Override
    public void addToCart(Product product, int quantity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // gets set where serial number is stored, if not stored return empty set
        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        serialNumbers.add(String.valueOf(product.getSerial_number()));
        editor.putStringSet(PreferenceKeys.shopping_cart, serialNumbers);
        editor.commit();

        // gets current Quantity value in preferences associated with this product using serial num, if none returns 0
        int currentQuantity = preferences.getInt(String.valueOf(product.getSerial_number()), 0);
        editor.putInt(String.valueOf(product.getSerial_number()), (currentQuantity + quantity));

        editor.commit();

        // reset quantity in ViewProductFragment
        setQuantity(1);

        Toast.makeText(this, "added to cart", Toast.LENGTH_LONG).show();

        // update the quantity value
        getShoppingCart();
    }


    @Override
    public void inflateViewCartFragment() {
        // checks to see if fragment already visible
        ViewCartFragment fragment = (ViewCartFragment) getSupportFragmentManager().findFragmentByTag(String.valueOf(R.string.fragment_view_cart));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment == null) {
            fragment = new ViewCartFragment();
            transaction.replace(R.id.main_container, fragment, getString(R.string.fragment_view_cart))
                    .addToBackStack(getString(R.string.fragment_view_cart));
            transaction.commit();
        }
    }

    // decides whether to make extra cart toolbar widget visible or not
    @Override
    public void setCartVisibility(boolean visibility) {
        mBinding.getCartView().setCartVisible(visibility);
    }

    /**
     * update the quantity tv in customToolbar and via the product updated and the new quantity added pr subtracted
     */
    @Override
    public void updateQuantity(Product product, int quantity) {
        Log.d(TAG, "updateQuantity: called");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        int currentQuantity = preferences.getInt(String.valueOf(product.getSerial_number()), 0);
        editor.putInt(String.valueOf(product.getSerial_number()), currentQuantity + quantity);
        editor.commit();

        getShoppingCart();                // called to update the tv in custom toolbar
    }

    /**
     * removes cartItem passed from the cartItemList and from sharedPreferences
     */
    @Override
    public void removeCartItem(CartItem cartItem) {
        Log.d(TAG, "removeCartItem: called");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // removes the productQuantity using the key i.e SN
        editor.remove(String.valueOf(cartItem.getProduct().getSerial_number()));
        editor.commit();

        // remove product using the serialNumber using Key shopping
        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        if (serialNumbers.size() == 1){  // if item is only product, clear it and remove set having this key from preferences
            editor.remove(PreferenceKeys.shopping_cart);
            editor.commit();
        } else{       // remove item from set and update preferences with new set
            serialNumbers.remove(String.valueOf(cartItem.getProduct().getSerial_number()));
            editor.putStringSet(PreferenceKeys.shopping_cart, serialNumbers);
            editor.commit();
        }
        getShoppingCart();

        // removes item from list in ViewCartFragment
        ViewCartFragment fragment = (ViewCartFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fragment_view_cart));
        if(fragment != null){
            fragment.updateCartItems();
        }


    }




}











