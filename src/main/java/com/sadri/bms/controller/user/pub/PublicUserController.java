package com.sadri.bms.controller.user.pub;

import com.sadri.bms.common.dto.user.UserIn;
import com.sadri.bms.common.dto.user.UserOut;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${rest.pub}")
public class PublicUserController {

    @PostMapping(path = "/user")
    public UserOut create(@RequestBody UserIn user) {

        return null;
    }

    @GetMapping(path = "/user")
    public UserOut userInfo() {

        return null;
    }
}