package com.xdl;

import com.xdl.dao.CustomerDao;
import com.xdl.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class) // 声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") // 指定spring容器的配置信息
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindOne() {
        Customer customer = customerDao.findOne(1L);
        System.out.println(customer);
    }

    /**
     *  save：保存或更新，
     *      根据传递的对象是否存在id，如果没有id属性则保存，否则为更新
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
}
