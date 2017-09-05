package com.whh.common.utils.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chunchun.xu on 2017/5/4.
 */
public class StringUtils {

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuffer()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }


    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * 检查对象是否为数字型字符串,包含负数开头的。
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        char[] chars = obj.toString().toCharArray();
        int length = chars.length;
        if (length < 1)
            return false;

        int i = 0;
        if (length > 1 && chars[0] == '-')
            i = 1;

        for (; i < length; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 把通用字符编码的字符串转化为汉字编码。
     */
    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }

    /**
     * 将驼峰字符串转为下划线分隔的字符串
     *
     * @param name
     * @return
     */
    public static String toUnderlineStyle(String name) {
        StringBuilder newName = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    newName.append("_");
                }
                newName.append(Character.toLowerCase(c));
            } else {
                newName.append(c);
            }
        }
        return newName.toString();
    }

    public static <T> String toString(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof byte[] || t instanceof Byte[]) {
            return new String((byte[]) t);
        } else {
            return t.toString();
        }
    }

    public static String toString(byte[] data, int offset, int length, String charset) {
        if (data == null) {
            return null;
        }
        try {
            return new String(data, offset, length, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static byte[] toBytes(String data) {
        return toBytes(data, "utf-8");
    }

    public static byte[] toBytes(String data, String charset) {
        if (data == null) {
            return null;
        }
        try {
            return data.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static boolean isDouble(String arg0) {
        if (arg0 == null || arg0.length() == 0) return false;
        try {
            Double.parseDouble(arg0);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 如果arg为null或转化为小定时为"null", 则返回"", 否则返回本身
     *
     * @param arg
     * @return
     */
    public static String nullToEmpty(String arg) {
        return arg == null || "null".equals(arg.toLowerCase()) ? "" : arg;
    }

    /**
     * 将字符串数组中的元素中间使用separator分隔连接为一个字符串
     *
     * @param array
     * @param separator
     * @return
     */
    public static String joinString(String[] array, String separator) {
        return joinString(Arrays.asList(array), separator);
    }

    /**
     * 将字符串List中的元素中间使用separator分隔连接为一个字符串
     *
     * @param list
     * @param separator
     * @return
     */
    public static String joinString(List<String> list, String separator) {
        return joinString(list.iterator(), separator);
    }

    public static String joinLong(List<Long> list, String separator) {
        return joinString(list.iterator(), separator);
    }

    public static String joinInteger(List<Integer> list, String separator) {
        return joinString(list.iterator(), separator);
    }

    /**
     * 将字符串Collection中的元素中间使用separator分隔连接为一个字符串
     *
     * @param coll
     * @param separator
     * @return
     */
    public static String joinString(Collection<String> coll, String separator) {
        return joinString(coll.iterator(), separator);
    }

    /**
     * 将字符串Collection中的元素中间使用separator分隔连接为一个字符串
     *
     * @param list
     * @param separator
     * @return
     */
    public static <T> String joinString(Iterator<T> list, String separator) {
        StringBuilder strBuilder = new StringBuilder();
        while (list.hasNext()) {
            if (strBuilder.length() > 0) {
                strBuilder.append(separator);
            }
            strBuilder.append(list.next());
        }
        return strBuilder.toString();
    }

    public static String[] split(String str, String separator) {
        List<String> list = splitToList(str, separator);
        return list.toArray(new String[list.size()]);
    }

    public static List<String> splitToList(String str, String separator) {
        List<String> list = new ArrayList<String>();
        if (isEmpty(str)) {
            return list;
        }

        if (isEmpty(separator)) {
            list.add(str);
            return list;
        }
        int lastIndex = -1;
        int index = str.indexOf(separator);
        if (-1 == index) {
            list.add(str);
            return list;
        }
        while (index >= 0) {
            if (index > lastIndex) {
                list.add(str.substring(lastIndex + 1, index));
            } else {
                list.add("");
            }

            lastIndex = index;
            index = str.indexOf(separator, index + 1);
            if (index == -1) {
                list.add(str.substring(lastIndex + 1, str.length()));
            }
        }
        return list;
    }

    /**
     * lpad函数，含义是对字符串使用指定字符进行在左侧填充
     *
     * @param str    被填充的字符串
     * @param length 字符的长度
     * @param pad    可选参数，这个字符串是要粘贴到str的左边，如果这个参数未写，rpad函数将会在string的右边粘贴空格。
     * @return
     */
    public static String lpad(String str, int length, String pad) {
        return appendPad(str, length, pad, false);
    }

    /**
     * lpad函数，含义是对字符串使用指定字符进行在右侧填充
     *
     * @param str    被填充的字符串
     * @param length 字符的长度
     * @param pad    可选参数，这个字符串是要粘贴到str的右边，如果这个参数未写，rpad函数将会在string的右边粘贴空格。
     * @return
     */
    public static String rpad(String str, int length, String pad) {
        return appendPad(str, length, pad, true);
    }

    /**
     * lpad函数，含义是对字符串使用指定字符进行在右侧填充
     *
     * @param str    被填充的字符串
     * @param length 字符的长度
     * @param pad    可选参数，这个字符串是要粘贴到str的右边，如果这个参数未写，rpad函数将会在string的右边粘贴空格。
     * @param right  是否添加至右侧，true：右侧 ，false：左侧
     * @return
     */
    private static String appendPad(String str, int length, String pad, boolean right) {
        StringBuilder builder = new StringBuilder();
        if (right) {
            builder.append(str);
        }
        //添加pad的数量
        int num = right ? length : length - str.length();
        if (StringUtils.isNotEmpty(pad)) {
            while (builder.length() < num) {
                for (int i = 0; i < pad.length(); i++) {
                    builder.append(pad.charAt(i));
                    if(builder.length() >= num){
                        break;
                    }
                }
            }
        }
        if (!right) {
            builder.append(str);
        }
        return builder.toString();
    }


    /**
     * 首字母大写
     *
     * @param srcStr
     * @return
     */
    public static String firstCharacterToUpper(String srcStr) {
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    /**
     * 去掉最后一位
     *
     * @param srcStr
     * @return
     */
    public static String cutLast(String srcStr) {
        return srcStr.substring(0, srcStr.length() - 1);
    }

    /**
     * 替换字符串并让它的下一个字母为大写
     *
     * @param srcStr
     * @param org
     * @param ob
     * @return
     */
    public static String replaceUnderlineAndfirstToUpper(String srcStr,
                                                         String org, String ob) {
        String newString = "";
        int first = 0;
        while (srcStr.indexOf(org) != -1) {
            first = srcStr.indexOf(org);
            if (first != srcStr.length()) {
                newString = newString + srcStr.substring(0, first) + ob;
                srcStr = srcStr
                        .substring(first + org.length(), srcStr.length());
                srcStr = firstCharacterToUpper(srcStr);
            }
        }
        newString = newString + srcStr;
        return newString;
    }

    public static String stringCut(String aString, int aLen, String aHintStr) {
        if (aString == null) return aString;
        int lLen = aString.length(), i;
        for (i = 0; aLen >= 0 && i < lLen; ++i)
            if (isBigChar(aString.charAt(i))) aLen -= 2;
            else --aLen;
        if (aLen >= 0) return aString;
        if (aHintStr == null) return aString.substring(0, i - 1);

        aLen -= aHintStr.length();
        for (; aLen < 0 && --i >= 0; )
            if (isBigChar(aString.charAt(i))) aLen += 2;
            else ++aLen;

        return aHintStr == null ? aString.substring(0, i) : aString
                .substring(0, i) + aHintStr;
    }

    public static boolean startsWith(String str, String prefix) {
        if (str == null || prefix == null) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length());
        return lcStr.equals(prefix);
    }

    public static boolean isBigChar(char c) {
        return c < 0 || c > 256;
    }

    public static String trim(String arg) {
        return arg == null ? null : arg.trim();
    }

    /**
     * 浮点数运算,小数位4位
     *
     * @param v1
     * @param v2
     * @param type 运算类型：+：add,-:sub, /:divide, *:multiply
     * @return
     */
    public static double comDouble(Double v1, Double v2, char type) {
        return comDouble(v1, v2, type, 4);
    }

    /**
     * 浮点数运算, 四舍五入
     *
     * @param v1
     * @param v2
     * @param type  运算类型：+：add,-:sub, /:divide, *:multiply
     * @param scale 小数点位数
     * @return
     */
    public static double comDouble(Double v1, Double v2, char type, int scale) {
        BigDecimal ret = new BigDecimal("0.0");
        if (v1 != null) {
            ret = ret.add(new BigDecimal(v1.toString()));
        }
        if (v2 != null) {
            switch (type) {
                case '+':
                    ret = ret.add(new BigDecimal(v2.toString()));
                    break;
                case '-':
                    ret = ret.subtract(new BigDecimal(v2.toString()));
                    break;
                case '/':
                    ret = ret.divide(new BigDecimal(v2.toString()), 10, BigDecimal.ROUND_HALF_EVEN);
                    break;
                case '*':
                    ret = ret.multiply(new BigDecimal(v2.toString()));
                    break;
                default:
                    break;
            }
        }
        return ret.setScale(scale, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }


    /**
     * 将相同属性的值从源对象复制到目标对象上,源对象上字段为空则保留目标对象上的值
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        for (Field f : sourceFields) {
            try {
                String fieldName = f.getName();
                Field field = sourceClass.getDeclaredField(fieldName);
                Class type = field.getType();
                String head = "";
                if (type.getName().contains("boolean")) {
                    head = "is";
                } else {
                    head = "get";
                }
                String sourceMethodName = head + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method sourceMethod = sourceClass.getMethod(sourceMethodName, null);
                Object fieldValue = sourceMethod.invoke(source, null);
                if (fieldValue != null) {
                    String targetMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method targetMethod = targetClass.getMethod(targetMethodName, fieldValue.getClass());
                    targetMethod.invoke(target, fieldValue);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
//			} catch (NoSuchFieldException e) {
                System.out.println("目标对象无 " + f.getName() + "属性");
//				e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.out.println("不能访问非公开属性");
//				e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(lpad("a", 7, "0123"));
        System.out.println(rpad("a", 7, "0123"));
        String d = "==--==";
        System.out.println(toString(d.getBytes()));
        System.out.println(comDouble(8D, 3D, '/'));
        System.out.println(fakeString("李四", false));
        System.out.println(fakeString("1231231", true));
        System.out.println(fakeString("好吃的大饼", true));
        System.out.println(fakeString("好吃的大饼V5", false));
        System.out.println(fakeString("12311", true));
        System.out.println(fakeString("123211", false));
        System.out.println(fakeString("1231133", true));
        System.out.println(fakeString("12312311", false));
        System.out.println(fakeString("123123111", false));
        System.out.println(fakeString("1231232111", true));
        System.out.println(fakeString("1231231231231111", false));
        System.out.println(fakeString("12312312312111", true));
    }

    /**
     * 判断src中是否包含args中的任何一个
     *
     * @param src
     * @param args
     * @return
     */
    public static boolean contains(String src, String... args) {
        if (args.length == 0)
            return false;

        for (String arg : args) {
            if (contain(src, arg)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contain(String src, String arg) {
        if (src == null) {
            return arg == null;
        }
        return arg != null ? src.contains(arg) : false;
    }

    /**
     * 字符串顺序反转
     *
     * @param str, 会执行trim
     * @return
     */
    public static String reverseString(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        str = str.trim();
        int strl = str.length();
        if (strl > 1) {
            StringBuffer sb = new StringBuffer(str);
            return sb.reverse().toString();
        }
        return str;
    }

    /**
     * <pre>
     * 格式化数值字符串，将科学记数法的数据转换成字符串，
     * 并进行四舍五入，如果传入字符串为空则返回空
     * </pre>
     *
     * @param v     待格式化的字符串
     * @param scale 精度
     * @return String 格式化后数字字符串
     */
    public static String roundStr(String v, int scale) {
        if (StringUtils.isNumeric(v)) {
            return "";
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(v);
        b = b.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return b.toPlainString();
    }

    /**
     * <pre>
     * 格式化数值，将科学记数法的数据转换成，
     * 并进行四舍五入，如果传入字符串为空则返回空
     * </pre>
     *
     * @param v     待格式化的字符串
     * @param scale 精度
     * @return double 格式化后数字
     */
    public static double setScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(v);
        b = b.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return b.doubleValue();
    }

    /**
     * 对string进行加密
     * <pre>
     * 1.位数<=7位,则后一半加密
     * 2.位数>8位,前3后3不加密
     * 可限制最大的的*号数量，如果限制，最多的*号数量是4个
     * </pre>
     * eg：23465789456123 => 123****123
     * eg: 12312 => 12***
     *
     * @param str          需要加密的字符串
     * @param limitFakeNum 是否限制最大的的*号数量，如果限制，最多的*号数量是4个
     */
    public static String fakeString(String str, boolean limitFakeNum) {
        if (isEmpty(str)) return str;
        int fakedNum = 0, maxFakeNum = 4, strLength = str.length(), showNum = 3;
        boolean isShort = strLength / 2 <= showNum;

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            if (isShort) {//位数<=7位,则后一半加密
                if (i < strLength / 2) {
                    strBuilder.append(str.charAt(i));
                } else {
                    strBuilder.append('*');
                }
                continue;
            }
            if (!isShort && (i < showNum || strLength - i <= showNum)) {//位数>8位,前3后3不加密
                strBuilder.append(str.charAt(i));
                continue;
            }

            if (!limitFakeNum || fakedNum < maxFakeNum) {//超过最大显示的*号，则不再显示
                ++fakedNum;
                strBuilder.append('*');
            }
        }
        return strBuilder.toString();
    }

    /**
     * 拼装方法中参数
     *
     * @param args
     * @return
     */
    public static <T> String getStringArgs(T... args) {
        StringBuilder str = new StringBuilder();
        for (T arg : args) {
            str.append(toString(arg));
            str.append(",");
        }
        return str.toString();
    }

    /**
     * 在指定的字符串固定长度插入指定分隔符，每3位一段，用空格分隔开的字符串
     * <p>
     * eg:
     * input:
     * data      = 1234567890
     * sectionLen = 3
     * separator = -
     * return:123-456-789-0
     *
     * @param data
     * @param sectionLen
     * @param separator
     * @return
     */
    public static String splitByFix(String data, int sectionLen, String separator) {
        if (isEmpty(data)) return data;

        int secs = (data.length() + sectionLen - 1) / sectionLen;

        int x = 0;
        StringBuilder strBuilder = new StringBuilder(data);
        for (int i = 1; i < secs; i++) {
            int idx = i * sectionLen + x;

            strBuilder.insert(idx, separator);

            x += separator.length();
        }
        return strBuilder.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 驼峰转下划线,效率比上面高
     *
     * @param str
     * @return
     */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
