package com.xdl.o2m.daomain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 1、实体类和表的映射关系
 * @Entity
 * @Table ,可以省略不写
 * 2、类中属性和表中字段的映射关系
 * @Id
 * @GeneratedValue
 * @Column, 类中数据如果没有使用该注解，则表中的字段名与类的属性名保持一致
 */
@Getter
@Setter
@Entity(name = "customer")
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
    // 配置客户和联系人之间的关系（一对多关系）
    /**
     * 使用注解的形式配置多表关系
     *      1、声明关系
     *          OneToMany: 一对多
     *              targetEntity 对方对象的字节码
     *      2、配置外键（中间表）
     *          JoinColumn：配置外键
     *              name：外键字段名称
     *              referencedColumnName： 参照的主表的主键字段名称
     *  在客户实体类上（一的一方）添加了外键配置，所以对于客户而言也具备了维护外键的作用
     */
//    @OneToMany(targetEntity = LinkMan.class)
//    @JoinColumn(name = "cid", referencedColumnName = "id")
    /**
     * 放弃外键维护权
     *      mappedBy：对方配置关系的属性名称
     * cascade: 配置级联（也可以配置到设置多表的映射关系的注解上）
     *  CascadeType.ALL         :  所有
     *  CascadeType.MERGE       ： 更新
     *  CascadeType.PERSIST     ： 保存
     *  CascadeType.REMOVE      ： 删除
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<LinkMan> linkMen = new HashSet<>();
}
