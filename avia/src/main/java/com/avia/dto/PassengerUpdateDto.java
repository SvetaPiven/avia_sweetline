package com.avia.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PassengerUpdateDto implements Serializable {

    private String fullName;
    private String personalId;

}