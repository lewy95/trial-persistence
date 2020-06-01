package cn.xzxy.lewy.mybatisplus.utils;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CodeGenerator 代码生成器
 */
@Slf4j
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // ============================全局配置============================
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/persistence-mybatis-plus";
        gc.setOutputDir(projectPath + "/src/main/java");   //生成文件的输出目录
        gc.setAuthor("lewy");                      // 作者
        gc.setOpen(false);                         // 是否打开输出目录 默认值:true
        gc.setServiceName("%sService");            // service 命名方式 %s 会自动填充表实体属性
        gc.setServiceImplName("%sServiceImpl");    // service impl 命名方式
        gc.setMapperName("%sMapper");              // mapper/dao 命名方式
        //gc.setEntityName("%sEntity");            // 实体类命名方式，一般不会写，实体类就是实体类的名字
        //gc.setXmlName("%sMapper");               // mapper.xml 命令方式，一般不会写，会放在resource目录下
        //gc.setFileOverride(true);                // 是否覆蓋已有文件 默认值 false
        //gc.setActiveRecord(true);
        gc.setEnableCache(false);                  // XML 二级缓存
        gc.setBaseResultMap(true);                 // 开启 mapper.xml 中 ResultMap
        gc.setBaseColumnList(false);               // 开启 mapper.xml 中 BaseResultMap
        //gc.setSwagger2(true);                    // 开启 swagger2 模式 默认false
        mpg.setGlobalConfig(gc);

        // ============================数据源配置============================
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/sakila?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // ============================包配置============================
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("cn.xzxy.lewy.mybatisplus");        // 设置父包名
        pc.setEntity("pojo");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        // ============================自定义配置============================
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapping/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
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

        // ============================配置模板============================
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // ============================映射策略配置============================
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);                        // 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);                  // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        //strategy.setCapitalMode(true);			                                  // 全局大写命名 ORACLE 注意
        //strategy.setSuperEntityClass("cn.xzxy.lewy.mybatisplus.pojo");              // 自定义继承的Entity类全称，带包名
        //strategy.setSuperEntityColumns(new String[]{"create_time", "update_time"}); // 自定义实体，公共字段
        //strategy.setTablePrefix(pc.getModuleName() + "_");		                  // 表前缀
        strategy.setEntityLombokModel(true);                                          // 实体是否为lombok模型（默认 false
        strategy.setRestControllerStyle(true);                                        // 生成 @RestController 控制器
        //strategy.setSuperControllerClass("cn.xzxy.lewy.mybatisplus.controller");    // Controller中的公共父类
        //strategy.setSuperEntityColumns("id");                                       // Controller父类中的公共字段
        //strategy.setControllerMappingHyphenStyle(true);                             // Controller驼峰映射
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
