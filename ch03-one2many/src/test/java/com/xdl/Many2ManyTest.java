package com.xdl;

import com.xdl.m2m.dao.RoleDao;
import com.xdl.m2m.dao.UserDao;
import com.xdl.m2m.domain.Role;
import com.xdl.m2m.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Many2ManyTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    /**
     * 保存一个客户，保存一个联系人
     *  效果：客户和联系人作为独立的数据保存到数据中，联系人的外键为空
     *  原因：实体类中没有配置关系
     */
    @Test
    @Transactional
    @Rollback(value = false) // 设置不自动回滚
    public void TestAdd() {
        User user = new User();
        Role role = new Role();
        user.setUserName("小李");
        role.setRoleName("java程序员");
        //配置用户带角色关系，可以对中间表中的数据进行维护
        user.getRoles().add(role);
        role.getUsers().add(user);
        userDao.save(user);
        roleDao.save(role);
    }

    /**
     * 级联保存
     */
    @Test
    @Transactional
    @Rollback(value = false) // 设置不自动回滚
    public void TestCascadeAdd() {
        User user = new User();
        Role role = new Role();
        user.setUserName("小李");
        role.setRoleName("java程序员");
        user.getRoles().add(role);
        role.getUsers().add(user);
        userDao.save(user);
    }
    /**
     * 级联保存
     */
    @Test
    @Transactional
    @Rollback(value = false) // 设置不自动回滚
    public void TestCascadeDelete() {
        User user = userDao.findOne(1l);
        userDao.delete(user);
    }
}
