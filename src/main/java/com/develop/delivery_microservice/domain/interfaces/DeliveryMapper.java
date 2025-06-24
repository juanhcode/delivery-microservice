package com.develop.delivery_microservice.domain.interfaces;

import com.develop.delivery_microservice.application.dtos.DeliveryRequestDto;
import com.develop.delivery_microservice.application.dtos.DeliveryResponseDto;
import com.develop.delivery_microservice.domain.models.Delivery;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryMapper {

    public  static Delivery toDelivery(DeliveryRequestDto deliveryRequestDto) {
        Delivery delivery = new Delivery();
        delivery.setPurchaseId(deliveryRequestDto.getPurchaseId());
        delivery.setDelivered(deliveryRequestDto.getDelivered());
        delivery.setStatusId(deliveryRequestDto.getStatusId());
        delivery.setUserId(deliveryRequestDto.getUserId());
        return delivery;
    }

    public static DeliveryRequestDto toDeliveryRequestDto(Delivery delivery) {
        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto();
        deliveryRequestDto.setPurchaseId(delivery.getPurchaseId());
        deliveryRequestDto.setDelivered(delivery.getDelivered());
        deliveryRequestDto.setStatusId(delivery.getStatusId());
        deliveryRequestDto.setUserId(delivery.getUserId());
        return deliveryRequestDto;
    }

    public static DeliveryResponseDto toDeliveryResponseDto(Delivery delivery) {
        DeliveryResponseDto deliveryResponseDto = new DeliveryResponseDto();
        deliveryResponseDto.setId(delivery.getId());
        deliveryResponseDto.setPurchaseId(delivery.getPurchaseId());
        deliveryResponseDto.setDelivered(delivery.getDelivered());
        deliveryResponseDto.setStatusId(delivery.getStatusId());
        deliveryResponseDto.setUserId(delivery.getUserId());
        return deliveryResponseDto;
    }

    public static List<DeliveryResponseDto> toDeliveryResponseDto(List<Delivery> deliveries) {
        return deliveries.stream()
                .map(
                    DeliveryMapper::toDeliveryResponseDto
                ).collect(Collectors.toList());
    }
}
