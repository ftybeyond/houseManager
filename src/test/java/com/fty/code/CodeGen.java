package com.fty.code;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CodeGen {

    VelocityEngine ve;

    String classpath ="";

    String basePath = "com/qth";

    @Before
    public void engineInit(){
        classpath = this.getClass().getResource("/").getPath();
        // 初始化模板引擎
        ve= new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
        ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //中文編碼
        ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        ve.init();
    }

    @Test
    public void enter(){
        //Mybatis-generator
        try {
            List<String> warnings = new ArrayList<>();
            boolean overwrite = true;
            File configFile = new File(classpath+ "/generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            List<TableConfiguration> tableList = config.getContext("mysql").getTableConfigurations();
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);

            //生成action、页面等
            for (int i = 0; i < tableList.size(); i++) {
//                System.out.println(tableList.get(i).get());
                gen("action",tableList.get(i).getDomainObjectName());
                gen("iservice",tableList.get(i).getDomainObjectName());
                gen("service",tableList.get(i).getDomainObjectName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void gen(String type,String domain){

        //基于velocity生成
        FileWriter fileWriter = null;
        // 设置变量
        VelocityContext ctx = new VelocityContext();
        ctx.put("domain", domain);
        ctx.put("domainObj",toLowerCaseFirstOne(domain));
        try {
            if ("dao".equals(type)) {
                File dir = new File(classpath+basePath + "/dao");
                dir.mkdirs();
                // 获取模板文件
                Template template = ve.getTemplate("template/dao.vm");
                //输出到文件
                fileWriter = new FileWriter(dir.getPath() +"/"+domain+"Mapper.java" );
                template.merge(ctx,fileWriter);
                fileWriter.close();
            }else if("action".equals(type)){
                File dir = new File(classpath+basePath + "/action");
                dir.mkdirs();
                // 获取模板文件
                Template template = ve.getTemplate("template/action.vm");
                //输出到文件
                fileWriter = new FileWriter(dir.getPath()+"/" +domain+"Controller.java" );
                template.merge(ctx,fileWriter);
                fileWriter.close();
            }else if("iservice".equals(type)){
                File dir = new File(classpath+basePath + "/service");
                dir.mkdirs();
                // 获取模板文件
                Template template = ve.getTemplate("template/iservice.vm");
                //输出到文件
                fileWriter = new FileWriter(dir.getPath()+"/" +"I" +domain+"Service.java" );
                template.merge(ctx,fileWriter);
                fileWriter.close();
            }else if("service".equals(type)){
                File dir = new File(classpath+basePath + "/service/impl");
                dir.mkdirs();
                // 获取模板文件
                Template template = ve.getTemplate("template/service.vm");
                //输出到文件
                fileWriter = new FileWriter(dir.getPath()+"/"+domain+"Service.java" );
                template.merge(ctx,fileWriter);
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
