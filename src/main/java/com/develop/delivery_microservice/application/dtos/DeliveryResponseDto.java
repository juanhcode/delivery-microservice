package com.develop.delivery_microservice.application.dtos;
import lombok.Data;

@Data
public class DeliveryResponseDto {
    private Long id;
    private Boolean delivered;
    private String status;
    private Long userId;
}
