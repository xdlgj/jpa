package com.xdl.m2m.dao;

import com.xdl.m2m.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}
