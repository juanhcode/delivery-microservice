package com.develop.delivery_microservice.application.use_cases;

import com.develop.delivery_microservice.application.dtos.DeliveryRequestDto;
import com.develop.delivery_microservice.application.dtos.DeliveryResponseDto;
import com.develop.delivery_microservice.domain.interfaces.DeliveryMapper;
import com.develop.delivery_microservice.domain.interfaces.DeliveryService;
import com.develop.delivery_microservice.domain.models.Delivery;
import com.develop.delivery_microservice.infrastructure.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public List<DeliveryResponseDto> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return DeliveryMapper.toDeliveryResponseDto(deliveries);
    }

    @Override
    public DeliveryResponseDto getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id " + id));
        return DeliveryMapper.toDeliveryResponseDto(delivery);
    }

    @Override
    public DeliveryResponseDto createDelivery(DeliveryRequestDto deliveryRequestDto) {
        Delivery delivery = DeliveryMapper.toDelivery(deliveryRequestDto);
        delivery.setId(null); // Asegura que se genere automáticamente
        Delivery saved = deliveryRepository.save(delivery);
        return DeliveryMapper.toDeliveryResponseDto(saved);
    }

    @Override
    public DeliveryResponseDto updateDelivery(Long id, DeliveryRequestDto deliveryRequestDto) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id " + id));

        delivery.setDelivered(deliveryRequestDto.getDelivered());
        delivery.setStatusId(deliveryRequestDto.getStatusId());

        Delivery updated = deliveryRepository.save(delivery);
        return DeliveryMapper.toDeliveryResponseDto(updated);
    }

    @Override
    public void deleteDelivery(Long id) {
        if (!deliveryRepository.existsById(id)) {
            throw new RuntimeException("Delivery not found with id " + id);
        }
        deliveryRepository.deleteById(id);
    }
}

