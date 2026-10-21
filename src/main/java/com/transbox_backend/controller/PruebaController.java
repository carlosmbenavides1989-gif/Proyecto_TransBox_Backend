package com.transbox_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PruebaController {

    @GetMapping("/api/prueba")
    public String probarBackend() {
        return "TransBox backend funcionando correctamente";
    }

}