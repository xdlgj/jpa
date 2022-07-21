package com.xdl.dao;

import com.xdl.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 符合SpringDataJpa的dao层接口规范:
 * JpaRepository<操作的实体类类型, 实体类中主键属性的类型>
 * 封装了基本的CRUD操作
 * JpaSpecificationExecutor<操作的实体类类型>
 * 封装了复杂查询（分页...）
 */
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    /**
     * 案例：根据客户名称查询客户
     * 使用jpql的形式查询
     * jpql： from Customer where name = ?
     * 配置jpql语句，使用@Query注解
     */
    @Query(value = "from com.xdl.domain.Customer where name = ?1")
    Customer findJpql(String name);
    /**
     * 案例：根据客户id 和名称进行查询
     * jpql: from Customer where id = ?1 and name = ?2
     * 对于多个占位符参数
     *  赋值的时候，默认情况下，占位符的位置需要和方法参数中的位置保持一致
     * 可以指定占位符参数的位置
     *  ?索引方式，指定次占位符的取值来源
     */
    @Query(value = "from com.xdl.domain.Customer c where c.id = ?1 and c.name = ?2")
    Customer findNameAndId(Long id, String name);
    /**
     * 案例： 根据ID更新客户名称
     * sql： update customer set name = ? where id = ?
     * @Query：代表的是进行查询，声明此方法是用来进行查询操作
     * @Modifying: 声明执行的是一个更新操作
     */
    @Query(value = "update customer set name = ?2 where id = ?1")
    @Modifying
    void updateCustomer(long id, String name);

    /**
     *使用sql的形式进行查询
     *查询全部客户
     * sql: select * from customer
     */
    @Query(value = "select * from customer ", nativeQuery = true)
    List<Object[]> findSql();
    /**
     * 根据用户名模糊查询
     */
    @Query(value = "select * from customer where name like ?", nativeQuery = true)
    List<Object[]> findLike(String name);

    /**
     * 方法名的约定
     *  findBy：查询， 对象中的属性名（首字母大写），查询条件
     *  findByName: 根据客户名称进行查询
     *  在springDataJpa的运行阶段，会根据方法名称进行解析findBy
     */
    Customer findByName(String name);
    List<Customer> findByNameLike(String name);

    /**
     * 使用客户名称模糊匹配和客户所在航行精准匹配
     */
    Customer findByNameLikeAndIndustry(String name, String industry);
}
