package com.sadri.bms.controller.user.admin;

import com.sadri.bms.common.dto.user.UserIn;
import com.sadri.bms.common.dto.user.UserOut;
import com.sadri.bms.model.service.user.AdminUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "${rest.admin}")
public class AdminUserController {

    private final AdminUserService service;

    @GetMapping(path = "/user/{userId}")
    public UserOut getById(@PathVariable(name = "userId") Long userId) {
        return null;
    }

    @GetMapping(path = "/user")
    public List<UserOut> getAll() {
        return null;
    }

    @PostMapping(path = "/user/{role}")
    public ResponseEntity<UserOut> create(@RequestBody UserIn user,
                                          @PathVariable(name = "role") String role) {
        return ResponseEntity.ok(service.create(user, role));
    }
}