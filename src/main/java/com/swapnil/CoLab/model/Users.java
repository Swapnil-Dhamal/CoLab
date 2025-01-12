package com.swapnil.CoLab.model;

import com.swapnil.CoLab.domain.USER_ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email", unique = true),
        @Index(name = "idx_role", columnList = "role")
})
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String fullName;

    @Email
    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private USER_ROLE role=USER_ROLE.MEMBER;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private List<String> skills;

    @Embedded
    private Availability availability;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;


}

class Availability{
    private String status;
    private String reason;
    private String from;
    private String to;

}
