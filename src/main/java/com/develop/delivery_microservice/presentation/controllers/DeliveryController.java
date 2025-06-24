package com.develop.delivery_microservice.presentation.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @GetMapping
    public String listDeliveries() {
        return "List of deliveries";
    }
}
