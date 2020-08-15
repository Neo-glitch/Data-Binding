package com.neo.databinding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.neo.databinding.databinding.ActivityMainBinding;
import com.neo.databinding.models.Product;

public class MainActivity extends AppCompatActivity implements IMainActivity{
    // data Binding
    ActivityMainBinding mBinding;       // named after the layout name plus Binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);    // same as setContentView
        init();
    }

    /**
     * performs transaction and inflate fragment
     */
    private void init(){
        MainFragment fragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment, getString(R.string.fragment_main));
        transaction.commit();
    }

    @Override
    public void inflateViewProductFragment(Product product) {
        ViewProductFragment fragment = new ViewProductFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.intent_product), product);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment, getString(R.string.fragment_view_product));
        transaction.commit();

    }
}
