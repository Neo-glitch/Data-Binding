package com.neo.databinding.databinding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.databinding.adapters.ProductsAdapter;
import com.neo.databinding.models.Product;
import com.neo.databinding.util.Products;

import java.util.List;

/**
 * does the setting up of an adapter to a recyclerView
 */
public class MainFragmentBindingAdapters {
    public static final int NUM_COLUMNS = 2;

    @BindingAdapter("productsList")                         // string repping how BindingAdapter is called in recyclerView attr
    public static void setProductsList(RecyclerView view, List<Product> products) {
        if(products == null){
            return;
        }
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if(layoutManager == null){                          // if no layoutManager associated with the Rv
            view.setLayoutManager(new GridLayoutManager(view.getContext(), NUM_COLUMNS));
        }
        ProductsAdapter adapter = (ProductsAdapter) view.getAdapter();
        if(adapter == null){
            adapter = new ProductsAdapter(view.getContext(), products);
            view.setAdapter(adapter);
        }


    }
}
