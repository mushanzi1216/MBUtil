package com.szzt.main;

import com.szzt.generate.AutoGenerate;
import com.szzt.generate.GetTablesStr;

import java.sql.SQLException;


public class Main {


    /**
     * 根据配置文件中的数据库连接信息，自动生成对应的model dao mapper
     * 目前仅支持mysql数据库
     * @param args
     */
    public static void main(String[] args) throws SQLException{

        
        //读取配置文件信息,生成数据库中所有表的字符串
        String tablestr = GetTablesStr.getTables();
        //填充配置文件中需要填充的信息
        AutoGenerate.fillingXml(tablestr);
        //生成对应的model dao mapper
        AutoGenerate.process();
    }



}
