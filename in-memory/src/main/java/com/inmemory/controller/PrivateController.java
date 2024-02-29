package com.inmemory.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    public String helloWorld() {
        return "Private endpoint";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String helloWorldAdmin() {
        return "Private Admin endpoint";
    }

    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String helloWorldUser() {
        return "Private User endpoint";
    }

}
