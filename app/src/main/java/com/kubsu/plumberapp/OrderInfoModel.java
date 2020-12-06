package com.kubsu.plumberapp;

import java.io.Serializable;


class OrderInfoModel implements Serializable {

    private String orderType;
    private String orderDescription;
    private String orderAddress;
    private Long phoneNumber;
    private String orderPrice;
    private int orderId;
    private boolean orderStatus;
    private int plumberID;
    private String plumberStatus;

    public String getPlumberStatus() {
        return plumberStatus;
    }

    public void setPlumberStatus(String plumberStatus) {
        this.plumberStatus = plumberStatus;
    }


    public int getPlumberID() {
        return plumberID;
    }

    public void setPlumberID(int plumberID) {
        this.plumberID = plumberID;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return  "Тип заказа: " + orderType  +"\n"+
                "Описание заказа: " + orderDescription + "\n"+
                "Адрес заказчика: " + orderAddress + "\n"+
                "Тел. номер заказчика: " + phoneNumber +"\n"+
                "Стоимость: " + orderPrice ;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public void setOrderAddress(String address) {
        this.orderAddress = address;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOrderPrice(String payment) {
        this.orderPrice = payment;
    }
}
