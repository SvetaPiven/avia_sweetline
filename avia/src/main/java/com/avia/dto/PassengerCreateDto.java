package com.avia.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PassengerCreateDto implements Serializable {

    private String fullName;
    private String personalId;

}