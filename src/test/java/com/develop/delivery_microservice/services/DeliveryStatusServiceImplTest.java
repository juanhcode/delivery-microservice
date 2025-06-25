package com.develop.delivery_microservice.services;

import com.develop.delivery_microservice.application.dtos.DeliveryStatusResponseDto;
import com.develop.delivery_microservice.application.use_cases.DeliveryStatusServiceImpl;
import com.develop.delivery_microservice.domain.models.DeliveryStatus;
import com.develop.delivery_microservice.infrastructure.repositories.DeliveryStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryStatusServiceImplTest {

    @Mock
    private DeliveryStatusRepository deliveryStatusRepository;

    @InjectMocks
    private DeliveryStatusServiceImpl deliveryStatusService;

    private DeliveryStatus status1;

    @BeforeEach
    void setUp() {
        status1 = new DeliveryStatus();
        status1.setId(1L);
        status1.setName("CREATED");
    }

    @Test
    void getAllStatuses_ReturnsList() {
        when(deliveryStatusRepository.findAll()).thenReturn(List.of(status1));

        List<DeliveryStatusResponseDto> result = deliveryStatusService.getAllStatuses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CREATED", result.get(0).getName());

        verify(deliveryStatusRepository, times(1)).findAll();
    }

    @Test
    void getAllStatuses_EmptyList_ThrowsException() {
        when(deliveryStatusRepository.findAll()).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deliveryStatusService.getAllStatuses();
        });

        assertEquals("No delivery statuses found", exception.getMessage());

        verify(deliveryStatusRepository, times(1)).findAll();
    }
}
