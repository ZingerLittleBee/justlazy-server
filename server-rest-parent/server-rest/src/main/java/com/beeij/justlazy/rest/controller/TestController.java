package com.beeij.justlazy.rest.controller;

import com.beeij.justlazy.mysql.dao.model.UserDo;
import com.beeij.justlazy.mysql.dao.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-05-29
 * @since 2021-05-29 20:34
 */

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public UserDo getUser() {
        return userService.getById("34324324");
    }

}
