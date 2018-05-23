package com.seezoon.framework.common.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.seezoon.framework.common.context.exception.ServiceException;

/**
 * 框架有地方用到了mybatis $ 符号
 * @author hdf
 * 2018年5月23日
 */
public class SQLFilterUtils {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlFilter(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\ -- 字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "--", "");
        //转换成小写
        str = str.toLowerCase();
        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};
        //判断是否包含非法字符
        if (ArrayUtils.contains(keywords, str)) {
        		throw new ServiceException("sql语句有注入风险");
        }
        return str;
    }
}
