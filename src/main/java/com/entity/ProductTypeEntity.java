package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Product_Type")
public class ProductTypeEntity {
  private Integer id;
  private String name;

  @Id
  @Column(name = "id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(nullable = false, length = 50, name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
 
}
