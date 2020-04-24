package com.wenruo;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class Springboot04DataApplicationTests {

    @Autowired
    DataSource dataSource;


    @Test
    void contextLoads() throws SQLException {
        /* 输出检测数据源 查看默认数据源： com.zaxxer.hikari.HikariDataSource*/
        System.out.println(dataSource.getClass());

        /* 获得数据库链接 */
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        /* xxxxxTemplate : SpringBoot已经配置好的模板bean，拿来即用 CRUD */

        /* 关闭连接 */
        connection.close();
    }

}
