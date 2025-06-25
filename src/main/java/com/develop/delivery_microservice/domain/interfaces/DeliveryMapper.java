package com.develop.delivery_microservice.domain.interfaces;

import com.develop.delivery_microservice.application.dtos.DeliveryRequestDto;
import com.develop.delivery_microservice.application.dtos.DeliveryResponseDto;
import com.develop.delivery_microservice.domain.models.Delivery;
import com.develop.delivery_microservice.infrastructure.repositories.DeliveryStatusRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryMapper {

    public  static Delivery toDelivery(DeliveryRequestDto deliveryRequestDto) {
        Delivery delivery = new Delivery();
        delivery.setDelivered(deliveryRequestDto.getDelivered());
        delivery.setStatusId(deliveryRequestDto.getStatusId());
        delivery.setUserId(deliveryRequestDto.getUserId());
        return delivery;
    }

    public static DeliveryRequestDto toDeliveryRequestDto(Delivery delivery) {
        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto();
        deliveryRequestDto.setDelivered(delivery.getDelivered());
        deliveryRequestDto.setStatusId(delivery.getStatusId());
        deliveryRequestDto.setUserId(delivery.getUserId());
        return deliveryRequestDto;
    }

    public static DeliveryResponseDto toDeliveryResponseDto(Delivery delivery, DeliveryStatusRepository statusRepository) {
        DeliveryResponseDto dto = new DeliveryResponseDto();
        dto.setId(delivery.getId());
        dto.setDelivered(delivery.getDelivered());
        dto.setUserId(delivery.getUserId());

        // Busca el nombre del estado por el ID
        String statusName = statusRepository.findById(delivery.getStatusId())
                .map(status -> status.getName())
                .orElse("UNKNOWN");

        dto.setStatus(statusName);
        return dto;
    }

    public static List<DeliveryResponseDto> toDeliveryResponseDto(List<Delivery> deliveries, DeliveryStatusRepository statusRepository) {
        return deliveries.stream()
                .map(d -> toDeliveryResponseDto(d, statusRepository))
                .collect(Collectors.toList());
    }
}
