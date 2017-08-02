package com.footballer.util;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class DBUtil {

    public static final int DEFAULT_DB_BATCH_SIZE = 1500;
    private static final Map<String, Object> EMPTY_MAP = new HashMap<String, Object>();
    private static Logger logger  = LoggerFactory.getLogger(DBUtil.class);
    
    private DBUtil() { }
    
    /**
     * 
     * @param parameterNameList
     * @param parameterValueList
     * @return
     * @throws InvalidParameterException
     */
    public static Map<String, Object> createParameterMap(
            String[] parameterNameList, Object[] parameterValueList,
            String method) throws InvalidParameterException {
        return createParameterMap(parameterNameList, parameterValueList, true, method);
    }
    
    /**
     * Create a hashmap instance by parameter  
     * @param parameterNameList
     * @param parameterValueList
     * @param throwException
     * @return
     * @throws InvalidParameterException
     */
    private static Map<String, Object> createParameterMap(
            String[] parameterNameList, Object[] parameterValueList, 
            boolean throwException, String method) throws InvalidParameterException {
        Map<String, Object> map = EMPTY_MAP;
        if (parameterNameList != null && parameterValueList != null) {
            if (parameterNameList.length != parameterValueList.length) {
                if (throwException) {
                    throw new InvalidParameterException();    
                }
            }
            map = new HashMap<String, Object>();
            logger.debug("Start to bind parameter for: " + method);
            for (int i = 0; i < parameterNameList.length; i++) {
                String name = parameterNameList[i];
                Object val = parameterValueList[i];
                logger.debug("name=[" + name + "] value=[" + val + "]");
                map.put(name, val);
            }
        } else {
            if (throwException) {
                throw new InvalidParameterException();
            }
        }
        return map;
    }
    /**
     * Don't need to do basic validation for parameters(like null, the same length...)
     * @param parameterNameList
     * @param parameterValueList
     * @return
     */
    public static synchronized Map<String, Object> createParameterMapNotThrowException(
            String[] parameterNameList, Object[] parameterValueList, String method) {
        Map<String, Object> map = EMPTY_MAP;
        try {
            map = createParameterMap(parameterNameList, parameterValueList, false, method);
        } catch (InvalidParameterException e) {
            logger.error("Can't bind parameters by:" + e.getMessage() + " for method:" + method);
            //throw new IllegalArgumentException(e);
        }
        return map;
    }
    
}
