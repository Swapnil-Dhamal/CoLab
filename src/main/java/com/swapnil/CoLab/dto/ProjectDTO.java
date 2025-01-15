package com.swapnil.CoLab.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectDTO {

    @NotNull(message = "Project name is required")
    @Size(min = 3, max = 100, message = "Project name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Project description is required")
    @Size(min = 5, max = 500, message = "Project description must be between 5 and 500 characters")
    private String description;

    @NotNull(message = "Project status is required")
    private String status;

    @NotNull(message = "Start date is required")
    private Date startDate;

    private Date endDate;

}
