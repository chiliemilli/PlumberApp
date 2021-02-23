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
    private String orderWorkingStatus;

    public void setOrderWorkingStatus(String orderWorkingStatus) {
        this.orderWorkingStatus = orderWorkingStatus;
    }

    public String getOrderWorkingStatus() {
        return orderWorkingStatus;
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
        return  "Тип заказа: " + orderType  +"\n"+"\n"+
                "Описание заказа: " + orderDescription + "\n"+"\n"+
                "Адрес заказчика: " + orderAddress + "\n"+"\n"+
                "Тел. номер заказчика: " + phoneNumber +"\n"+"\n"+
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
