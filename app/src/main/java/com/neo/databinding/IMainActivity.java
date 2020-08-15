package com.neo.databinding;


import com.neo.databinding.models.Product;

/**
 * interface for communication btw fragments
 */
public interface IMainActivity {
    void inflateViewProductFragment(Product product);
}
