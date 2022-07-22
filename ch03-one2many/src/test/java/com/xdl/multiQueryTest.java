package com.xdl;

import com.xdl.o2m.dao.CustomerDao;
import com.xdl.o2m.dao.LinkManDao;
import com.xdl.o2m.daomain.Customer;
import com.xdl.o2m.daomain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class multiQueryTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    /**
     * 对象导航查询
     */
    @Test
    @Transactional // 解决java代码中 no Session 问题
    public void testQuery1() {
        Customer customer = customerDao.findOne(1l);
        // 对象导航查询
        Set<LinkMan> linkMen = customer.getLinkMen();
        for (LinkMan linkMan: linkMen) {
            System.out.println(linkMan);
        }
    }
    /**
     * 对象导航查询:
     *  默认使用的是延迟加载的形式查询的
     *      调用get方法并不会立即发送查询，而是在使用关联对象的时候才会查询（延迟加载）。
     * 修改配置，将延迟加载修改为立即加载
     *      fetch 需要配置到多表映射关系的注解上, 不推荐使用
     * @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     */
    @Test
    @Transactional // 解决java代码中 no Session 问题
    public void testQuery2() {
        Customer customer = customerDao.findOne(1l);
        // 对象导航查询
        Set<LinkMan> linkMen = customer.getLinkMen();
        System.out.println(linkMen.size());
    }

    /**
     * 从联系人查询客户
     *      默认使用立即加载
     * 修改为延迟加载：需要在多的一方的注解上配置fetch
     * @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
     */
    @Test
    @Transactional // 解决java代码中 no Session 问题
    public void testQuery3() {
        LinkMan linkMan = linkManDao.findOne(1l);
        // 对象导航查询
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);
    }
}
