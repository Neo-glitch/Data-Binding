package com.neo.databinding.util;

import com.neo.databinding.models.Product;

import java.math.BigDecimal;
import java.util.HashMap;


/**
 * class returns the prices of the product obj and this prices are stored in a hashMap of keys and values
 */
public class Prices {

    private static final HashMap<String, BigDecimal> PRICES;
    static
    {
        PRICES = new HashMap<String, BigDecimal>();
        Products products = new Products();
        for(Product product : products.PRODUCTS){
            PRICES.put(String.valueOf(product.getSerial_number()), product.getPrice());
        }
    }



    public static HashMap<String, BigDecimal> getPrices(){
        return  PRICES;
    }
}
