package com.sevnis.poc.transhystrix.service;

import com.sevnis.poc.transhystrix.repository.Person;
import com.sevnis.poc.transhystrix.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalService {

  @Autowired
  private HystrixService hystrixService;

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private TransactionalHelperService transactionalHelperService;

  @Transactional(propagation = Propagation.REQUIRED)
  public void transactionA(String mode) {
    personRepository.save(Person.builder().firstname("Thor").lastname("Odinson").build());
    try {
      if ("local".equalsIgnoreCase(mode)) {
        transactionB(mode);
      } else if ("helper".equalsIgnoreCase(mode)) {
        transactionalHelperService.transactionB();
      } else if ("hystrix".equalsIgnoreCase(mode) || "hystrixlocal".equalsIgnoreCase(mode)) {
        hystrixService.hystrixTransactionB(mode);
      }
    } catch (Exception e) {

    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void transactionB(String mode) {
    personRepository.save(Person.builder().firstname("Loki").lastname("Odinson").build());
    try {
      if ("local".equalsIgnoreCase(mode)) {
        transactionC();
      } else if ("hystrix".equalsIgnoreCase(mode)) {
        hystrixService.hystrixTransactionC();
      }
    } catch (Exception e) {
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void transactionC() {
    throw new RuntimeException("simulated runtime exception");
  }
}
