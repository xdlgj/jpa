package com.xdl;

import com.xdl.o2m.dao.CustomerDao;
import com.xdl.o2m.dao.LinkManDao;
import com.xdl.o2m.daomain.Customer;
import com.xdl.o2m.daomain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class One2ManyTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;
    /**
     * 保存一个客户，保存一个联系人
     *  效果：客户和联系人作为独立的数据保存到数据中，联系人的外键为空
     *  原因：实体类中没有配置关系
     */
    @Test
    @Transactional
    @Rollback(value = false) // 设置不自动回滚
    public void TestSave() {
        Customer customer = new Customer();
        LinkMan linkMan = new LinkMan();
        customer.setName("百度");
        linkMan.setName("小李");
        /**
         * 配置了客户到联系人的关系
         * 从客户的角度上：发送两条insert语句，发送一条更新语句更新数据库（外键）
         */
        customer.getLinkMen().add(linkMan);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }
    @Test
    @Transactional
    @Rollback(value = false) // 设置不自动回滚
    public void TestSave2() {
        Customer customer = new Customer();
        LinkMan linkMan = new LinkMan();
        customer.setName("百度");
        linkMan.setName("小李");
        /**
         * 配置联系人到客户的关系（多对一）
         * 只发送了两条insert语句
         * 由于配置了联系人到客户的映射关系（多对一）
         */
        linkMan.setCustomer(customer);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 会有一条多余的update语句
     *      由于一的一方可以维护外键，会发送update语句
     *      解决此问题：只需要在一的一方放弃维护权即可
     */
    @Test
    @Transactional
    @Rollback(value = false) // 设置不自动回滚
    public void TestSave3() {
        Customer customer = new Customer();
        LinkMan linkMan = new LinkMan();
        customer.setName("百度");
        linkMan.setName("小李");
        linkMan.setCustomer(customer); // 由于配置了多的一方到一的一方的关联关系（当保存的时候，就已经对外键赋值）
        customer.getLinkMen().add(linkMan); // 由于配置了一的一方到多的一方的关联关系（发送一条update语句）
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 级联添加：保存客户的同时保存客户所有的联系人
     * 需要在操作主体的实体类对象属性上配置cascade属性
     * @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
     */
    @Test
    @Transactional
    @Rollback(value = false) // 设置不自动回滚
    public void TestCascadeAdd() {
        Customer customer = new Customer();
        LinkMan linkMan = new LinkMan();
        customer.setName("百度1");
        linkMan.setName("小李1");
        linkMan.setCustomer(customer);
        customer.getLinkMen().add(linkMan);
        customerDao.save(customer);
    }

    /**
     * 级联删除
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void TestCascadeRemove() {
        Customer customer = customerDao.findOne(1l);
        customerDao.delete(customer);
    }
}
