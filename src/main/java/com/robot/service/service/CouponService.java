package com.robot.service.service;

import com.robot.service.controller.CouponController;
import com.robot.service.model.CouponTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CouponService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createCoupon() {
        for (int i = 0; i < 100; i++) {
            String str = UUID.randomUUID().toString();
            String sql = "INSERT INTO coupon (coupon_ticket, is_used) VALUE(?, ?)";
            jdbcTemplate.update(sql, new Object[]{str, false});
        }
    }

    public List<CouponTicket> getNoUseCoupon() {
        List<CouponTicket> couponTickets = new ArrayList<>();
        String sql = "SELECT * FROM coupon WHERE is_used = false";
        List<Map<String, Object>> mapArrayList = new ArrayList<>();
        mapArrayList = jdbcTemplate.queryForList(sql);
        if (mapArrayList != null && mapArrayList.size() > 0) {
            for (int i = 0; i < mapArrayList.size(); i++) {
                CouponTicket couponTicket = new CouponTicket();
                couponTicket.setId((Integer) mapArrayList.get(i).get("id"));
                couponTicket.setCouponTicket((String) mapArrayList.get(i).get("coupon_ticket"));
                couponTicket.setUsed((Boolean) mapArrayList.get(i).get("is_used"));
                couponTickets.add(couponTicket);
            }
        }
        return couponTickets;
    }
}
