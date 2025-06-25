package com.develop.delivery_microservice.application.dtos;
import lombok.Data;

@Data
public class DeliveryResponseDto {
    private Long id;
    private Boolean delivered;
    private Long statusId;
    private Long userId;
}
