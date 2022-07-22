package com.xdl.m2m.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long roleId;
    @Column(name = "name")
    private String roleName;
    //@ManyToMany(targetEntity = User.class)
//    @JoinTable(name = "user_role",
//            // 当前对象在中间表的外键
//            joinColumns = {@JoinColumn(name = "rid", referencedColumnName = "id")},
//            // 对方对象在中间表的外键
//            inverseJoinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")}
//    )
    @ManyToMany(mappedBy = "roles") // 放弃维护权
    private Set<User> users = new HashSet<>();
}
