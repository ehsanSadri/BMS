package com.sadri.bms.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableFilter {

    @NotNull
    @Min(1)
    private int page;
    @NotNull
    @Min(1)
    private int size;
}