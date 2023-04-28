package tech.abc.platform.common.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 应用上下文工具
 * 用于获取applicationContext.xml里配置的数据持久化用的bean。
 *
 * @author wqliu
 * @date 2023-03-08
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 得到指定名称的Spring Bean.
     *
     * @param beanName bean 名称
     * @return Spring Bean
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 得到指定类型的Spring Bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    /**
     * 获取应用程序配置信息.
     *
     * @param ctx the new application context
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }


}
