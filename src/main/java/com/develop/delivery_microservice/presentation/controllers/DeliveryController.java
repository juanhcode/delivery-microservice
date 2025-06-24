package com.develop.delivery_microservice.presentation.controllers;
import com.develop.delivery_microservice.application.dtos.DeliveryRequestDto;
import com.develop.delivery_microservice.application.dtos.DeliveryResponseDto;
import com.develop.delivery_microservice.application.dtos.DeliveryStatusResponseDto;
import com.develop.delivery_microservice.domain.interfaces.DeliveryService;
import com.develop.delivery_microservice.domain.interfaces.DeliveryStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryStatusService deliveryStatusService;



    @GetMapping
    public List<DeliveryResponseDto> listDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public DeliveryResponseDto getDeliveryById(@PathVariable Long id) {
        return deliveryService.getDeliveryById(id);
    }

    @PostMapping
    public DeliveryResponseDto createDelivery(@RequestBody DeliveryRequestDto dto) {
        return deliveryService.createDelivery(dto);
    }

    @PutMapping("/{id}")
    public DeliveryResponseDto updateDelivery(@PathVariable Long id, @RequestBody DeliveryRequestDto dto) {
        return deliveryService.updateDelivery(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
    }

    @GetMapping("/status")
    public List<DeliveryStatusResponseDto> getAllDeliveriesByStatus() {
        return deliveryStatusService.getAllStatuses();
    }
}
