package com.swapnil.CoLab.model;


import com.swapnil.CoLab.domain.STATUS;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;


@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_name", columnList = "name"),
        @Index(name = "idx_status", columnList = "status")
})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATUS status=STATUS.ACTIVE;

//    @ManyToOne
//    @JoinColumn(name = "manager_id", nullable = false)
//    private Users manager;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}
