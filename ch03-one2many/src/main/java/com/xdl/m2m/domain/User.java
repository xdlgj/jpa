package com.xdl.m2m.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;
    @Column(name = "name")
    private String userName;
    @Column(name = "age")
    private Integer userAge;
    /**
     * 配置用户到角色的多对多关系
     * 1、声明表关系的配置
     * ManyToMany(targetEntity = Role.class)
     * targetEntity：代表对方的实体类字节码
     * 2、配置中间表（包含两个外键）
     */
    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", // 中间表的名称
            // 当前对象在中间表的外键
            joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            // 对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "id")}
    )
    private Set<Role> roles = new HashSet<>();
}
