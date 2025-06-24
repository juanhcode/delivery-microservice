package com.develop.delivery_microservice.domain.interfaces;

import com.develop.delivery_microservice.application.dtos.DeliveryResponseDto;

import java.util.List;

import com.develop.delivery_microservice.application.dtos.DeliveryRequestDto;

public interface DeliveryService {
    List<DeliveryResponseDto> getAllDeliveries();
    DeliveryResponseDto getDeliveryById(Long id);
    DeliveryResponseDto createDelivery(DeliveryRequestDto deliveryRequestDto);
    DeliveryResponseDto updateDelivery(Long id, DeliveryRequestDto deliveryRequestDto);
    void deleteDelivery(Long id);
}

