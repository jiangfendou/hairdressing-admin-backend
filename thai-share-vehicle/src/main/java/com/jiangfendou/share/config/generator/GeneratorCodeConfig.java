package com.jiangfendou.share.config.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 自动生成mybatisplus的相关代码
 * @author jiangmh
 */
public class GeneratorCodeConfig {

    private final static String PRODUCT_URL = "/thai-share-user";

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        //1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //生成路径(一般都是生成在此项目的src/main/java下面)
        gc.setOutputDir(projectPath + PRODUCT_URL + "/src/main/java");
        //设置作者
        gc.setAuthor("jiangfendou");
        gc.setOpen(false);
        //第二次生成会把第一次生成的覆盖掉
        gc.setFileOverride(true);
        //生成的service接口名字首字母是否为I，这样设置就没有
        gc.setServiceName("%sService");
        //生成resultMap
        gc.setBaseResultMap(true);
        mpg.setGlobalConfig(gc);

        //2、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/vehicle?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root123");
        mpg.setDataSource(dsc);

        // 3、包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.jiangfendou.share");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("mapper");
        packageConfig.setEntity("entity");
        mpg.setPackageInfo(packageConfig);

        // 4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//         strategy.setSuperEntityClass("com.jiangfendou.share.entity.BaseEntity");
        // 使用lombok
        strategy.setEntityLombokModel(true);
        // 逆向工程使用的表   如果要生成多个,这里可以传入String[]
        strategy.setInclude("make");
        mpg.setStrategy(strategy);

        //5、执行
        mpg.execute();
    }
}
