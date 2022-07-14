package com.xdl;

import com.xdl.domain.Customer;
import com.xdl.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {
    /**
     * 测试jpa的保存
     * 案例：保存一个客户数据到数据库中
     * 操作步骤：
     * 1、加载配置文件创建工厂（实体类管理器工厂）对象
     * 2、通过实体管理器工厂中获取实体管理器
     * 3、获取事物对象、开启事物
     * 4、完成增删改查操作
     * 5、提交事务/回滚事物
     * 6、释放资源
     */
    @Test
    public void testSave() {
        // 1、加载配置文件创建工厂（实体类管理器工厂）对象, 在persistence-unit标签中定义的myJpa
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        // 2、通过实体管理器工厂中获取实体管理器
        EntityManager em = factory.createEntityManager();
        // 3、获取事物对象、开启事物
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // 4、保存一个客户数据到数据库中
        Customer customer = new Customer();
        customer.setName("李白");
        customer.setIndustry("文学");
        em.persist(customer);
        // 5、提交事务
        tx.commit();
        // 6、释放资源
        em.close();
        factory.close();
    }

    @Test
    public void testJpaUtil() {
        EntityManager em = JpaUtils.getEntityManager();
        // 3、获取事物对象、开启事物
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // 4、保存一个客户数据到数据库中
        Customer customer = new Customer();
        customer.setName("杜甫");
        customer.setIndustry("文学");
        em.persist(customer);
        // 5、提交事务
        tx.commit();
        // 6、释放资源
        em.close();
    }

    /**
     * 根据ID进行查询
     */
    @Test
    public void testFind() {
        EntityManager em = JpaUtils.getEntityManager();
        /*
          find: 根据id查询
               class: 查询数据的结果需要包装的实体类类型的字节码
               id：查询的主键
         */
        Customer customer = em.find(Customer.class, new Long(1));
        System.out.println(customer);
        em.close();
    }
    /**
     * 根据ID进行查询
     *  getReference方法
     *      1、获取的对象是一个动态代理对象
     *      2、调用该方法不会立即发送sql语句查询数据库
     *          当调用查询结果时，才会发送sql语句
     */
    @Test
    public void testReference() {
        EntityManager em = JpaUtils.getEntityManager();
        /*
          getReference: 根据id查询,懒加载，只有在使用数据的时候才会去查询
               class: 查询数据的结果需要包装的实体类类型的字节码
               id：查询的主键
         */
        Customer customer = em.getReference(Customer.class, new Long(3));
        System.out.println(customer);
        em.close();
    }
    /**
     * 删除数据
     */
    @Test
    public void testRemove() {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer = em.getReference(Customer.class, new Long(1));
        em.merge(customer);
        tx.commit();
        em.close();
    }
    /**
     * 更新数据
     */
    @Test
    public void testUpdate() {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer = em.getReference(Customer.class, new Long(3));
        customer.setIndustry("诗人");
        em.merge(customer);
        tx.commit();
        em.close();
    }
}
