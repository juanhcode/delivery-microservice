package com.develop.delivery_microservice.application.dtos;

import lombok.Data;

@Data
public class DeliveryRequestDto {
    private Boolean delivered;
    private Long statusId;
    private Long userId;
}
