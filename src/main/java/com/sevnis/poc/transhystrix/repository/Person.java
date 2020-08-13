package com.sevnis.poc.transhystrix.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "PERSON")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstname;
  private String lastname;

}
