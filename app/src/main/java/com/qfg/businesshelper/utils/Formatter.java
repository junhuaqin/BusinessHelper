package com.qfg.businesshelper.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by rbtq on 8/12/16.
 */
public class Formatter {
    public static String toCurrency(float f) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINA);
        return nf.format(f);
    }

    public static float toFG(int money) {
        return money/100.f;
    }

    public static float toFG(long money) {
        return money/100.f;
    }

    public static String bgToShow(int money) {
        return toCurrency(toFG(money));
    }

    public static String bgToShow(long money) {
        return toCurrency(toFG(money));
    }

    public static int toBG(float money) {
        return (int)(money*100);
    }

}
