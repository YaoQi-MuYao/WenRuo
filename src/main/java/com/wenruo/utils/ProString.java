package com.wenruo.utils;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @ClassName ProString
 * @Description 字符串处理工具
 * @Author MuYao
 * @Date 2020/4/24 4:20
 * @Version 1.0
 **/
public class ProString {

    /**系统换行符*/
    public static final String LINE_SEPARATOR =System.getProperty("line.separator");
    /**前端换行*/
    public static final String WEB_LINE_SEPARATOR ="<br>";

    public static final String UNDER_LINE = "_";
    /**
    * @Description: 判断字符串是否为null,不为null则转换为bigdecimal类型
    * @param: [str]
    * @return: java.math.BigDecimal
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:13
    */
    public static BigDecimal getBigDecimal(String str) {
        return str == null ? null : new BigDecimal(str);
    }

    /**
     * 返回首字母大写的字符串
     * @param str
     * @return
     * @Author : jk. create at 2016年5月31日 下午4:43:13
     */
    public static String upperFirst(String str){
        if(str!=null){
            if(str.length()>0){
                return str.substring(0, 1).toUpperCase()+str.substring(1);
            }
        }
        return str;
    }


    /**
     * @Author xiaojun.li
     * @Description  判断是否为null，如果是就用""代替;
     * @Date 2019/12/27 17:22
     * @Param [str]
     * @return java.lang.String
     **/
    public static String getString(String str) {
        return str == null ? "" : str;
    }

    /**
     * @Author xiaojun.li
     * @Description
     * @Date 2020/1/3 15:41
     * @Param [separator, strs]
     * @return java.lang.String
     **/
    public static String getPrintWebUnitString(String... strs) {
        return ProString.join(strs,ProString.WEB_LINE_SEPARATOR);
    }
    /**
     * @Author xiaojun.li
     * @Description  判断是否为null，如果是就用""代替;
     * @Date 2019/12/27 17:23
     * @Param [obj]
     * @return java.lang.String
     **/
    public static String getString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
    * @Description: 举例：userInfo转换为 USER_INFO
    * @param: [str]
    * @return: java.lang.String
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:15
    */
    public static String screamingSnakeCase(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= 65 && a <= 90) {
                stringBuffer.append(UNDER_LINE);
            }
            stringBuffer.append(String.valueOf(a));
        }
        return stringBuffer.toString().toUpperCase();
    }

    /**
     * @Description: 举例：userInfo转换为 user_info
     * @param: [str]
     * @return: java.lang.String
     * @author: yupeng.yang
     * @Date: 2019/3/8 11:15
     */
    public static String snakeCase(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= 65 && a <= 90) {
                stringBuffer.append(UNDER_LINE);
            }
            stringBuffer.append(String.valueOf(a));
        }
        return stringBuffer.toString().toLowerCase();
    }

    /**
    * @Description: 举例:把USER_INFO转化为userInfo
    * @param: [str]
    * @return: java.lang.String
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:15
    */
    public static String camelCase(String str){
        str=str.toLowerCase();
        if(str.contains(UNDER_LINE)){
            int a = str.indexOf(UNDER_LINE);
            str= str.substring(0,a)+str.substring(a+1,a+2).toUpperCase()+str.substring(a+2,str.length());
            str=str.replace(UNDER_LINE, "");
        }
        return str;
    }

    /**
    * @Description: 大驼峰 把USER_INFO转化为UserInfo
    * @param: [str]
    * @return: java.lang.String
    * @author: yupeng.yang
    * @Date: 2019/4/22 15:39
    */
    public static String bigCamelCase(String str){
        str = str.toLowerCase();
        if(str.contains(UNDER_LINE)){
            int a = str.indexOf(UNDER_LINE);
            str= str.substring(0,a)+str.substring(a+1,a+2).toUpperCase()+str.substring(a+2,str.length());
            str= str.replace(UNDER_LINE, "");
        }
        return upperFirstString(str);
    }

    /** 
    * @Description: userInfo转换为 UserInfo
    * @param: [str]
    * @return: java.lang.String 
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:17
    */ 
    public static String upperFirstString(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }
    
    /** 
    * @Description: UserInfo转换为 userInfo
    * @param: [str]
    * @return: java.lang.String 
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:18
    */ 
    public static String lowerFirst(String str){
        return str.substring(0, 1).toLowerCase()+ str.substring(1, str.length());
    }

    /** 
    * @Description: 把数组转换为字符串，使用joinString进行连接
    * @param: [objects, joinString]
    * @return: java.lang.String 
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:19
    */ 
    public static String join(Object[] objects, String joinString) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object str : objects) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append(joinString);
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }


    /**
    * @Description: 判断一个字符串是否为空，如果str为"null"，也返回true
     * ProString.isBlank(null)      = true
     * ProString.isBlank("null")    = true
     * ProString.isBlank("")        = true
     * ProString.isBlank(" ")       = true
     * ProString.isBlank("bob")     = false
     * ProString.isBlank("  bob  ") = false
    * @param: [str]
    * @return: boolean
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:27
    */
    public static boolean isBlank(final String str) {
        return StringUtils.isBlank(str);
    }


    /**
    * @Description:判断一个字符串是否为空
     * ProString.isNotBlank(null)      = false
     * ProString.isNotBlank("")        = false
     * ProString.isNotBlank(" ")       = false
     * ProString.isNotBlank("bob")     = true
     * ProString.isNotBlank("  bob  ") = true
    * @param: [str]
    * @return: boolean
    * @author: yupeng.yang
    * @Date: 2019/3/8 11:28
    */
    public static boolean isNotBlank(final String str) {
        return StringUtils.isNotBlank(str);
    }

}
