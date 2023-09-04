package com.enviro.envirobankingapp.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
@ConfigurationProperties(prefix = "account-constants")
public class AccountConstants {

    @Value("${account-constants.minimum}")
    public BigDecimal minimum;

    @Value("${account-constants.overdraft}")
    public BigDecimal overdraft;

}
