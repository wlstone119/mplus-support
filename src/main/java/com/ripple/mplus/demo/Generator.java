package com.ripple.mplus.demo;

import com.ripple.mplus.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.ripple.mplus.generator.AutoGenerator;
import com.ripple.mplus.generator.InjectionConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanglei
 */
public class Generator {

    public static void doGenerator(String author, String dir, String mapperDir, String projectUrl, String[] jdbc,
                                   String tablePrefix, String table_name, boolean needRepository) {
        // 自定义需要填充的字段
        ArrayList<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        GlobalConfig globalConfig = new GlobalConfig().setOutputDir(projectUrl)// 输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                // .setKotlin(true) 是否生成 kotlin 代码
                .setAuthor(author)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sMapper").setXmlName("%sMapper").setServiceName("%sRepository")
                .setServiceImplName("%sRepositoryImpl");

        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                        // 全局配置
                        globalConfig).setDataSource(
                        // 数据源配置
                        new DataSourceConfig().setDbType(DbType.MYSQL)// 数据库类型
                                .setTypeConvert(new MySqlTypeConvert() {
                                    // 自定义数据库表字段类型转换【可选】
                                    @Override
                                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                                        System.out.println("转换类型：" + fieldType);
                                        return super.processTypeConvert(globalConfig, fieldType);
                                    }
                                }).setDriverName("com.mysql.jdbc.Driver").setUrl(jdbc[0]).setUsername(jdbc[1])
                                .setPassword(jdbc[2]))
                .setStrategy(
                        // 策略配置
                        new StrategyConfig()
                                // .setCapitalMode(true)// 全局大写命名
                                // .setDbColumnUnderline(true)//全局下划线命名
                                .setTablePrefix(new String[] { tablePrefix })// 此处可以修改为您的表前缀
                                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                                .setInclude(new String[] { table_name }) // 需要生成的表
                                // .setExclude(new String[]{"test"}) // 排除生成的表
                                // 自定义实体父类
                                // .setSuperEntityClass("com.xdong.ripple.CommonEntity")
                                // 自定义实体，公共字段
                                // .setSuperEntityColumns(new String[]{"test_id"})
                                // .setTableFillList(tableFillList)
                                // 自定义 mapper 父类
                                .setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper")
                                // 自定义 service 父类
                                .setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService")
                                // 自定义 service 实现类父类
                                .setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl")
                                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                                .setEntityLombokModel(true)
                        // 【实体】是否生成字段常量（默认 false）
                        // public static final String ID = "test_id";
                        // .setEntityColumnConstant(true)
                        // 【实体】是否为构建者模型（默认 false）
                        // public User setName(String name) {this.name = name; return this;}
                        // .setEntityBuilderModel(true)
                        // Boolean类型字段是否移除is前缀处理
                        // .setEntityBooleanColumnRemoveIsPrefix(true)
                        // .setRestControllerStyle(true)
                        // .setControllerMappingHyphenStyle(true)
                        // .entityTableFieldAnnotationEnable(true)
                        // .setLogicDeleteFieldName("is_delete")
                ).setPackageInfoList(new ArrayList<PackageConfig>() {
                    {// 包配置
                        add(new PackageConfig().setModuleName("AppFundBillService-infrastructure.src.main.java")
                                .setParent("com.hellobike.platform.fund.bill.infrastructure.mysql")// 自定义包路径//
                                .setEntity("entity." + dir).setMapper(mapperDir).setXml(null).setService(null)
                                .setServiceImpl(null).setController(null)

                        );
                        // 包配置
                        add(new PackageConfig().setModuleName("AppFundBillService-infrastructure.src.main.resources")
                                .setParent(null)// 自定义包路径//
                                // 自定义包路径
                                .setXml(mapperDir).setEntity(null).setMapper(null).setService(null).setServiceImpl(null)
                                .setController(null));
                        // 包配置
                        if (needRepository) {
                            add(new PackageConfig().setModuleName("AppFundBillService-infrastructure.src.main.java")
                                    .setParent("com.hellobike.platform.fund.bill.infrastructure.mysql")// 自定义包路径//
                                    // 自定义包路径
                                    .setService("repository").setServiceImpl("repository.service.impl").setEntity(null)
                                    .setController(null).setMapper(null)
                                    .setXml(null));
                        }
                        if (false) {
                            // 包配置
                            add(new PackageConfig().setModuleName(null)
                                    .setParent("ripple-webapp.src.main.java.com.ripple")// 自定义包路径// 自定义包路径
                                    .setController("controller.app." + dir)// 这里是控制器包名，默认 web
                                    .setService(null).setServiceImpl(null).setEntity(null).setMapper(null).setXml(null));
                        }
                    }
                }).setCfg(
                        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                        new InjectionConfig() {
                            @Override
                            public void initMap() {
                                Map<String, Object> map = new HashMap<>();
                                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                                this.setMap(map);
                            }
                        }
                        // .setFileOutConfigList(Collections.<FileOutConfig>singletonList(new
                        // FileOutConfig(
                        // "/templates/mapper.xml" + ((1 == result) ? ".ftl" : ".vm")) {
                        // // 自定义输出文件目录
                        // @Override
                        // public String outputFile(TableInfo tableInfo) {
                        // return "/Users/stone/Downloads/mybatis/xml/" + tableInfo.getEntityName() +
                        // ".xml";
                        // }
                        // }))
                ).setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                        new TemplateConfig()
                        //.setXml("/templates/mapper.xml.vm")
                        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                        // .setController("...");
                        // .setEntity("...");
                        // .setMapper("...");
                        // .setXml("...");
                        // .setService("...");
                        // .setServiceImpl("...");
                );
        // 执行生成

        mpg.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
