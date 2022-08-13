package com.wsl.mybatis;


import com.wsl.mybatis.entity.Goods;
import com.wsl.utils.mybatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.*;

//JUNIT单元测试类
public class MyBatisTestor {
    /**
     * 初始化SqlSessionFactory
     * @throws IOException
     */
    @Test
    public void testSqlSessionFactory() throws IOException {
        //利用Reader加载classpath下的mybatis-config.xml核心配置文件
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        //初始化SqlSessionFactory对象,同时解析mybatis-config.xml文件
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        System.out.println("SessionFactory load success");
        SqlSession sqlSession = null;
        //创建SqlSession对象,SqlSession是JDBC的扩展类,用于与数据库交互
        try {
            sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }

    }
    @Test
    public void testMybatisUtils(){
        SqlSession sqlSession = null;
        try {
            sqlSession = mybatisUtils.openSession();
            Connection connection= sqlSession.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mybatisUtils.closeSession(sqlSession);
        }
    }
    @Test
    public void testSelectAll(){
        SqlSession sqlSession = null;
        try {
            sqlSession = mybatisUtils.openSession();
            List<Goods> list = sqlSession.selectList("goods.selectAll");
            for (Goods g : list){
                System.out.println(g.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mybatisUtils.closeSession(sqlSession);
        }
    }
    @Test
    public void testSelectById(){
        SqlSession sqlSession = null;
        try {
            sqlSession = mybatisUtils.openSession();
            Goods goods = sqlSession.selectOne("goods.selectById",1222);
            System.out.println(goods.getTitle());
//            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mybatisUtils.closeSession(sqlSession);
        }
    }
    @Test
    public void testSelectPriceRange(){
        SqlSession sqlSession = null;
        try {
            sqlSession = mybatisUtils.openSession();
            Map param = new HashMap();
            param.put("min", 100);
            param.put("max", 500);
            param.put("limt", 30);
            List<Goods> list = sqlSession.selectList("selectByPriceRange",param);
            for (Goods g : list){
                System.out.println(g.getTitle() + ":"+g.getCurrentPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mybatisUtils.closeSession(sqlSession);
        }
    }

}
