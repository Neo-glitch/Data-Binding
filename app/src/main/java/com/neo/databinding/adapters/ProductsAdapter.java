package com.neo.databinding.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.databinding.IMainActivity;
import com.neo.databinding.R;
import com.neo.databinding.databinding.ProductItemBinding;
import com.neo.databinding.models.Product;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class ProductsAdapter extends  RecyclerView.Adapter<ProductsAdapter.BindingHolder>{

    private static final String TAG = "ProductsAdapter";

    private List<Product> mProducts = new ArrayList<>();
    private Context mContext;

    public ProductsAdapter(Context context, List<Product> products) {
        mProducts = products;
        mContext = context;
    }

    public void refreshList(List<Product> products){
        mProducts.clear();
        mProducts.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ViewDataBinding binding = DataBindingUtil.inflate(                               // generic implementation
//                LayoutInflater.from(mContext), R.layout.product_item, parent, false);
        ProductItemBinding binding = DataBindingUtil.inflate(                               // inflates the viewHolder.xml
                LayoutInflater.from(mContext), R.layout.product_item, parent, false);

        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Product product = mProducts.get(position);
        holder.binding.setProduct(product);                                                 // sets the retrieved product to product var in product_item.xml
//        holder.binding.setVariable(BR.product, product);                                  // generic implementation
        holder.binding.setIMainActivity((IMainActivity) mContext);                          // init the interface listener used in the holder layout
        holder.binding.executePendingBindings();                                            // update Views that need data from the product var
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    // View holder Class
    public class BindingHolder extends RecyclerView.ViewHolder{

//        ViewDataBinding binding;                              //generic implementation
        ProductItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }



}













