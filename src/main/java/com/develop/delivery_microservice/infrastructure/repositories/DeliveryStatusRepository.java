package com.develop.delivery_microservice.infrastructure.repositories;

import com.develop.delivery_microservice.domain.models.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {

}
