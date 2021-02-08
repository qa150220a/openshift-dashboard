package io.openshift.model.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
public class AuthTokenDTO {
    private final long createdMs;

    private String accessToken;
    private String scope;
    private String tokenType;
    private String expiresIn;

    public AuthTokenDTO() {
        createdMs = System.currentTimeMillis();
    }

    public Date getValidTo() {
        if (null == expiresIn || expiresIn.trim().length() == 0) return null;
        return new Date(createdMs + Long.parseLong(expiresIn)*1000L);
    }
}
