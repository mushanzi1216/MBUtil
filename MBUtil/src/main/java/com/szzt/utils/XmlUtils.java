package com.szzt.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by peterguo on 2016/12/27.
 */
public class XmlUtils {

    /**
     * 填充mbg_configuration_mysql.xml
     * @param xmlPath
     * @param tableName
     * @param domainObjectName
     */
    public static void parseXml(String xmlPath,String tableName,String domainObjectName){
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(false);

        try{

            DocumentBuilder db=dbf.newDocumentBuilder();
            Document xmldoc=db.parse(xmlPath);

            NodeList gList = xmldoc.getElementsByTagName("generatorConfiguration");
            Element generatorConfiguration = (Element) gList.item(0);
            NodeList cList = generatorConfiguration.getElementsByTagName("context");
            Element root = (Element) cList.item(0);
            Element table =xmldoc.createElement("table");
            table.setAttribute("tableName", tableName);
            table.setAttribute("domainObjectName", domainObjectName);

            root.appendChild(table);
            //保存
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));

        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
