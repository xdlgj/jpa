package com.xdl;

import com.xdl.dao.CustomerDao;
import com.xdl.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class) // 声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") // 指定spring容器的配置信息
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindOne() {
        Customer customer = customerDao.findOne(2L);
        System.out.println(customer);
    }

    /**
     *  save：保存或更新，
     *      根据传递的对象是否存在id，如果没有id属性则保存，
     *      如果存在id则先根据id进行查询，如果存在则更新，如果不存在则创建
     */
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setName("李白");
        customer.setIndustry("诗人");
        customer.setLevel("VIP");
        customerDao.save(customer);
    }
    @Test
    public void testUpdate() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setIndustry("文学");
        customerDao.save(customer);
    }
    @Test
    public void testDelete() {
        customerDao.delete(1L);
    }

    /**
     * 查询所有
     */
    @Test
    public void testFindAll() {
        List<Customer> customers = customerDao.findAll();
        for (Customer customer: customers) {
            System.out.println(customer);
        }
    }
    /**
     * 测试统计查询，查询客户的总数量
     */
    @Test
    public void testCount() {
        Long count = customerDao.count(); // 查询数量
        System.out.println(count);
    }

    /**
     * 测试: 判断id为4的客户是否存在
     *      1、可以通过查询id为4的客户，如果为空代表不存在
     *      2、判断数据中id为4的客户数量，为0表示不存在(exists方法使用的方法)
     */
    @Test
    public void testExists() {
        boolean exists = customerDao.exists(4l);
        System.out.println(exists);
    }
}
