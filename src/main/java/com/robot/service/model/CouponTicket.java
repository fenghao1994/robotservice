package com.robot.service.model;

import javax.persistence.*;

@Entity
@Table(name = "coupon")
public class CouponTicket {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column
    private String couponTicket;
    @Column
    private boolean isUsed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCouponTicket() {
        return couponTicket;
    }

    public void setCouponTicket(String couponTicket) {
        this.couponTicket = couponTicket;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
