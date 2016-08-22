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
    public static ReceiptBean createBean(String companyName, String name, String address, String recieptNo){
        ReceiptBean receiptBean = new ReceiptBean();
        receiptBean.setCompanyName(companyName);
        receiptBean.setRecieptNumber(recieptNo);
        receiptBean.setCustomerName(name);
        receiptBean.setCustomerAddress(address);

        return receiptBean;

    }
}
