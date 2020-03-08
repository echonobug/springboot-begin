package com.jw.springbootbegin.dto;

import lombok.Data;

@Data
public class UploadResultDTO {
    private Integer success;
    private String message;
    private String url;
}
