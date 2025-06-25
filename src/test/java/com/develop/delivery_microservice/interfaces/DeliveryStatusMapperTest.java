package com.develop.delivery_microservice.interfaces;

import com.develop.delivery_microservice.application.dtos.DeliveryStatusResponseDto;
import com.develop.delivery_microservice.domain.interfaces.DeliveryStatusMapper;
import com.develop.delivery_microservice.domain.models.DeliveryStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryStatusMapperTest {

    @Test
    void testToDeliveryStatusResponseDto_SingleStatus() {
        DeliveryStatus status = new DeliveryStatus();
        status.setId(1L);
        status.setName("CREATED");

        DeliveryStatusResponseDto dto = DeliveryStatusMapper.toDeliveryStatusResponseDto(status);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("CREATED", dto.getName());
    }

    @Test
    void testToDeliveryStatusResponseDto_ListOfStatuses() {
        DeliveryStatus s1 = new DeliveryStatus();
        s1.setId(1L);
        s1.setName("CREATED");

        DeliveryStatus s2 = new DeliveryStatus();
        s2.setId(2L);
        s2.setName("DELIVERED");

        List<DeliveryStatusResponseDto> result = DeliveryStatusMapper.toDeliveryStatusResponseDto(List.of(s1, s2));

        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals("CREATED", result.get(0).getName());

        assertEquals(2L, result.get(1).getId());
        assertEquals("DELIVERED", result.get(1).getName());
    }

    @Test
    void testToDeliveryStatusResponseDto_NullInput() {
        assertThrows(NullPointerException.class, () -> {
            DeliveryStatusMapper.toDeliveryStatusResponseDto((DeliveryStatus) null);
        });
    }

    @Test
    void testToDeliveryStatusResponseDto_EmptyList() {
        List<DeliveryStatusResponseDto> result = DeliveryStatusMapper.toDeliveryStatusResponseDto(List.of());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToDeliveryStatusResponseDto_StatusWithNullFields() {
        DeliveryStatus status = new DeliveryStatus(); // id y name null

        DeliveryStatusResponseDto dto = DeliveryStatusMapper.toDeliveryStatusResponseDto(status);

        assertNull(dto.getId());
        assertNull(dto.getName());
    }

}
