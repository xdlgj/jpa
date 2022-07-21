package com.xdl;

import com.xdl.dao.CustomerDao;
import com.xdl.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class) // 声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") // 指定spring容器的配置信息
public class JpqlTest {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindJpql() {
        Customer customer = customerDao.findJpql("李白");
        System.out.println(customer);
    }
    @Test
    public void testFindNameAndId() {
        Customer customer = customerDao.findNameAndId(1l, "李白");
        System.out.println(customer);
    }

    /**
     * jpql更新操作
     * springDataJpa中使用jpql完成更新或删除操作
     *      需要手动添加事务的支持
     *      默认在执行结束之后，回滚事务
     * @Rollback : 设置是否回滚
     */
    @Test
    @Transactional // 添加事务的支持
    @Rollback(value = false)
    public void testUpdateCustomer() {
        customerDao.updateCustomer(1l, "杜甫");
    }
    @Test
    public void testSql() {
        List<Object[]> list = customerDao.findSql();
        for(Object[] obj: list){
            System.out.println(Arrays.toString(obj));
        }
    }
    @Test
    public void testFindLike() {
        List<Object[]> list = customerDao.findLike("%杜%");
        for(Object[] obj: list){
            System.out.println(Arrays.toString(obj));
        }
    }
    @Test
    public void testFindBy() {
        Customer customer = customerDao.findByName("杜甫");
        System.out.println(customer);
    }
    @Test
    public void testFindByLike() {
        List<Customer> customers = customerDao.findByNameLike("%杜%");
        for (Customer customer: customers) {
            System.out.println(customer);
        }
    }
    @Test
    public void testFindByLikeMulti() {
        Customer customer = customerDao.findByNameLikeAndIndustry("%杜%", "诗人");
        System.out.println(customer);
    }
}
