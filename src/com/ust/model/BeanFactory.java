package com.ust.model;

/**
 * Created by Jude on 3/7/2016.
 */
public class BeanFactory {

    //mGa bea
    public static ItemBean createBean(String name,  double price, int quantity, double total){
        ItemBean jaba = new ItemBean();
        jaba.setName(name);
        jaba.setPrice(price);
        jaba.setQuantity(quantity);
        jaba.setTotal(total);
        return jaba;
    }
    public static RecieptBean createBean(String companyName,String name, String address, String recieptNo){
        RecieptBean recieptBean= new RecieptBean();
        recieptBean.setCompanyName(companyName);
        recieptBean.setRecieptNumber(recieptNo);
        recieptBean.setCustomerName(name);
        recieptBean.setCustomerAddress(address);

        return recieptBean;

    }
}
