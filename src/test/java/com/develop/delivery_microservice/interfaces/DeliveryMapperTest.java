package com.develop.delivery_microservice.interfaces;

import com.develop.delivery_microservice.application.dtos.DeliveryRequestDto;
import com.develop.delivery_microservice.application.dtos.DeliveryResponseDto;
import com.develop.delivery_microservice.domain.interfaces.DeliveryMapper;
import com.develop.delivery_microservice.domain.models.Delivery;
import com.develop.delivery_microservice.domain.models.DeliveryStatus;
import com.develop.delivery_microservice.infrastructure.repositories.DeliveryStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryMapperTest {

    private Delivery delivery;
    private DeliveryRequestDto requestDto;

    @BeforeEach
    void setup() {
        delivery = new Delivery();
        delivery.setId(1L);
        delivery.setDelivered(true);
        delivery.setUserId(5L);
        delivery.setStatusId(2L);

        requestDto = new DeliveryRequestDto();
        requestDto.setDelivered(true);
        requestDto.setStatusId(2L);
        requestDto.setUserId(5L);
    }

    @Test
    void testToDelivery() {
        Delivery result = DeliveryMapper.toDelivery(requestDto);

        assertEquals(requestDto.getDelivered(), result.getDelivered());
        assertEquals(requestDto.getStatusId(), result.getStatusId());
        assertEquals(requestDto.getUserId(), result.getUserId());
    }

    @Test
    void testToDeliveryRequestDto() {
        DeliveryRequestDto result = DeliveryMapper.toDeliveryRequestDto(delivery);

        assertEquals(delivery.getDelivered(), result.getDelivered());
        assertEquals(delivery.getStatusId(), result.getStatusId());
        assertEquals(delivery.getUserId(), result.getUserId());
    }

    @Test
    void testToDeliveryResponseDto_StatusFound() {
        DeliveryStatusRepository mockRepo = mock(DeliveryStatusRepository.class);
        DeliveryStatus status = new DeliveryStatus();
        status.setId(2L);
        status.setName("IN_PROGRESS");

        when(mockRepo.findById(2L)).thenReturn(Optional.of(status));

        DeliveryResponseDto result = DeliveryMapper.toDeliveryResponseDto(delivery, mockRepo);

        assertEquals(1L, result.getId());
        assertEquals(true, result.getDelivered());
        assertEquals(5L, result.getUserId());
        assertEquals("IN_PROGRESS", result.getStatus());

        verify(mockRepo).findById(2L);
    }

    @Test
    void testToDeliveryResponseDto_StatusNotFound() {
        DeliveryStatusRepository mockRepo = mock(DeliveryStatusRepository.class);
        when(mockRepo.findById(2L)).thenReturn(Optional.empty());

        DeliveryResponseDto result = DeliveryMapper.toDeliveryResponseDto(delivery, mockRepo);

        assertEquals("UNKNOWN", result.getStatus());
        verify(mockRepo).findById(2L);
    }

    @Test
    void testToDeliveryResponseDtoList() {
        DeliveryStatusRepository mockRepo = mock(DeliveryStatusRepository.class);
        DeliveryStatus status = new DeliveryStatus();
        status.setId(2L);
        status.setName("IN_PROGRESS");

        when(mockRepo.findById(2L)).thenReturn(Optional.of(status));

        List<DeliveryResponseDto> result = DeliveryMapper.toDeliveryResponseDto(List.of(delivery), mockRepo);

        assertEquals(1, result.size());
        assertEquals("IN_PROGRESS", result.get(0).getStatus());
    }
}
