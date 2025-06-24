package com.develop.delivery_microservice.domain.interfaces;

import com.develop.delivery_microservice.application.dtos.DeliveryStatusResponseDto;
import com.develop.delivery_microservice.domain.models.DeliveryStatus;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryStatusMapper {

    public static DeliveryStatusResponseDto toDeliveryStatusResponseDto(DeliveryStatus status) {
        DeliveryStatusResponseDto responseDto = new DeliveryStatusResponseDto();
        responseDto.setId(status.getId());
        responseDto.setName(status.getName());
        return responseDto;
    }

     public static List<DeliveryStatusResponseDto> toDeliveryStatusResponseDto(List<DeliveryStatus> statuses) {
         return statuses.stream()
                 .map(DeliveryStatusMapper::toDeliveryStatusResponseDto)
                 .collect(Collectors.toList());
     }
}
