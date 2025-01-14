package com.swapnil.CoLab.model;


import com.swapnil.CoLab.domain.PRIORITY;
import com.swapnil.CoLab.domain.STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id", nullable = false)
    private Long projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private Users assignee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATUS status=STATUS.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PRIORITY priority=PRIORITY.NORMAL;


    private Instant deadline;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

}
