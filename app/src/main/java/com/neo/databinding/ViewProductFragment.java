package com.neo.databinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neo.databinding.databinding.FragmentViewProductBinding;
import com.neo.databinding.models.Product;
import com.neo.databinding.util.Products;


/**
 * Created by User on 2/6/2018.
 */

public class ViewProductFragment extends Fragment {

    private static final String TAG = "ViewProductFragment";

    // Data binding
    FragmentViewProductBinding mBinding;

    //vars
    private Product mProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mProduct = bundle.getParcelable(getString(R.string.intent_product));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewProductBinding.inflate(inflater);        // same as View view = inflater.inflate()

        ProductViewModel productView = new ProductViewModel();
        productView.setProduct(mProduct);
        productView.setQuantity(1);

        mBinding.setProductView(productView);                                  // sets the productView obj(product and quantity) in the layout dataTag

        return mBinding.getRoot();                                      // must be done since this is a fragment, same as returning th view
    }

}













