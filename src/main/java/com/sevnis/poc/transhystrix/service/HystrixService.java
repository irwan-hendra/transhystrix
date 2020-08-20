package com.sevnis.poc.transhystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HystrixService {

  @Autowired
  private TransactionalService transactionalService;

  @Autowired
  private TransactionalHelperService transactionalHelperService;

  @HystrixCommand
  public void hystrixTransactionA(String mode) {
    transactionalService.transactionA(mode);
  }

  @HystrixCommand
  public void hystrixTransactionB(String mode) {
    try {
      if ("hystrix" .equalsIgnoreCase(mode)) {
        transactionalService.transactionB(mode);
      }

      if ("hystrixlocal" .equalsIgnoreCase(mode)) {
        transactionalService.transactionB("local");
      }
      if ("hystrixhelper" .equalsIgnoreCase(mode)) {
        transactionalHelperService.transactionB(mode);
      }
      if ("hystrixremote" .equalsIgnoreCase(mode)) {
        transactionalHelperService.transactionB("remote");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @HystrixCommand
  public void hystrixTransactionC() {
    try {
      transactionalService.transactionC();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @HystrixCommand
  public void hystrixShort() {
    try {
      transactionC();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void transactionC() {
    throw new RuntimeException("simulated runtime exception");
  }
}
