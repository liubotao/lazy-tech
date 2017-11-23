package com.whh.common.utils.enums;

import com.whh.common.utils.db.JdbcUtil;
import com.whh.common.utils.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 枚举生成工具类，2
 * Created by huahui.wu on 2017/11/23.
 */
public class EnumUtil {

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
    public static String createEuumFile(String packagePath, String fileName, String filePath) throws IOException {
        //文件名合法性验证
        RegResult result = handClassName(fileName);
        String className = "";
        if (!RegResult.LEGAL.equals(result)) {
            return result.getDescription();
        } else {
            className = result.getResult();
        }

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
            Map<String, List<Map>> dbData = getDbData();
            out = new BufferedWriter(new FileWriter(file));
            initFile(packagePath, className);

            createEnumFile(dbData);
            out.write("}\n");
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

        return "生成成功";
    }

    //			t.defined_code_type,t.display_code, t.defined_code, t.defined_name
    private static Map<String, List<Map>> getDbData() throws IOException, SQLException {
        Map<String, List<Map>> map = new HashMap<>();
        JdbcUtil jdbc = new JdbcUtil();

        List<Map<String, Object>> records = null;
        try {
            String sql = "select * from BA_DEFINED_CODE order by defined_code,display_seq asc";
            ResultSet rs = jdbc.executeQuery(sql);
            records = getRecords(rs);
        } finally {
            jdbc.close();
        }
        for (Map<String, Object> record : records) {
            String definedCodeType = (String) record.get("DEFINED_CODE_TYPE");
            List<Map> list = map.get(definedCodeType);
            if (list == null) {
                list = new ArrayList<>();
                list.add(record);

                map.put(definedCodeType, list);
            } else {
                list.add(record);
            }
        }

        return map;
    }

    private static List<Map<String, Object>> getRecords(ResultSet rs) throws IOException, SQLException {
        List<Map<String, Object>> list = new ArrayList<>();

        ResultSetMetaData rmd = rs.getMetaData();
        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int i = 1; i <= rmd.getColumnCount(); i++) {
                String columnName = rmd.getColumnName(i);
                Object value = rs.getObject(columnName);
                record.put(columnName.toUpperCase(), value);
            }

            list.add(record);
        }
        return list;
    }

    private static String methodSpace = "    ";

    public static String getSpaces(int tabs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabs; i++) {
            sb.append(methodSpace);
        }
        return sb.toString();
    }

    public static String getMapValue(Map<String, Object> map, String key) {
        Object obj = map.get(key);
        return obj == null ? null : obj.toString();
    }

    private static void createEnumFile(Map<String, List<Map>> map) throws IOException {
        for (String key : map.keySet()) {
            List<Map> contents = map.get(key);

            String enumName = key.toUpperCase();

            String temp1 = "        {0}({1}, \"{2}\"),\n";
            StringBuffer conts = new StringBuffer("");
            String DATA_TYPE = null;
            for (int i = 0; i < contents.size(); i++) {
                Map jso = contents.get(i);
                String DISPLAY_CODE = getMapValue(jso, "DISPLAY_CODE");
                String DEFINED_CODE = getMapValue(jso, "DEFINED_CODE");
                String DEFINED_NAME = getMapValue(jso, "DEFINED_NAME");
                if (StringUtils.isNotEmpty(DEFINED_NAME)) {
                    DEFINED_NAME = DEFINED_NAME.replaceAll("[\n|\r]{1,}", "");
                }

                String DATA_TYPE1 = getMapValue(jso, "DATA_TYPE");
                if (DATA_TYPE != null && !DATA_TYPE.equals(DATA_TYPE1)) {
                    String errorMsg = "data_type 不一致,请手动删除错误纪录：" + key + ", data_type:" + DATA_TYPE + "," + DATA_TYPE1;
                    throw new IOException(errorMsg);
                } else {
                    DATA_TYPE = DATA_TYPE1;
                }

                if (StringUtils.isEmpty(DISPLAY_CODE)) {
                    if (StringUtils.isNumeric(DEFINED_CODE)) {
                        DISPLAY_CODE = enumName + "_" + DEFINED_CODE;
                    } else {
                        DISPLAY_CODE = DEFINED_CODE;
                    }
                }

                String DEFINED_CODE_V = null;
                DATA_TYPE1 = DATA_TYPE1 == null ? "" : DATA_TYPE1;
                if (StringUtils.isNumeric(DEFINED_CODE)) {
                    switch (DATA_TYPE1) {
                        case "Long":
                            DEFINED_CODE_V = DEFINED_CODE + "L";
                            break;
                        case "Integer":
                            DEFINED_CODE_V = DEFINED_CODE;
                            break;
                        default: {
                            DEFINED_CODE_V = "\"" + DEFINED_CODE + "\"";
                        }
                    }
                } else {
                    DEFINED_CODE_V = "\"" + DEFINED_CODE + "\"";
                }

                String item = MessageFormat.format(temp1, DISPLAY_CODE, DEFINED_CODE_V, DEFINED_NAME);
                conts.append(item);
            }
            conts.append("        ;");
            if (StringUtils.isEmpty(DATA_TYPE)) {
                DATA_TYPE = "String";
            }

            String value = format.replace("{ENUM_NAME}", enumName);
            value = value.replace("{ENUM_LIST}", conts.toString());
            value = value.replace("{DATA_TYPE}", DATA_TYPE);

            out.write(value);
        }
    }

    static String format = "    public enum {ENUM_NAME} {\n" +
            "{ENUM_LIST}" +
            "\n" +
            "        private {DATA_TYPE} code;\n" +
            "        private String name;\n" +
            "\n" +
            "        {ENUM_NAME}({DATA_TYPE} code, String name) {\n" +
            "            this.code = code;\n" +
            "            this.name = name;\n" +
            "        }\n" +
            "\n" +
            "        public {DATA_TYPE} getCode() {\n" +
            "            return this.code;\n" +
            "        }\n" +
            "\n" +
            "        public String getName() {\n" +
            "            return this.name;\n" +
            "        }\n" +
            "\n" +
            "        @Override\n" +
            "        public String toString() {\n" +
            "            return (\"name = \" + this.name + \",\" + \"code = \" + this.code);\n" +
            "        }\n" +
            "\n" +
            "        public boolean equalByCode({DATA_TYPE} code) {\n" +
            "            return this.code != null && this.code.equals(code);\n" +
            "        }\n" +
            "\n" +
            "        public static {ENUM_NAME} getByCode({DATA_TYPE} code) {\n" +
            "            for ({ENUM_NAME} item : values()) {\n" +
            "                if (item.code.equals(code)) {\n" +
            "                    return item;\n" +
            "                }\n" +
            "            }\n" +
            "            return null;\n" +
            "        }\n" +
            "    }\n";

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


    public static <T extends Enum<T>, E> T getEnumByCode(Class<T> clazz, E code) {
        return getEnumByCode(clazz, code, "getCode");
    }

    public static <T extends Enum<T>, E> T getEnumByCode(Class<T> clazz, E code, String getCodeMethodName) {
        T result = null;
        try {
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(getCodeMethodName);
            String typeCodeVal = null;
            for (T entity : arr) {
                typeCodeVal = targetMethod.invoke(entity).toString();
                if (typeCodeVal.contentEquals(String.valueOf(code))) {
                    result = entity;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}