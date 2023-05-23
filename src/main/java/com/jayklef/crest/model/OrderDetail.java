package com.jayklef.crest.model;

public class OrderDetail {

    private String productName;
    private String shipping;
    private String subtotal;
    private String tax;
    private String total;

    public OrderDetail(String productName, String shipping, String subtotal, String tax, String total) {
        this.productName = productName;
        this.shipping = shipping;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public String getSubtotal() {
        return String.format("%.2f", subtotal);
    }

    public String getShipping() {
        return String.format("%.2f", shipping);
    }

    public String getTax() {
        return String.format("%.2f", tax);
    }


    public String getTotal() {
        return String.format("%.2f", total);
    }

}
