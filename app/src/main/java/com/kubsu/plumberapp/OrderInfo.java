package com.kubsu.plumberapp;

enum OrderStatus {
    DEFAULT, // 2
    SEEN,  // 3 просмотрел
    STARTED, // 4 приступил к выполнению
    DONE, // 5 заврешил вполнение
    ERROR
}

class OrderInfo {

    private String orderType;
    private String orderDescription;
    private String address;
    private String phoneNumber;
    private int payment;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
