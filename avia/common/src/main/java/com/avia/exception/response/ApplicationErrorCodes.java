package com.avia.exception.response;

public enum ApplicationErrorCodes {

    SQL_ERROR(10),
    USER_NOT_FOUND(40),
    FATAL_ERROR(1);

    private int codeId;

    ApplicationErrorCodes(int codeId) {
        this.codeId = codeId;
    }

    public int getCodeId() {
        return codeId;
    }
}
