package com.sevnis.poc.transhystrix.service;

import com.sevnis.poc.transhystrix.repository.Person;
import com.sevnis.poc.transhystrix.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalHelperService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private TransactionalService transactionalService;


  @Transactional(propagation = Propagation.REQUIRED)
  public void transactionB() {
    personRepository.save(Person.builder().firstname("Loki").lastname("Odinson").build());

    try {
      transactionalService.transactionC();
    } catch (Exception e) {

    }
  }
}
