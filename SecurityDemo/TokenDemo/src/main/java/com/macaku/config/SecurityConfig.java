package com.macaku.config;

import com.macaku.component.JwtAuthenticationTokenFilter;
import com.macaku.component.AuthSuccessFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 20:29
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启权限控制，不开启这个，注解的权限控制不能生效
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private AuthSuccessFilter authSuccessFilter;


    //BCryptPasswordEncoder的Bean对象加入到容器里
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /**
     *      不必调用父类的configure方法，UsernamePasswordAuthenticationFilter就不认证了
     *      “哪些可以匿名访问不用认证（认证了才有权限可言，不代表不用验证权限），哪些需要进行认证”，只不过是一些“认证/权限等等的控制”罢了，没有实际的认证机制
     *      但是这里没有任何UsernamePasswordAuthenticationFilter相关的调用
     *      既然无法进行认证（SecurityContextHolder就不会有对应的那个Authentication对象），最终需要认证的接口就访问不了
     *      这样我们就需要自己写个拦截器，去规定怎么样才算认证成功
     *      这样，必须提前在/user/login （接口内用了认证方法，这一步代表体检做认证），将userid存到redis，生成token返回
     *      请求必须携带token，由 jwtAuthenticationTokenFilter 进行认证是否有token，redis是否有记录
     *      有则代表提前做了认证，SecurityContextHolder 设置那个Authentication对象，放行即可，就不需要进行UsernamePasswordAuthenticationFilter的那个默认login页面去认证了
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); 默认是cookie session
    //内部指定表单，用户输入表单就去验证，也就是UsernamePasswordAuthenticationFilter的认证方式
    // 既然注释掉了，则代表这个过滤器(可能不止这一个)失效了~（不被调用 -> 过滤器不工作），并且还注释掉“控制”相关的配置


        http
    //关闭 csrf
            .csrf().disable()
    //不通过 Session 获取 SecurityContext
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/user/login") /*匿名访问*/.anonymous()
//            .antMatchers("/test/hi").hasAuthority("haha")//【配置控制权限，和注解配置权限，是“并”的关系】
//            .antMatchers("/test/hi").hasAnyAuthority("haha")
//            .antMatchers("/test/hi").hasRole("haha")
//            .antMatchers("/test/hi").hasAnyRole("haha")
            .antMatchers("/test/hello")/*没有认证或者认证都能访问（无权也不能访问）*/.permitAll()
            .anyRequest()/*除上面外的所有请求全部需要认证(认证才能访问)*/.authenticated();
        // 添加过滤器
        http.addFilterAfter(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authSuccessFilter, JwtAuthenticationTokenFilter.class);
        // 配置异常处理器（默认的话貌似是抛对应的异常，一大串的东西、或者控制台无表示，用对应的响应状态码表示异常）
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        // 允许跨域
        http.cors();
    }
}
