package com.szzt.generate;

import com.szzt.utils.FileUtils;
import com.szzt.utils.XmlUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peterguo on 2016/12/26.
 */
public class AutoGenerate {


    /***
     * 生成对应的model dao mapper
     */
    public static void process() {

        System.out.println("开始生成代码------------------");
        writeHead();

        List<String> warnings = new ArrayList<String>();

        boolean overwrite = true;
        File configFile = new File(FileUtils.getResourceRealPath());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(new VerboseProgressCallback());


            System.out.println("Mybatis 工具类生产成功！！！！！！！");
            System.out.println("model路径为：   " +FileUtils.getValue("model.package"));
            System.out.println("mapper路径为：   " +FileUtils.getValue("xml.mapper.package"));
            System.out.println("dao路径为：   " +FileUtils.getValue("dao.package"));
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /***
     *
     * 生成配置文件mbg_configuration_mysql.xml所需要的xml映射关系
     * 执行SELECT GROUP_CONCAT(TABLE_NAME) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '数据库名称'得到的结果作为参数
     * @param tables
     */
    public static void fillingXml(String tables) {
        String[] params = tables.replaceAll(" ", "").split(",");
        System.out.println("需要转化的表名-------------------");
        for (String param : params) {
            System.out.println(param);
        }
        for (String param : params) {
            if (!(param == null || "".equals(param.trim()))) {
                int len = param.length();
                StringBuilder sb = new StringBuilder(len);
                for (int i = 0; i < len; i++) {
                    char c = param.charAt(i);
                    if (i == 0) {
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    } else if (c == '_') {
                        if (++i < len) {
                            sb.append(Character.toUpperCase(param.charAt(i)));
                        }
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                }
                String result = sb.toString();
                //填充配置文件
                XmlUtils.parseXml(FileUtils.getResourceRealPath(),param,result);
            } else {
                System.out.println("填充到mbg_configuration_mysql.xml的数据出错--------------------");
            }
        }
    }


    /***
     * 填充头部 之前的写入会覆盖 目前没有找到原因
     */
    private static void writeHead(){
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(false);
        try{

            DocumentBuilder db=dbf.newDocumentBuilder();
            String xmlPath = FileUtils.getResourceRealPath();
            Document xmldoc=db.parse(xmlPath);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd");
            transformer.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
