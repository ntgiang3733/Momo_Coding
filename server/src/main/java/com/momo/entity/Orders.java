package com.momo.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "remain_money")
    private Long remainMoney;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public Orders() {
    }

    public Orders(Long id, Date createdAt, Long totalPrice, Long remainMoney) {
        this.id = id;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.remainMoney = remainMoney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(Long remainMoney) {
        this.remainMoney = remainMoney;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
