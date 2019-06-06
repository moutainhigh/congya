package com.chauncy.web.controller.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangwancheng
 * @create 2019-05-22 15:38
 * <p>
 * 代码自动生成 页面控制器
 */
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @PostMapping("/create")
    public void generator(@RequestParam(value = "model") String model,
                          @RequestParam(value = "tableName") String tableName,
                          @RequestParam(value = "dataBaseName") String dataBaseName
                          /*@RequestParam(value = "dbUserName") String dbUserName,
                          @RequestParam(value = "dbPassword") String dbPassword*/) {

        /**
         * 需要改的地方
         */
        //模块名称
//        String model = "product";
//        String tableName = "tb_user";
        /**
         * 暂时硬编码
         */
//        dataBaseName = "distribution";
        String dbUserName = "root" ;
        String dbPassword = "root" ;


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "/distribution-data" ;
        gc.setOutputDir(path + "/src/main/java");
        gc.setAuthor("huangwancheng");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setEntityName("%sPo");
        gc.setXmlName("%sMapper");
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://47.102.104.124:3306/" + dataBaseName + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(dbUserName);
        dsc.setPassword(dbPassword);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("data");
        pc.setParent("com.chauncy");
        pc.setEntity("domain.po." + model);
        pc.setMapper("mapper." + model);
        pc.setService("temp." + model + ".service");
        pc.setServiceImpl("temp." + model + ".service.impl");
        pc.setController("temp.controller." + model);

        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl" ;
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return path + "/src/main/resources/mapper/" + model
                        + "/" + tableInfo.getMapperName() + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        templateConfig.setServiceImpl("templates/mybatis/serviceImpl.java");
        templateConfig.setController("templates/mybatis/controller.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.chauncy.common.base.BaseEntity");
        strategy.setSuperServiceClass("com.chauncy.data.core.Service");
        strategy.setSuperServiceImplClass("com.chauncy.data.core.AbstractService");
        strategy.setSuperMapperClass("com.chauncy.data.mapper.IBaseMapper");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setInclude(tableName/*scanner("表名，多个英文逗号分割").split(",")*/);
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntitySerialVersionUID(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}

