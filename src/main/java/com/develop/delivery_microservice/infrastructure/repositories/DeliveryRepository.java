package com.develop.delivery_microservice.infrastructure.repositories;

import com.develop.delivery_microservice.domain.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository  extends JpaRepository<Delivery, Long> {

}
