package com.zcy.cn;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

/**
 * SpringBoot还暂不直接支持Druid 因此不会有自动配置
 * yml中的配置需要自己读入
 */
@Configuration
@EnableJpaAuditing
public class HeathyServerBeanApplication {

    @Value("${spring.datasource.druid.stat-view-servlet.login-username}")
    private String druidLoginName;

    @Value("${spring.datasource.druid.stat-view-servlet.login-password}")
    private String druidLoginPwd;

    @Value("${spring.datasource.druid.stat-view-servlet.allow}")
    private String allow;

    @Value("${spring.datasource.druid.stat-view-servlet.deny}")
    private String deny;

//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSource druidDataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter("allow", allow);// IP白名单 (没有配置或者为空，则允许所有访问)
        registrationBean.addInitParameter("deny", deny);// IP黑名单 (存在共同时，deny优先于allow)
        registrationBean.addInitParameter("loginUsername", druidLoginName);
        registrationBean.addInitParameter("loginPassword", druidLoginPwd);
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean druidWebStatViewFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new WebStatFilter());
        registrationBean.addInitParameter("urlPatterns", "/*");
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return registrationBean;
    }

}
