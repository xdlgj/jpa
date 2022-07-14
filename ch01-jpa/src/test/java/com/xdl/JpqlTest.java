package com.xdl;
/**
 * 测试jpql查询
 */

import com.xdl.domain.Customer;
import com.xdl.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class JpqlTest {
    /**
     * 查询全部
     * sql： select * from customer
     * jpql: from Customer
     */
    @Test
    public void testFindAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String jpql = "from Customer";
        Query query = em.createQuery(jpql);
        List list = query.getResultList();
        for(Object obj: list) {
            System.out.println(obj);
        }
    }
    /**
     * 排序查询
     * sql： select * from customer order by cust_id desc
     * jpql: from Customer order by Id desc
     */
    @Test
    public void testOrder() {
        EntityManager em = JpaUtils.getEntityManager();
        String jpql = "from Customer order by id desc";
        Query query = em.createQuery(jpql);
        List<Customer> list = query.getResultList();
        for(Customer customer: list) {
            System.out.println(customer);
        }
    }
    /**
     * 统计
     *  sql: select count(*) from customer
     *  jpql: select count(*) from Customer
     */
    @Test
    public void testCount() {
        EntityManager em = JpaUtils.getEntityManager();
        String jpql = "select count(*) from Customer";
        Query query = em.createQuery(jpql);
        Object res = query.getSingleResult();
        System.out.println(res);
    }
    /**
     * 分页查询
     *  sql: select * from customer limit ?,?;
     *  jpql:  from Customer
     */
    @Test
    public void testPaged() {
        EntityManager em = JpaUtils.getEntityManager();
        String jpql = "from Customer";
        Query query = em.createQuery(jpql);
        query.setFirstResult(1);
        query.setMaxResults(2);
        List<Customer> customers = query.getResultList();
        for (Customer customer: customers){
            System.out.println(customer);
        }
    }
    /**
     * 条件查询
     *  sql: select * from customer where cust_name like ?;
     *  jpql:  from Customer where name like ?
     */
    @Test
    public void testWhere() {
        EntityManager em = JpaUtils.getEntityManager();
        String jpql = "from Customer where name like ?";
        Query query = em.createQuery(jpql);
        query.setParameter(1, "%李%");
        List<Customer> customers = query.getResultList();
        for (Customer customer: customers){
            System.out.println(customer);
        }
    }
}
