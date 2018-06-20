package com.robot.service.service;

import com.robot.service.model.CouponTicket;
import com.robot.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 1.已注册 2. 邀请码错误 3.邀请码被使用 4.注册成功 5. 注册失败 未知错误
     *
     * @param userName
     * @param password
     * @param ticket
     * @return
     */
    public int register(String userName, String password, String ticket) {
        User user = isExists(userName);
        if (user != null) {
            return 1;
        }
        CouponTicket coupon = getCoupon(ticket);
        if (coupon == null) {
            return 2;
        }
        if (coupon.isUsed()) {
            return 3;
        }
        int register = register(userName, password, coupon.getId());
        if (register >= 0) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * 1. 登录成功 2 密码错误 3 账号不存在
     * @param userName
     * @param password
     * @return
     */
    public int login(String userName, String password) {
        User user = isExists(userName);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return 1;
            } else {
                return 2;
            }
        }
        return 3;
    }

    /**
     * 注册账户
     *
     * @return
     */
    private int register(String userName, String password, int ticketId) {
        String sql = "INSERT INTO user (user_name, password, ticket_id) VALUE (?, ?, ?)";
        int update = jdbcTemplate.update(sql, new Object[]{userName, password, ticketId});
        if (update > 0) {
            String sql1 = "UPDATE coupon SET is_used = ? WHERE id = ?";
            jdbcTemplate.update(sql1, new Object[]{true, ticketId});
        }
        return update;
    }

    /**
     * 获取邀请码
     *
     * @param ticket
     * @return
     */
    private CouponTicket getCoupon(String ticket) {
        String sql = "SELECT * FROM coupon WHERE coupon_ticket = ?";
        RowMapper<CouponTicket> rowMapper = new BeanPropertyRowMapper<>(CouponTicket.class);
        CouponTicket couponTicket;
        try {
            couponTicket = jdbcTemplate.queryForObject(sql, rowMapper, ticket);
        } catch (Exception e) {
            couponTicket = null;
        }
        return couponTicket;
    }

    /**
     * 是否已经注册
     *
     * @param userName
     * @return
     */
    private User isExists(String userName) {
        String sql = "SELECT * FROM user WHERE user_name = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        User user;
        try {
            user = jdbcTemplate.queryForObject(sql, rowMapper, userName);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }


}
