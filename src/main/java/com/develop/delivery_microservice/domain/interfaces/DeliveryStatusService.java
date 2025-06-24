package com.develop.delivery_microservice.domain.interfaces;

import com.develop.delivery_microservice.application.dtos.DeliveryStatusResponseDto;

import java.util.List;

public interface DeliveryStatusService {
    List<DeliveryStatusResponseDto> getAllStatuses();
}
