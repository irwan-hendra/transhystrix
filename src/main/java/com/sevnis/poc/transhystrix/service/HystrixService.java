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

  @HystrixCommand
  public void hystrixTransactionA(String mode) {
    transactionalService.transactionA(mode);
  }

  @HystrixCommand
  public void hystrixTransactionB(String mode) {
    if ("hystrixlocal".equalsIgnoreCase(mode)) {
      transactionC();
    } else {
      transactionalService.transactionB(mode);
    }
  }

  @HystrixCommand
  public void hystrixTransactionC() {
    transactionalService.transactionC();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void transactionC() {
    throw new RuntimeException("simulated runtime exception");
  }
}
