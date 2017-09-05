package com.whh.common.utils.enums;


import com.alibaba.fastjson.JSONObject;
import com.whh.common.utils.db.JdbcUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huahui.wu on 2017/6/19.
 */
public class EnumGenerateUtil {

    private static BufferedWriter out;

    private static String[] filterKeys = {"TIME_UNIT"};

    /**
     * 所生成文件需要注意的地方：
     * code数据：
     * 如果为数字，则带有NUMBER前缀
     * 如果为负数，则带有NEGATIVE前缀
     * 如果类型为时间单位，则没有做大小写转换
     *
     * @param packagePath
     * @param fileName
     */
    public static String createEmunFile(String packagePath, String fileName, String filePath) throws IOException {
        //文件名合法性验证
        RegResult result = handClassName(fileName);
        String className = "";
        if (!RegResult.LEGAL.equals(result)) {
            return result.getDescription();
        } else {
            className = result.getResult();
        }

        JdbcUtil jdbc = new JdbcUtil();
        filePath = filePath + "/src/main/java/" + packagePath.replaceAll("\\.", "/");
        File file = new File(filePath);
        if (!file.isDirectory()) {
            file.mkdir();
        }

        file = new File(filePath + "/" + className + ".java");
        if (!file.exists()) {
            file.createNewFile();
        }

        try {

            ResultSet rs = jdbc.executeQuery(
                    "select t.defined_code_type,t.display_code, t.defined_code, t.defined_name,t.data_type\n" +
                            "from BA_DEFINED_CODE t order by t.defined_code,t.display_seq asc");
            out = new BufferedWriter(new FileWriter(file));

            initFile(packagePath, className);
            createEmunFile(rs);
            out.write("}\n");
            out.flush();
            out.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "生成成功";
    }

    private static void createEmunFile(ResultSet rs) throws IOException, SQLException {
        Map<String, List<JSONObject>> map = new HashMap<>();
        while (rs.next()) {
            String defined_code_type = rs.getString("DEFINED_CODE_TYPE");
            JSONObject obj = new JSONObject();
            obj.put("DISPLAY_CODE", rs.getString("DISPLAY_CODE"));
            obj.put("DEFINED_CODE", rs.getString("DEFINED_CODE"));
            obj.put("DEFINED_NAME", rs.getString("DEFINED_NAME"));
            obj.put("DATA_TYPE", rs.getString("DATA_TYPE"));
            if (map.containsKey(rs.getString("DEFINED_CODE_TYPE"))) {
                map.get(rs.getString("DEFINED_CODE_TYPE")).add(obj);
            } else {
                List<JSONObject> list = new ArrayList<>();
                list.add(obj);
                map.put(defined_code_type, list);
            }
        }
        //修改 文件
        createEmunFile(map);
    }

    private static String returnSpace = "   ";
    private static String enumSpace = "        ";
    private static String methodSpace = "              ";

    private static void createEmunFile(Map<String, List<JSONObject>> map) throws IOException {

        for (String key : map.keySet()) {
            List<JSONObject> contents = map.get(key);
            StringBuffer conts = new StringBuffer("");
            conts.append("\n" + enumSpace + "public enum " + key.toUpperCase() + " {\n");
            String dateType = null;
            String dataTypeTemp = null;
            for (int i = 0; i < contents.size(); i++) {
                JSONObject jso = contents.get(i);
                String paramValue = jso.getString("DISPLAY_CODE");
                dateType = jso.getString("DATA_TYPE");
                if (dataTypeTemp != null && !dateType.equals(dataTypeTemp)) {
                    System.out.println("data_type 不一致,请手动删除错误文件");
                    throw new IOException();
                }
                dataTypeTemp = jso.getString("DATA_TYPE");
                conts.append(methodSpace + paramValue).append("(");
                switch (dateType) {
                    case "Long":
                        conts.append(jso.getString("DEFINED_CODE")).append("L");
                        break;
                    case "Integer":
                        conts.append(jso.getString("DEFINED_CODE"));
                        break;
                    default:
                        conts.append("\"");
                        conts.append(jso.getString("DEFINED_CODE"));
                        conts.append("\"");
                }

                conts.append(", \"").append(jso.getString("DEFINED_NAME").replaceAll("[\n|\r]{1,}", "")).append("\"");
                if ((i + 1) == contents.size()) {
                    conts.append(");\n\r");
                } else {
                    conts.append("),\n");
                }
            }

            conts.append(methodSpace + "private " + dateType + " code;\n");
            conts.append(methodSpace + "private String name;\n\r");
            conts.append(methodSpace + key.toUpperCase()).append("(" + dateType + " code, String name) {\n")
                    .append(methodSpace + returnSpace + "this.code = code; \n")
                    .append(methodSpace + returnSpace + "this.name = name; \n" + methodSpace + "}\n");
            conts.append(methodSpace + "public " + dateType + " getCode").append("() {\n").append(methodSpace + returnSpace + "return this.code; \n" + methodSpace + "}\n");
            conts.append(methodSpace + "public String getName").append("() {\n").append(methodSpace + returnSpace + "return this.name; \n" + methodSpace + "}\n");
            conts.append(methodSpace + "public String toString").append("() {\n").append(methodSpace + returnSpace + "return  (\"name = \" + this.name + \",\" + \"code = \" + this.code );" + methodSpace + "}\n");
            conts.append(enumSpace + "}\n");
            out.write(conts.toString());
        }
    }

    private static void initFile(String packagePath, String className) throws IOException {
        StringBuffer content = new StringBuffer("");

        content.append("package " + packagePath + ";\n\r");
        content.append("public class " + className + " {").append("\n");
        out.write(content.toString());
    }


    private static boolean inFilter(String key) {
        for (String filter : filterKeys) {
            if (filter.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private static String chRegx = "[\\u4E00-\\u9FA5]";
    private static String nuRegx = "^[0-9]";
    private static String ruleRegx = "[^\\w\\.$_]";

    private static RegResult handClassName(String cname) {

        //中文判断
        Matcher match = Pattern.compile(chRegx).matcher(cname);
        if (match.find()) {
            return RegResult.ILLEGAL_CHINESE;
        }

        //数字开头验证
        match = Pattern.compile(nuRegx).matcher(cname);
        if (match.find()) {
            return RegResult.ILLEGAL_NUMBER;
        }

        //非法验证
        match = Pattern.compile(ruleRegx).matcher(cname);
        if (match.find()) {
            return RegResult.ILLEGAL;
        }

        if (cname == null || "".equals(cname)) {
            RegResult.LEGAL.setResult("NoNameFile");
        } else {
            RegResult.LEGAL.setResult(cname.substring(0, 1).toUpperCase() + cname.substring(1));
        }

        return RegResult.LEGAL;
    }

    public enum RegResult {

        LEGAL("合法", "legal"), ILLEGAL("文件名非法", "illegal"),
        ILLEGAL_CHINESE("文件名含有中文字符", "illegal"), ILLEGAL_NUMBER("文件名不能以数字开头", "illegal");

        private String description;

        private String result;

        RegResult(String description, String result) {
            this.description = description;
            this.result = result;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

}
