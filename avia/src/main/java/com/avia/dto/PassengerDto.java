package com.avia.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
public class PassengerDto implements Serializable {
    private final Long idPass;
    private final String fullName;
    private final String personalId;
    private final Timestamp changed;
    private final Boolean idDeleted;
}