package com.robot.service.controller;

import com.robot.service.model.Result;
import com.robot.service.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 1.已注册 2. 邀请码错误 3.邀请码被使用 4.注册成功 5. 注册失败 未知错误
     *
     * @param userName
     * @param password
     * @param ticket
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Result> register(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("ticket") String ticket) {
        int flag = loginService.register(userName, password, ticket);
        Result result = new Result();
        switch (flag) {
            case 1:
                result.setCode(1);
                result.setData("账号已注册");
                break;
            case 2:
                result.setCode(2);
                result.setData("邀请码错误");
                break;
            case 3:
                result.setCode(3);
                result.setData("邀请码被注册");
                break;
            case 4:
                result.setCode(4);
                result.setData("注册成功");
                break;
            case 5:
                result.setCode(5);
                result.setData("注册失败 未知错误");
                break;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 1. 登录成功 2 密码错误 3 账号不存在
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Result> login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        int flag = loginService.login(userName, password);
        Result result = new Result();
        switch (flag) {
            case 1:
                result.setCode(1);
                result.setData("登录成功");
                break;
            case 2:
                result.setCode(2);
                result.setData("密码错误");
                break;
            case 3:
                result.setCode(3);
                result.setData("账号不存在");
                break;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
