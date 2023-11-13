package com.enviro.envirobankingapp.services;

import java.math.BigDecimal;

public interface PendingTransactions {

    BigDecimal getTransactionAmount();
    Integer getSenderAccountNum();
    Integer getReceiverAccountNum();

}
