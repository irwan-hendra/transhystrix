package com.sevnis.poc.transhystrix;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.sevnis.poc.transhystrix.repository.Person;
import com.sevnis.poc.transhystrix.repository.PersonRepository;
import com.sevnis.poc.transhystrix.service.TransactionalService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionTest {

  @Autowired
  private TransactionalService transactionalService;

  @Autowired
  private PersonRepository personRepository;

  @AfterEach
  public void teardown() {
    personRepository.deleteAll();
  }

  @Test
  public void transactions_chainedLocally_rollBackFailed() {

    try {
      transactionalService.transactionA("local");
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Person> persons = personRepository.findAll();
    assertAll("Person size is 2",
        () -> assertThat(persons.size(), is(2)),
        () -> assertThat(persons, hasItems(
            allOf(hasProperty("firstname", is("Thor")), hasProperty("lastname", is("Odinson"))),
            allOf(hasProperty("firstname", is("Loki")), hasProperty("lastname", is("Odinson")))))
    );
  }

  @Test
  public void transactions_chainedWithHelper_rollBackSuccessful() {

    try {
      transactionalService.transactionA("helper");
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Person> persons = personRepository.findAll();
    assertAll("Person size is 0",
        () -> assertThat(persons.size(), is(0))
    );
  }

  @Test
  public void transactions_chainedWithHelperThenRemote_rollBackSuccessful() {

    try {
      transactionalService.transactionA("remote");
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Person> persons = personRepository.findAll();
    assertAll("Person size is 0",
        () -> assertThat(persons.size(), is(0))
    );
  }

  @Test
  public void transactions_chainedWithHystrix_rollBackSuccessful() {

    try {
      transactionalService.transactionA("hystrix");
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Person> persons = personRepository.findAll();
    assertAll("Person size is 0",
        () -> assertThat(persons.size(), is(0))
    );
  }

  @Test
  public void transactions_chainedWithHystrixThenLocal_rollBackFailed() {

    try {
      transactionalService.transactionA("hystrixlocal");
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Person> persons = personRepository.findAll();
    assertAll("Person size is 2",
        () -> assertThat(persons.size(), is(2)),
        () -> assertThat(persons, hasItems(
            allOf(hasProperty("firstname", is("Thor")), hasProperty("lastname", is("Odinson"))),
            allOf(hasProperty("firstname", is("Loki")), hasProperty("lastname", is("Odinson")))))
    );
  }

  @Test
  public void transactions_chainedWithHystrixThenHelper_rollBackSuccessful() {

    try {
      transactionalService.transactionA("hystrixhelper");
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Person> persons = personRepository.findAll();
    assertAll("Person size is 0",
        () -> assertThat(persons.size(), is(0))
    );
  }

  @Test
  public void transactions_chainedWithHystrixThenHelperThenRemote_rollBackSuccessful() {

    try {
      transactionalService.transactionA("hystrixremote");
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Person> persons = personRepository.findAll();
    assertAll("Person size is 0",
        () -> assertThat(persons.size(), is(0))
    );
  }

  @Test
  public void transactions_chainedWithHystrixShort_rollBackFailed() {

    try {
      transactionalService.transactionA("hystrixshort");
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Person> persons = personRepository.findAll();
    assertAll("Person size is 1",
        () -> assertThat(persons.size(), is(1))
    );
  }
}
