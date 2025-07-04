package com.develop.delivery_microservice.controllers;

import com.develop.delivery_microservice.application.dtos.DeliveryRequestDto;
import com.develop.delivery_microservice.application.dtos.DeliveryResponseDto;
import com.develop.delivery_microservice.application.dtos.DeliveryStatusResponseDto;
import com.develop.delivery_microservice.domain.interfaces.DeliveryService;
import com.develop.delivery_microservice.domain.interfaces.DeliveryStatusService;
import com.develop.delivery_microservice.presentation.controllers.DeliveryController;
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
class DeliveryControllerTest {

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private DeliveryStatusService deliveryStatusService;

    @InjectMocks
    private DeliveryController deliveryController;

    private DeliveryResponseDto sampleDelivery;
    private DeliveryRequestDto requestDto;

    @BeforeEach
    void setup() {
        sampleDelivery = new DeliveryResponseDto();
        sampleDelivery.setId(1L);
        sampleDelivery.setStatus("CREATED");

        requestDto = new DeliveryRequestDto();
        requestDto.setStatusId(1L);
    }

    @Test
    void testListDeliveries() {
        when(deliveryService.getAllDeliveries()).thenReturn(List.of(sampleDelivery));

        List<DeliveryResponseDto> result = deliveryController.listDeliveries();

        assertEquals(1, result.size());
        verify(deliveryService).getAllDeliveries();
    }

    @Test
    void testGetDeliveryById() {
        when(deliveryService.getDeliveryById(1L)).thenReturn(sampleDelivery);

        DeliveryResponseDto result = deliveryController.getDeliveryById(1L);
        verify(deliveryService).getDeliveryById(1L);
    }

    @Test
    void testCreateDelivery() {
        when(deliveryService.createDelivery(requestDto)).thenReturn(sampleDelivery);

        DeliveryResponseDto result = deliveryController.createDelivery(requestDto);

        assertNotNull(result);
        assertEquals("CREATED", result.getStatus());
        verify(deliveryService).createDelivery(requestDto);
    }

    @Test
    void testUpdateDelivery() {
        when(deliveryService.updateDelivery(1L, requestDto)).thenReturn(sampleDelivery);

        DeliveryResponseDto result = deliveryController.updateDelivery(1L, requestDto);
        verify(deliveryService).updateDelivery(1L, requestDto);
    }

    @Test
    void testDeleteDelivery() {
        doNothing().when(deliveryService).deleteDelivery(1L);

        deliveryController.deleteDelivery(1L);

        verify(deliveryService).deleteDelivery(1L);
    }

    @Test
    void testGetAllDeliveriesByStatus() {
        DeliveryStatusResponseDto status = new DeliveryStatusResponseDto();
        status.setId(1L);
        status.setName("CREATED");

        when(deliveryStatusService.getAllStatuses()).thenReturn(List.of(status));

        List<DeliveryStatusResponseDto> result = deliveryController.getAllDeliveriesByStatus();

        assertEquals(1, result.size());
        assertEquals("CREATED", result.get(0).getName());
        verify(deliveryStatusService).getAllStatuses();
    }

    @Test
    void testGetDeliveryById_NotFound() {
        when(deliveryService.getDeliveryById(99L))
                .thenThrow(new RuntimeException("Delivery not found"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deliveryController.getDeliveryById(99L);
        });

        assertEquals("Delivery not found", ex.getMessage());
        verify(deliveryService).getDeliveryById(99L);
    }

    @Test
    void testCreateDelivery_InvalidRequest() {
        when(deliveryService.createDelivery(any()))
                .thenThrow(new RuntimeException("Invalid delivery data"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deliveryController.createDelivery(new DeliveryRequestDto());
        });

        assertEquals("Invalid delivery data", ex.getMessage());
        verify(deliveryService).createDelivery(any());
    }

    @Test
    void testUpdateDelivery_NotFound() {
        when(deliveryService.updateDelivery(eq(42L), any()))
                .thenThrow(new RuntimeException("Delivery not found"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deliveryController.updateDelivery(42L, requestDto);
        });

        assertEquals("Delivery not found", ex.getMessage());
        verify(deliveryService).updateDelivery(42L, requestDto);
    }

    @Test
    void testDeleteDelivery_NotFound() {
        doThrow(new RuntimeException("Delivery not found")).when(deliveryService).deleteDelivery(999L);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deliveryController.deleteDelivery(999L);
        });

        assertEquals("Delivery not found", ex.getMessage());
        verify(deliveryService).deleteDelivery(999L);
    }

    @Test
    void testGetAllDeliveriesByStatus_EmptyList() {
        when(deliveryStatusService.getAllStatuses()).thenReturn(List.of());

        List<DeliveryStatusResponseDto> result = deliveryController.getAllDeliveriesByStatus();

        assertTrue(result.isEmpty());
        verify(deliveryStatusService).getAllStatuses();
    }


}
