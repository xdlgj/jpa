package com.xdl.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 客户的实体类
 *  配置映射关系
 *      1、实体类与数据表的映射关系
 *      @Entity:声明实体类
 *      @Table:实体类和表的映射关系
 *          name：数据库中表名
 *      2、实体类中属性与表中字段的映射关系
 * @Data: 为了简化代码，自动生成get set toString 等多个方法
 */
@Entity
@Table(name = "customer")
@Data
public class Customer {
    /**
     * @Id:声明主键的名称
     * @GeneratedValue: 主键的生成策略
     *  strategy属性：
     *      GenerationType.IDENTITY：自增，要求底层数据库必须支持自动增长（mysql）
     *      GenerationType.SEQUENCE: 序列，要求底层数据库必须支持序列 （oracle）
     *      GenerationType.TABLE： jpa提供的一种机制，通过一张表(hibernate_sequences)的形式帮助我们完成主键自增
     *      GenerationType.AUTO: 由程序自动帮助我们选择上面三种的主键生成策略
     * @Column:配置属性和字段的映射关系
     *      name：数据库中字段名称
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long id; // 客户主键
    @Column(name = "cust_name")
    private String name; // 客户名称
    @Column(name = "cust_source")
    private String source; // 客户来源
    @Column(name = "cust_industry")
    private String industry; // 客户行业
    @Column(name = "cust_level")
    private String level; // 客户级别
    @Column(name = "cust_address")
    private String address; // 客户地址
    @Column(name = "cust_phone")
    private String phone; // 客户联系方法
}
