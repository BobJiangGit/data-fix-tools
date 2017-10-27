package com.ik.crm.fix.tools.conf;

import com.ik.crm.commons.starter.EnableIkAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@MapperScan("com.ik.crm.fix.tools.mapper")
@EnableIkAutoConfigure
public class BootConfig {

}