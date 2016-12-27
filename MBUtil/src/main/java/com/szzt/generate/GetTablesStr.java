package com.szzt.generate;

import com.szzt.utils.FileUtils;

/**
 * Created by peterguo on 2016/12/26.
 */
public class GetTablesStr {

    public static String getTables() throws SQLException {
        Connection conn = null;
        String sql;

        String url = FileUtils.getValue("jdbc.url");
        String username = FileUtils.getValue("jdbc.username");
        String password = FileUtils.getValue("jdbc.password");
        String drvier = FileUtils.getValue("jdbc.driver");
        String mysqlUrl = url + "?user="+username+"&password="+password+"&useUnicode=true&characterEncoding=UTF8";

        try {
            Class.forName(drvier);
            conn = DriverManager.getConnection(mysqlUrl);
            Statement stmt = conn.createStatement();
            String dbName = getDatebaseName(url);
            sql = "SELECT GROUP_CONCAT(TABLE_NAME) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = " +"'"+dbName+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return "";
    }


    private static String getDatebaseName(String url){
        String dbName = "";
        if(url == "" || url == null){
            return dbName;
        }

        String []strs = url.split(":");
        String strdb = strs[strs.length-1];
        dbName = strdb.split("/")[1];
        return dbName;
    }

}
