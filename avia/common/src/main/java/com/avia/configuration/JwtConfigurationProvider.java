package com.avia.configuration;

public interface JwtConfigurationProvider {
    String getSecret();

    Integer getExpiration();

    String getPasswordSalt();
}
