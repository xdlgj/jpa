package com.xdl;

import com.xdl.dao.CustomerDao;
import com.xdl.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据条件查询单个对象
     */
    @Test
    public void TestSpec() {
        // 匿名内部类
        /**
         * 自定义查询条件
         *  1、实现Specification接口（提供范型：查询的对象类型）
         *  2、实现toPredicate方法（构造查询条件）
         *  3、需要借助方法参数中的两个参数
         *      root:获取需要查询的对象属性
         *      CriteriaBuilder: 构造查询条件的，内部封装了很多的查询条件（模糊匹配，精准匹配）
         * 案例：根据客户名称查询
         *      查询条件：
         *          1、查询方法： criteriaBuilder对象
         *          2、比较的属性名称：root对象
         */
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 1、获取比较的属性
                Path<Object> name = root.get("name");
                // 2、构造查询条件：select * from customer where name = ?
                Predicate predicate = criteriaBuilder.equal(name, "李白"); //精准匹配，
                // 如果此处返回空，则后面会查询出所有的结果
                return predicate;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * 多条件查询
     */
    @Test
    public void testSpec1() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");
                Path<Object> industry = root.get("industry");
                Predicate predicate1 = criteriaBuilder.equal(name, "李白");
                Predicate predicate2 = criteriaBuilder.equal(industry, "诗人");
                // 多个查询条件组合到一起： and | or
                Predicate and = criteriaBuilder.and(predicate1, predicate2);
                return and;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }
    /**
     * 根据客户名称模糊查询
     * equal： 直接用path对象进行比较
     * gt、lt、le、like：得到path对象，根据path指定比较的参数类型在进行比较
     *      指定参数类型：path.as(类型的字节码对象)
     */
    @Test
    public void testSpec2() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");
                Predicate predicate = criteriaBuilder.like(name.as(String.class), "%李%");
                return predicate;
            }
        };
        List<Customer> customers = customerDao.findAll(spec);
        for (Customer customer: customers) {
            System.out.println(customer);
        }
    }
    /**
     * 排序查询
     */
    @Test
    public void testSpec3() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        // 创建排序对象
        // 第一个参数：排序的顺序，Sort.Direction.DESC(倒序) Sort.Direction.ASC（升序）
        // 第二个参数：排序的属性
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Customer> customers = customerDao.findAll(spec, sort);
        for (Customer customer: customers) {
            System.out.println(customer);
        }
    }
    /**
     * 分页查询
     *      Pageable: 分页参数
     * 返回：Page(springDataJpa封装好的pageBean对象，数据类标，总条数)
     */
    @Test
    public void testSpec4() {
        /**
         * PageRequest对象是Pageable接口的实现类
         * 参数：
         *  1、当前查询的页数（从0开始）
         *  2、每页查询的数量
         */
        Pageable pageable = new PageRequest(0, 2);
        Page<Customer> page = customerDao.findAll(null, pageable);
        System.out.println(page.getContent()); //得到数据集合的列表
        System.out.println(page.getTotalElements()); //得到总条数
        System.out.println(page.getTotalPages()); //得到总页数
    }
}
