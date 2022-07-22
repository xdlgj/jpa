package com.xdl.o2m.daomain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity // 默认表名与类名相同
@Getter
@Setter
public class LinkMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 1)
    private String gender;
    private String phone;
    private String mobile;
    private String email;
    private String position; //职位
    private String memo; // 备注
    /**
     * 配置联系人到客户的多对一关系
     *  使用注解的形式配置多对一关系
     *      1、配置表关系
     *          ManyToOne: 多对一关系
     *              targetEntity：对方的实体类字节码
     *      2、配置外键（中间表）
     *          JoinColumn：
     *              name：外键名称
     *              referencedColumnName：参照的主表的主键字段名称
     * 配置外键的过程，配置到了多的一方，就会在多的一方维护外键
     */
    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cid", referencedColumnName = "id")
    private Customer customer;

}
