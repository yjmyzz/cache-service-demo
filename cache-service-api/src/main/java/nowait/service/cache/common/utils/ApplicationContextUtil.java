package nowait.service.cache.common.utils;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

/**
 * 出于演示目的,临时放在api项目里,省得provider与consumer项目中各复制一份
 */
public class ApplicationContextUtil {

    private static ConfigurableApplicationContext context = null;

    private static ApplicationContextUtil instance = null;

    public static ApplicationContextUtil getInstance() {
        if (instance == null) {
            synchronized (ApplicationContextUtil.class) {
                if (instance == null) {
                    instance = new ApplicationContextUtil();
                }
            }
        }
        return instance;
    }

    public ConfigurableApplicationContext getContext() {
        return context;
    }

    private ApplicationContextUtil() {

    }


    static {


        String configLocation = "resources/log4j2.xml";
        File configFile = new File(configLocation);
        if (!configFile.exists()) {
            System.err.println("log4j2 config file:" + configFile.getAbsolutePath() + " not exist");
            System.exit(0);
        }
        System.out.println("log4j2 config file:" + configFile.getAbsolutePath());

        try {

            System.setProperty("log4j.configurationFile", configFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("log4j2 initialize error:" + e.getLocalizedMessage());
            System.exit(0);
        }

        //约定:spring入口配置文件为resources/spring-context.xml
        configLocation = "resources/spring-context.xml";
        configFile = new File(configLocation);

        if (!configFile.exists()) {
            System.err.println("spring config file:" + configFile.getAbsolutePath() + " not exist");
            System.exit(0);
        }

        System.out.println("spring config file:" + configFile.getAbsolutePath());

        if (context == null) {
            context = new FileSystemXmlApplicationContext(configLocation);
            if (context.containsBean("dataSource")) {
                DataSource dataSource = context.getBean("dataSource", DataSource.class);
                if (dataSource != null) {
                    java.sql.Connection connection;
                    try {
                        connection = dataSource.getConnection();
                        connection.close();
                    } catch (SQLException e) {
                        System.err.println("spring initialize error:" + e.getLocalizedMessage());
                        System.exit(0);
                    }
                }
            }
        }

    }

}
