package com.sadri.bms.controller.user.admin;

import com.sadri.bms.common.dto.user.UserIn;
import com.sadri.bms.common.dto.user.UserOut;
import com.sadri.bms.model.service.user.AdminUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping(path = "${rest.admin}")
public class AdminUserController {

    private final AdminUserService service;

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<UserOut> getById(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(service.getById(userId));
    }

    @GetMapping(path = "/user")
    public List<UserOut> getAll() {
        return null;
    }

    @PostMapping(path = "/user/{role}")
    public ResponseEntity<UserOut> create(@Valid @RequestBody UserIn user,
                                          @PathVariable(name = "role") String role) {
        return ResponseEntity.ok(service.create(user, role));
    }
}