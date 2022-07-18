package com.xdl.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 1、实体类和表的映射关系
 * @Entity
 * @Table ,可以省略不写
 * 2、类中属性和表中字段的映射关系
 * @Id
 * @GeneratedValue
 * @Column, 类中数据如果没有使用该注解，则表中的字段名与类的属性名保持一致
 */
@Data
@Entity(name = "customer")
//@Table(name = "cst_customer") 当不写name属性时默认以类名的小写做为表名
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String industry;
    private String level;
    private String name;
    private String phone;
    private String source;
}
