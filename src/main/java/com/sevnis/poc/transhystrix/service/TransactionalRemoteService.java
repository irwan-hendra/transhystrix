package com.sevnis.poc.transhystrix.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalRemoteService {

  @Transactional(propagation = Propagation.REQUIRED)
  public void transactionC() {
    throw new RuntimeException("simulated runtime exception");
  }
}
