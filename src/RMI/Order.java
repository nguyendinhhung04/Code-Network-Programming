/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMI;

import java.io.Serializable;

/**
 *
 * @author dinhh
 */
// ====== Lớp Order (theo đề bài) ======
class Order implements Serializable {
    private static final long serialVersionUID = 20241132L;

    private String id;
    private String customerCode;
    private String orderDate;
    private String shippingType;
    private String orderCode;

    public Order() {}

    public Order(String id, String customerCode, String orderDate, String shippingType) {
        this.id = id;
        this.customerCode = customerCode;
        this.orderDate = orderDate;
        this.shippingType = shippingType;
    }

    // Getter / Setter
    public String getId() { return id; }
    public String getCustomerCode() { return customerCode; }
    public String getOrderDate() { return orderDate; }
    public String getShippingType() { return shippingType; }
    public String getOrderCode() { return orderCode; }

    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }

    @Override
    public String toString() {
        return String.format(
            "Order{id='%s', customerCode='%s', orderDate='%s', shippingType='%s', orderCode='%s'}",
            id, customerCode, orderDate, shippingType, orderCode
        );
    }
}
