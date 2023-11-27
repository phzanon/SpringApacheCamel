package com.apache.camel.example.camelexample.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CamelModelRequest {
    private String code;
    private String messageFilter;
    private String message;
}
