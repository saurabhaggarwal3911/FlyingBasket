package com.commondb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "appconfiguration")
public class ApplicationConfiguration {
  private int id;
  private String name;
  private String value;

  @Id
  @Column(name = "id")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Length(max = 50)
  @Column(name = "name", nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Length(max = 100)
  @Column(name = "value", nullable = false)
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
