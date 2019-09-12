# mplus-support
对MybatisPlus开源框架源码Generator模块进行改造，支持开发者生成文件目录进行定制化输出.

源码改造点：

mybatisPuls generator生成文件结构为：
![Image text](https://raw.githubusercontent.com/wlstone119/img/master/20190912122500.jpg)

修改为：
![Image text](https://raw.githubusercontent.com/wlstone119/img/master/20190912123547.jpg)

增加packageConfigList循环生成文件：

public ConfigBuilder(List<PackageConfig> configList, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig,
                         TemplateConfig template, GlobalConfig globalConfig){
        // 全局配置
        if (null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }
        // 模板配置
        if (null == template) {
            this.template = new TemplateConfig();
        } else {
            this.template = template;
        }
        // 包配置
        if (CollectionUtils.isEmpty(configList)) {
            handlerPackage(this.template, this.globalConfig.getOutputDir(), new ArrayList<PackageConfig>());
        } else {
            handlerPackage(this.template, this.globalConfig.getOutputDir(), configList);
        }
        this.dataSourceConfig = dataSourceConfig;
        handlerDataSource(dataSourceConfig);
        // 策略配置
        if (null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }
        handlerStrategy(this.strategyConfig);
    }

    private void handlerPackage(TemplateConfig template, String outputDir, List<PackageConfig> configList) {
        packageInfo = new HashMap<>();
        pathInfo = new HashMap<>();

        for(PackageConfig config : configList) {
            if (StringUtils.isNotEmpty(config.getEntity())) {
                packageInfo.put(ConstVal.ENTITY, joinPackage(config.getParent(), config.getEntity()));
                // 生成路径信息
                if (StringUtils.isNotEmpty(template.getEntity(getGlobalConfig().isKotlin()))) {
                    pathInfo.put(ConstVal.ENTITY_PATH,
                                 joinPath(outputDir, config.getModuleName() + "." + packageInfo.get(ConstVal.ENTITY)));
                }
            }
            if (StringUtils.isNotEmpty(config.getMapper())) {
                packageInfo.put(ConstVal.MAPPER, joinPackage(config.getParent(), config.getMapper()));
                if (StringUtils.isNotEmpty(template.getMapper())) {
                    pathInfo.put(ConstVal.MAPPER_PATH,
                                 joinPath(outputDir, config.getModuleName() + "." + packageInfo.get(ConstVal.MAPPER)));
                }
            }
            if (StringUtils.isNotEmpty(config.getXml())) {
                packageInfo.put(ConstVal.XML, joinPackage(config.getParent(), config.getXml()));
                if (StringUtils.isNotEmpty(template.getXml())) {
                    pathInfo.put(ConstVal.XML_PATH,
                                 joinPath(outputDir, config.getModuleName() + "." + packageInfo.get(ConstVal.XML)));
                }
            }
            if (StringUtils.isNotEmpty(config.getService())) {
                packageInfo.put(ConstVal.SERIVCE, joinPackage(config.getParent(), config.getService()));
                if (StringUtils.isNotEmpty(template.getService())) {
                    pathInfo.put(ConstVal.SERIVCE_PATH,
                                 joinPath(outputDir, config.getModuleName() + "." + packageInfo.get(ConstVal.SERIVCE)));
                }
            }
            if (StringUtils.isNotEmpty(config.getServiceImpl())) {
                packageInfo.put(ConstVal.SERVICEIMPL, joinPackage(config.getParent(), config.getServiceImpl()));
                if (StringUtils.isNotEmpty(template.getServiceImpl())) {
                    pathInfo.put(ConstVal.SERVICEIMPL_PATH,
                                 joinPath(outputDir, config.getModuleName() + "." + packageInfo.get(ConstVal.SERVICEIMPL)));
                }
            }
            if (StringUtils.isNotEmpty(config.getController())) {
                packageInfo.put(ConstVal.CONTROLLER, joinPackage(config.getParent(), config.getController()));
                if (StringUtils.isNotEmpty(template.getController())) {
                    pathInfo.put(ConstVal.CONTROLLER_PATH,
                                 joinPath(outputDir, config.getModuleName() + "." + packageInfo.get(ConstVal.CONTROLLER)));
                }
            }
            if (StringUtils.isNotEmpty(config.getFacacde())) {
                packageInfo.put(ConstVal.FACADE, joinPackage(config.getParent(), config.getFacacde()));
                if (StringUtils.isNotEmpty(template.getFacade())) {
                    pathInfo.put(ConstVal.FACADE_PATH,
                                 joinPath(outputDir, config.getModuleName() + "." + packageInfo.get(ConstVal.FACADE)));
                }
            }
            if (StringUtils.isNotEmpty(config.getFacacdeImpl())) {
                packageInfo.put(ConstVal.FACADEIMPL, joinPackage(config.getParent(), config.getFacacdeImpl()));
                if (StringUtils.isNotEmpty(template.getServiceImpl())) {
                    pathInfo.put(ConstVal.FACADEIMPL_PATH,
                                 joinPath(outputDir, config.getModuleName() + "." + packageInfo.get(ConstVal.FACADEIMPL)));
                }
            }
        }

    }

# 生成文件demo如下：


public class Generator {


    public static void doGenerator(String author, final String dir, String project_url, String tablePrefix, String table_name, final boolean isNeedController, final boolean isNeedService) {
        // 自定义需要填充的字段
        ArrayList<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        GlobalConfig globalConfig = new GlobalConfig().setOutputDir(project_url)// 输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                // .setKotlin(true) 是否生成 kotlin 代码
                //.setAuthor("wanglei")
                .setAuthor(author)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sMapper").setXmlName("%sMapper");
        if (isNeedService) {

            globalConfig.setServiceName("I%sService").setServiceImplName("%sServiceImpl");
        }
        if (isNeedController) {
            globalConfig.setControllerName("%sController");
        }
        AutoGenerator mpg = new AutoGenerator()
                .setGlobalConfig(
                        // 全局配置
                        globalConfig)
                .setDataSource(
                        // 数据源配置
                        new DataSourceConfig().setDbType(DbType.MYSQL)// 数据库类型
                                .setTypeConvert(new MySqlTypeConvert() {
                                    // 自定义数据库表字段类型转换【可选】
                                    @Override
                                    public DbColumnType processTypeConvert(String fieldType) {
                                        System.out.println("转换类型：" + fieldType);
                                        // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                                        // return DbColumnType.BOOLEAN;
                                        // }
                                        return super.processTypeConvert(fieldType);
                                    }
                                }).setDriverName("com.mysql.jdbc.Driver").setUsername("test").setPassword("test!")
                                .setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull"))
                .setStrategy(
                        // 策略配置
                        new StrategyConfig()
                                // .setCapitalMode(true)// 全局大写命名
                                // .setDbColumnUnderline(true)//全局下划线命名
                                .setTablePrefix(new String[]{tablePrefix})// 此处可以修改为您的表前缀
                                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                                .setInclude(new String[]{table_name}) // 需要生成的表
                                // .setExclude(new String[]{"test"}) // 排除生成的表
                                // 自定义实体父类
                                // .setSuperEntityClass("com.xdong.ripple.CommonEntity")
                                // 自定义实体，公共字段
                                // .setSuperEntityColumns(new String[]{"test_id"})
                                //.setTableFillList(tableFillList)
                                // 自定义 mapper 父类
                                .setSuperMapperClass("com.baomidou.mybatisplus.mapper.BaseMapper")
                                // 自定义 service 父类
                                .setSuperServiceClass("com.baomidou.mybatisplus.service.IService")
                                // 自定义 service 实现类父类
                                .setSuperServiceImplClass("com.baomidou.mybatisplus.service.impl.ServiceImpl")
                        // 自定义 controller 父类
                        // .setSuperControllerClass("com.weidai.demo.TestController")
                        // 【实体】是否生成字段常量（默认 false）
                        // public static final String ID = "test_id";
                        // .setEntityColumnConstant(true)
                        // 【实体】是否为构建者模型（默认 false）
                        // public User setName(String name) {this.name = name; return this;}
                        // .setEntityBuilderModel(true)
                        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                        // .setEntityLombokModel(true)
                        // Boolean类型字段是否移除is前缀处理
                        // .setEntityBooleanColumnRemoveIsPrefix(true)
                        // .setRestControllerStyle(true)
                        // .setControllerMappingHyphenStyle(true)
                        // .entityTableFieldAnnotationEnable(true)
                        //.setLogicDeleteFieldName("is_delete")
                )
                .setPackageInfoList(
                        new ArrayList<PackageConfig>() {{// 包配置
                            add(new PackageConfig().setModuleName("XX-dal.src.main.java")
                                    .setParent("com.XX.project")// 自定义包路径// 自定义包路径
                                    .setEntity("dal.bean." + dir)
                                    .setMapper("dal.mapper." + dir)
                                    .setXml(null)
                                    .setService(null)
                                    .setServiceImpl(null)
                                    .setController(null)
                                    .setFacacde(null)
                                    .setFacacdeImpl(null)

                            );

                            // 包配置

                            add(new PackageConfig().setModuleName("XX-dal.src.main.resources")
                                    .setParent("com.XX.project")// 自定义包路径// 自定义包路径
                                    .setXml("mybatis.mapping." + dir)

                                    .setEntity(null)
                                    .setMapper(null)
                                    .setService(null)
                                    .setServiceImpl(null)
                                    .setController(null)
                                    .setFacacde(null)
                                    .setFacacdeImpl(null)
                            );
                            if (isNeedService) {

                                // 包配置
                                add(new PackageConfig().setModuleName("XX-service.src.main.java")
                                        .setParent("com.XX.project")// 自定义包路径// 自定义包路径
                                        .setService("biz.dalService." + dir)
                                        .setServiceImpl("biz.dalService." + dir + ".impl")

                                        .setEntity(null)
                                        .setController(null)
                                        .setFacacde(null)
                                        .setFacacdeImpl(null)
                                        .setMapper(null)
                                        .setXml(null)
                                );
                            }
                            if (isNeedController) {
                                // 包配置
                                add(

                                        new PackageConfig().setModuleName("XX-webapp.src.main.java")
                                                .setParent("com.XX.project")// 自定义包路径// 自定义包路径
                                                .setController("controller.app." + dir)// 这里是控制器包名，默认 web

                                                .setService(null)
                                                .setServiceImpl(null)
                                                .setEntity(null)
                                                .setFacacde(null)
                                                .setFacacdeImpl(null)
                                                .setMapper(null)
                                                .setXml(null)
                                );
                            }
                        }}
                ).setCfg(
                        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                        new InjectionConfig() {
                            @Override
                            public void initMap() {
                                Map<String, Object> map = new HashMap<>();
                                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                                this.setMap(map);
                            }
                        }
                        // .setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(
                        // "/templates/mapper.xml" + ((1 == result) ? ".ftl" : ".vm")) {
                        // // 自定义输出文件目录
                        // @Override
                        // public String outputFile(TableInfo tableInfo) {
                        // return "/Users/stone/Downloads/mybatis/xml/" + tableInfo.getEntityName() + ".xml";
                        // }
                        // }))
                ).setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                        new TemplateConfig().setXml("/templates/mapper.xml.vm")
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
    
public class WlGenerator {

    public static void main(String[] args) {
        
        Date date = new Date(1555084800000L);
        Date date2 = new Date(1557757297016L);
        System.out.println(DateUtil.formatDate(date));
        System.out.println(DateUtil.formatDate(date2));
        
        
        // 作者
        String author = "stone";
        // 文件目录
        final String dir = "XX";
        // 本地项目路径
        String project_url = "/Users/stone/git/XX";
        // 前缀
        String tablePrefix = "XX";
        // 表名
        String[] table_names = new String[] { "XX_user"};
        //是否需要controller
        boolean isNeedController = false;
        //是否需要service
        boolean isNeedService = true;

        for (String table_name : table_names) {
            Generator.doGenerator(author, dir, project_url, tablePrefix, table_name, isNeedController, isNeedService);
        }
    }
}
