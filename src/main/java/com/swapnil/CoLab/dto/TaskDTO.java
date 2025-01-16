package com.swapnil.CoLab.dto;

import com.swapnil.CoLab.domain.PRIORITY;
import com.swapnil.CoLab.domain.STATUS;
import lombok.Data;

import java.time.Instant;

@Data
public class TaskDTO {

    private String name;
    private String description;
    private STATUS status;
    private PRIORITY priority;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deadline;
}
