package com.easyacg.core.mybatis;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis Plus 相关 bean 注入
 *
 * @author barhma
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * 修改 IdType: ASSIGN_ID 的主键生成策略
     *
     * @return IdentifierGenerator
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        return entity -> IdUtil.getSnowflakeNextId();
    }

    /**
     * 配置分页插件
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
