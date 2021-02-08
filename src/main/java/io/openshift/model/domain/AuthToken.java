package io.openshift.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AuthToken {
    private String accessToken;
    private String scope;
    private String tokenType;
    private Date validTo;
}