package com.ust.model;

/**
 * Created by Jude on 3/7/2016.
 */
public class BeanFactory {

    public static ItemBean createBean(String name, int quantity, double price, double total){
        ItemBean jaba = new ItemBean();
        jaba.setName(name);
        jaba.setPrice(price);
        jaba.setQuantity(quantity);
        jaba.setTotal(total);
        return jaba;
    }
}
