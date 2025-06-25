package com.develop.delivery_microservice.services;

import com.develop.delivery_microservice.application.dtos.DeliveryRequestDto;
import com.develop.delivery_microservice.application.dtos.DeliveryResponseDto;
import com.develop.delivery_microservice.application.use_cases.DeliveryServiceImpl;
import com.develop.delivery_microservice.domain.interfaces.DeliveryMapper;
import com.develop.delivery_microservice.domain.models.Delivery;
import com.develop.delivery_microservice.infrastructure.repositories.DeliveryRepository;
import com.develop.delivery_microservice.infrastructure.repositories.DeliveryStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryStatusRepository deliveryStatusRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    private Delivery delivery;
    private DeliveryRequestDto requestDto;
    private DeliveryResponseDto responseDto;

    @BeforeEach
    void setUp() {
        delivery = new Delivery();
        delivery.setId(1L);
        delivery.setDelivered(false);
        delivery.setStatusId(1L);

        requestDto = new DeliveryRequestDto();
        requestDto.setDelivered(true);
        requestDto.setStatusId(2L);

        responseDto = new DeliveryResponseDto();
        responseDto.setId(1L);
        responseDto.setDelivered(false);
        responseDto.setStatus("CREATED");
    }

    @Test
    void getAllDeliveries_ReturnsList() {
        when(deliveryRepository.findAll()).thenReturn(List.of(delivery));

        try (MockedStatic<DeliveryMapper> mocked = mockStatic(DeliveryMapper.class)) {
            mocked.when(() -> DeliveryMapper.toDeliveryResponseDto(List.of(delivery), deliveryStatusRepository))
                    .thenReturn(List.of(responseDto));

            List<DeliveryResponseDto> result = deliveryService.getAllDeliveries();

            assertEquals(1, result.size());
            assertEquals(1L, result.get(0).getId());
            verify(deliveryRepository).findAll();
        }
    }

    @Test
    void getDeliveryById_Existing_ReturnsDto() {
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));

        try (MockedStatic<DeliveryMapper> mocked = mockStatic(DeliveryMapper.class)) {
            mocked.when(() -> DeliveryMapper.toDeliveryResponseDto(delivery, deliveryStatusRepository))
                    .thenReturn(responseDto);

            DeliveryResponseDto result = deliveryService.getDeliveryById(1L);

            assertEquals(1L, result.getId());
            verify(deliveryRepository).findById(1L);
        }
    }

    @Test
    void getDeliveryById_NotFound_ThrowsException() {
        when(deliveryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deliveryService.getDeliveryById(1L);
        });

        assertEquals("Delivery not found with id 1", ex.getMessage());
    }

    @Test
    void createDelivery_ReturnsDto() {
        Delivery deliveryToSave = new Delivery();
        deliveryToSave.setDelivered(requestDto.getDelivered());
        deliveryToSave.setStatusId(requestDto.getStatusId());

        when(deliveryRepository.save(any())).thenReturn(delivery);

        try (MockedStatic<DeliveryMapper> mocked = mockStatic(DeliveryMapper.class)) {
            mocked.when(() -> DeliveryMapper.toDelivery(requestDto)).thenReturn(deliveryToSave);
            mocked.when(() -> DeliveryMapper.toDeliveryResponseDto(delivery, deliveryStatusRepository))
                    .thenReturn(responseDto);

            DeliveryResponseDto result = deliveryService.createDelivery(requestDto);

            assertNotNull(result);
            assertEquals(1L, result.getId());
            verify(deliveryRepository).save(any());
        }
    }

    @Test
    void updateDelivery_Existing_ReturnsUpdatedDto() {
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));
        when(deliveryRepository.save(any())).thenReturn(delivery);

        try (MockedStatic<DeliveryMapper> mocked = mockStatic(DeliveryMapper.class)) {
            mocked.when(() -> DeliveryMapper.toDeliveryResponseDto(delivery, deliveryStatusRepository))
                    .thenReturn(responseDto);

            DeliveryResponseDto result = deliveryService.updateDelivery(1L, requestDto);

            assertEquals(1L, result.getId());
            assertTrue(delivery.getDelivered());
            assertEquals(2L, delivery.getStatusId());
            verify(deliveryRepository).save(delivery);
        }
    }

    @Test
    void updateDelivery_NotFound_ThrowsException() {
        when(deliveryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deliveryService.updateDelivery(1L, requestDto);
        });

        assertEquals("Delivery not found with id 1", ex.getMessage());
    }

    @Test
    void deleteDelivery_Existing() {
        when(deliveryRepository.existsById(1L)).thenReturn(true);

        deliveryService.deleteDelivery(1L);

        verify(deliveryRepository).deleteById(1L);
    }

    @Test
    void deleteDelivery_NotFound_ThrowsException() {
        when(deliveryRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deliveryService.deleteDelivery(1L);
        });

        assertEquals("Delivery not found with id 1", ex.getMessage());
    }

    @Test
    void getAllDeliveries_EmptyList_ReturnsEmpty() {
        when(deliveryRepository.findAll()).thenReturn(List.of());

        try (MockedStatic<DeliveryMapper> mocked = mockStatic(DeliveryMapper.class)) {
            mocked.when(() -> DeliveryMapper.toDeliveryResponseDto(List.of(), deliveryStatusRepository))
                    .thenReturn(List.of());

            List<DeliveryResponseDto> result = deliveryService.getAllDeliveries();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(deliveryRepository).findAll();
        }
    }


    @Test
    void createDelivery_NullRequest_ThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            deliveryService.createDelivery(null);
        });
    }

    @Test
    void updateDelivery_StatusIdNull_StillUpdates() {
        requestDto.setStatusId(null);
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));
        when(deliveryRepository.save(any())).thenReturn(delivery);

        try (MockedStatic<DeliveryMapper> mocked = mockStatic(DeliveryMapper.class)) {
            mocked.when(() -> DeliveryMapper.toDeliveryResponseDto(delivery, deliveryStatusRepository))
                    .thenReturn(responseDto);

            DeliveryResponseDto result = deliveryService.updateDelivery(1L, requestDto);

            assertNotNull(result);
            verify(deliveryRepository).save(delivery);
            assertNull(delivery.getStatusId()); // importante
        }
    }

    @Test
    void createDelivery_SetsIdToNull_BeforeSave() {
        Delivery mappedDelivery = new Delivery();
        mappedDelivery.setId(100L); // Simula que viene con ID

        when(deliveryRepository.save(any())).thenAnswer(invocation -> {
            Delivery d = invocation.getArgument(0);
            assertNull(d.getId()); // Verifica que se reinicializó
            return delivery;
        });

        try (MockedStatic<DeliveryMapper> mocked = mockStatic(DeliveryMapper.class)) {
            mocked.when(() -> DeliveryMapper.toDelivery(requestDto)).thenReturn(mappedDelivery);
            mocked.when(() -> DeliveryMapper.toDeliveryResponseDto(delivery, deliveryStatusRepository)).thenReturn(responseDto);

            DeliveryResponseDto result = deliveryService.createDelivery(requestDto);

            assertNotNull(result);
        }
    }

    @Test
    void deleteDelivery_VerifiesOnlyIfExistsOnce() {
        when(deliveryRepository.existsById(999L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            deliveryService.deleteDelivery(999L);
        });

        verify(deliveryRepository, never()).deleteById(any());
    }

}
