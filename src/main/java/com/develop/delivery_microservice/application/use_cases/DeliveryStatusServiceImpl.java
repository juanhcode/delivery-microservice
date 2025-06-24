package com.develop.delivery_microservice.application.use_cases;

import com.develop.delivery_microservice.application.dtos.DeliveryStatusResponseDto;
import com.develop.delivery_microservice.domain.interfaces.DeliveryStatusMapper;
import com.develop.delivery_microservice.domain.interfaces.DeliveryStatusService;
import com.develop.delivery_microservice.domain.models.DeliveryStatus;
import com.develop.delivery_microservice.infrastructure.repositories.DeliveryStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;
    @Override
    public List<DeliveryStatusResponseDto> getAllStatuses() {
        List<DeliveryStatus> statuses = deliveryStatusRepository.findAll();
        if (statuses.isEmpty()) {
            throw new RuntimeException("No delivery statuses found");
        }
        return DeliveryStatusMapper.toDeliveryStatusResponseDto(statuses);
    }


}
