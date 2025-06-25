package com.develop.delivery_microservice.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delivery")
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivered", nullable = false)
    private Boolean delivered;

    @Column(name = "status_id", nullable = false)
    private Long statusId;

    @Column(name="user_id", nullable = false)
    private Long userId;
}
