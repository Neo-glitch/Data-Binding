package com.neo.databinding.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * class gets big decimal obj either in a string or in a float
 */
public class BigDecimalUtil {

    public static String getValue(BigDecimal value){
        DecimalFormat df = new DecimalFormat("###,###,###.00");
        return String.valueOf(df.format(value));
    }
    /*
        For rating bar (actual rating)
     */
    public static float getFloat(BigDecimal value){
        return value.floatValue();
    }


}
