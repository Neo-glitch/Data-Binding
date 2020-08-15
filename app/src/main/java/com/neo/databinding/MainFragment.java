package com.neo.databinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.neo.databinding.adapters.ProductsAdapter;
import com.neo.databinding.databinding.FragmentMainBinding;
import com.neo.databinding.models.Product;
import com.neo.databinding.util.Products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "MainFragment";

    // Data binding
    FragmentMainBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        setupProductsList();

        return mBinding.getRoot();
    }

    /**
     * method to setup List to be assigned to the adapter
     */
    private void setupProductsList(){
        Products products = new Products();
        List<Product> productList = new ArrayList<>();
        productList.addAll(Arrays.asList(products.PRODUCTS));
        mBinding.setProducts(productList);                              // assigns list to the list in xml
    }



    @Override
    public void onRefresh() {
        Products products = new Products();
        ArrayList<Product> productList = new ArrayList<>();
        productList.addAll(Arrays.asList(products.PRODUCTS));
        ((ProductsAdapter) mBinding.recyclervView.getAdapter()).refreshList(productList);              // calls the adapter refreshList() method
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        (mBinding.recyclervView.getAdapter()).notifyDataSetChanged();
        mBinding.swipeRefreshLayout.setRefreshing(false);
    }
}














