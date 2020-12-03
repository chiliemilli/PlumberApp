package com.kubsu.plumberapp;

import java.io.Serializable;

enum OrderStatus {
    DEFAULT, // 2
    SEEN,  // 3 просмотрел
    STARTED, // 4 приступил к выполнению
    DONE, // 5 заврешил вполнение
    ERROR
}

class OrderInfoModel implements Serializable {

    private String orderType;
    private String orderDescription;
    private String orderAddress;
    private String phoneNumber;
    private String orderPrice;
    private int orderId;
    private boolean noOrder;

    public boolean isNoOrder() {
        return noOrder;
    }

    public void setNoOrder(boolean noOrder) {
        this.noOrder = noOrder;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    private OrderStatus status;

    public void setStatus(int statusId) {
        OrderStatus status;

        switch (statusId) {
            case 2: status = OrderStatus.DEFAULT;
            break;
            case 3: status = OrderStatus.SEEN;
            break;
            case 4: status = OrderStatus.STARTED;
            break;
            case 5:status = OrderStatus.DONE;
            break;

            default: status = OrderStatus.ERROR;
        }

        this.status = status;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String address) {
        this.orderAddress = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String payment) {
        this.orderPrice = payment;
    }
}
