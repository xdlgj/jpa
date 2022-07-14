package com.xdl.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * 解决创建实体管理器工厂时浪费资源和耗时的问题
 *  通过静态代码块的形式，当程序第一次方法此工具类时，创建一个公共的实体管理器工厂对象
 * 第一次访问getEntityManager方法，经过静态代码块创建一个factory对象，再调用方法创建一个EntityManager对象
 * 后面访问getEntityManager方法，直接通过一个已经创建好的factory对象，创建EntityManager对象
 */
public class JpaUtils {
    private static  EntityManagerFactory factory;
    static {
        // 加载配置文件，创建entityManagerFactory
        factory = Persistence.createEntityManagerFactory("myJpa");
    }
    /**
     * 获取EntityManager对象
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
