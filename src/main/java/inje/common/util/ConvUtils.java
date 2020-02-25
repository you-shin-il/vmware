package inje.common.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import inje.common.exception.BusinessException;
import inje.common.exception.ExceptionVO;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.AttributedString;
import java.util.*;

/**
 * 컨버트 관련 유틸
 * @author  유기목
 * @since    2015. 9. 7.
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *      수정일          수정자                        수정내용
 *  ----------------     ----------      --------------------------------------------
 *   2015. 9. 7.       유기목        최초 생성
 *  </pre>
 */
public class ConvUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvUtils.class);

    /**
     * MapList를 클래스 List로 변환.
     * @param mapList
     * @param obj
     * @return
     * @author 유기목
     */
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getMapListConvObjectList(List mapList, Class obj) {
        List classList = new ArrayList();
        
        try {
            Field[] fields = getReadableDirectFields(obj.getClass());

            for (int i = 0; i < mapList.size(); i++) {
                Object classTmp = obj.newInstance();

                Map maps = (HashMap) mapList.get(i);

                if (maps != null) {
                    for (int f = 0; f < fields.length; ++f) {
                        fields[f].setAccessible(true);

                        Set keys = maps.keySet();
                        Iterator vals = keys.iterator();

                        for (int j = 0; j < keys.size(); j++) {
                            String key = (String) vals.next();

                            if (fields[f].getName().toLowerCase().equals(key.toLowerCase())) {
                                fields[f].set(classTmp, getNullToEmpty(maps.get(key)));
                            }
                        }
                    }
                }
                classList.add(classTmp);
            }
        }
        catch (InstantiationException e) {
            LOGGER.info("InstantiationException: 필드값 할당(입력)에 실패했습니다.");
        }
        catch (ClassCastException e) {
            LOGGER.info("ClassCastException: 클래스 타입 변환에 실패했습니다.");
        }
        catch (IllegalAccessException e) {
            LOGGER.info("IllegalAccessException: 필드값 할당(입력)에 실패했습니다.");  
        }
        catch (IllegalArgumentException e) {
            LOGGER.info("IllegalArgumentException: 필드값 할당(입력)에 실패했습니다.");
        }

        return classList;
    }

    /**
     * null String을 ""(공백) String으로 변환.
     * @param text
     * @return String
     * @author 유기목
     */
    public static String getNullToEmpty(Object text) {
        String rtn;

        rtn = "";
        if (text == null) {
            return rtn;
        }
        else if (String.valueOf(text).toLowerCase().trim().equals("null")) {
            return rtn;
        }
        else {
            return String.valueOf(text);
        }
    }

    /**
     * 객체를 HashMap으로 변환.
     * @param obj
     * @return java.util.HashMap
     * @author 유기목
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static HashMap getObjectConvMap(Object obj) throws NullPointerException, IllegalArgumentException  {
        Field[] fields = getReadableDirectFields(obj.getClass());
        
        HashMap clzInfo = new HashMap();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);

                if (fields[i].get(obj) != null) {
                    clzInfo.put(fields[i].getName(), fields[i].get(obj));
                }
            } catch (NullPointerException ne) {
                LOGGER.info("NullPointerException: 에러가 발생했습니다.");
            } catch (IllegalArgumentException e) {
                LOGGER.info("IllegalArgumentException: 객체 변환(HashMap)에 실패했습니다.");
            } catch (IllegalAccessException e) {
                LOGGER.info("IllegalAccessException: 객체 변환(HashMap)에 실패했습니다.");
            }
        }

        return clzInfo;
    }

    /**
     * Class를 조사하여 Field를 정방향으로 필드배열에 담도록한다.
     * @param clazz
     * @return
     * @author 유기목
     */
    @SuppressWarnings("rawtypes")
    public static Field[] getReadableDirectFields(Class clazz) {
        Field[] fields01 = getReadableFields(clazz);

        boolean reverse = false;
        Field[] aFields = AttributedString.class.getDeclaredFields();
        if(aFields.length < Integer.MAX_VALUE){
            for (int i = 0; i < aFields.length; i++) {
                if (aFields[i].getName().equals("text")) {
                    reverse = i > 3;
                    break;
                }
            }
        }

        Field[] fields = new Field[fields01.length];
        
        if (reverse && fields01.length < Integer.MAX_VALUE) {
            for (int i = 0; i < fields01.length; i++) {
                fields[i] = fields01[(fields01.length - 1) - i];
            }
        }
        else {
            fields = fields01;
        }
        

        return fields;
    }

    /**
     * Class를 조사하여 Field를  필드배열에 담도록한다.
     * @param clazz
     * @return
     * @author 유기목
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Field[] getReadableFields(Class clazz) {
        Field fields[] = clazz.getDeclaredFields();
        if(fields.length > 0)
        {
            Map methodsMap = new HashMap();
            Method methods[] = clazz.getDeclaredMethods();
            for(int i = 0; i < methods.length; i++)
                if(methods[i].getName().startsWith("get"))
                    methodsMap.put(methods[i].getName().substring(3).toLowerCase(), methods[i].getReturnType());

            List readbleFieldList = new ArrayList();
            for(int i = 0; i < fields.length; i++)
                if(methodsMap.get(fields[i].getName().toLowerCase()) != null && methodsMap.get(fields[i].getName().toLowerCase()) == fields[i].getType())
                    readbleFieldList.add(fields[i]);

            Field readableFields[] = new Field[readbleFieldList.size()];
            for(int i = 0; i < readableFields.length; i++)
                readableFields[i] = (Field)readbleFieldList.get(i);

            return readableFields;
        } else
        {
            return new Field[0];
        }
    }

    /**
     * 객체를 다른 객체로 변환.
     * @param oriObj
     * @param convObj
     * @return
     * @author 유기목
     */
    @SuppressWarnings("rawtypes")
    public static Object getObjectConvObject(Class oriObj, Class convObj) {
        Object classTmp = null;
        try {
            classTmp = convObj.newInstance();

            Field[] convFields = getReadableDirectFields(convObj.getClass());
            Field[] oriFields = getReadableDirectFields(oriObj.getClass());

            for(int f = 0; f < convFields.length; ++f) {
                convFields[f].setAccessible(true);

                for(int j = 0; j < oriFields.length; j++) {
                    if (convFields[f].getName().toLowerCase().equals(oriFields[j].getName().toLowerCase())) {
                        convFields[f].set(classTmp, getNullToEmpty(oriFields[j].get(oriObj)));
                    }
                }
            }
        } catch (InstantiationException e) {
            LOGGER.info("InstantiationException: 객체 변환에 실패했습니다.");
        } catch (IllegalAccessException e) {
            LOGGER.info("IllegalAccessException: 객체 변환에 실패했습니다.");
        } catch (IllegalArgumentException e) {
            LOGGER.info("IllegalArgumentException: 객체 변환에 실패했습니다.");
        }
        
        return classTmp;
    }

    /**
     * 맵을 쿼리 스트링으로 변환
     * @param map
     * @return
     */
    public static String getMapToString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('&');
            }

            String value = map.get(key);

            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
                stringBuilder.append('=');
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();
    }
    
    /**
     * Object를 받아 스트링 배열로 반환
     * @param obj
     * @return
     */
    public static String[] getStringArrayToObject(Object obj) {
        String[] result = null;
        
        if(obj instanceof String[]) {
            result = (String[])obj;
        } else if (obj instanceof String) {
            result = new String[]{(String)obj};
        }

        return result;
    }    

    /**
     * 쿼리 스트링을 맵형식으로 변환
     * @param input
     * @return
     */
    public static Map<String, String> getStringToMap(String input) {
        Map<String, String> map = new HashMap<String, String>();

        String[] nameValuePairs = input.split("&");
        for (String nameValuePair : nameValuePairs) {
            String[] nameValue = nameValuePair.split("=");
            try {
                map.put(URLDecoder.decode(nameValue[0], "UTF-8"), nameValue.length > 1 ? URLDecoder.decode(
                nameValue[1], "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return map;
    }
    
    /**
     * 리퀘스트 파라메터 맵에 넘어온 값중 배열 1에 해당하는 것은 스트링으로 2이상은 배열 그대로 반환
     * @param request
     * @return
     */
    public static Map<String, Object> getConvParamMap(HttpServletRequest request) {
        Map<String, String[]> requestParams = request.getParameterMap(); 
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Iterator<String> it = requestParams.keySet().iterator();
        
        while (it.hasNext()) {
            String paramName = it.next();
            String[] paramValue = requestParams.get(paramName);
            
            if (paramValue != null) {
                if(paramValue.length > 1) {
                    paramMap.put(paramName, paramValue); 
                } else {
                    paramMap.put(paramName, paramValue[0]);
                }
            }
        }
        return paramMap;
    }
    
    /**
     * 리퀘스트 파라메터에서 키값에 해당하는 값을 스트링으로 반환
     * @param request
     * @param paramName
     * @return
     */
    public static String getConvParam(HttpServletRequest request, String paramName) {
        return getConvParam(request, paramName, null);
    }

    /**
     * 리퀘스트 파라메터에서  키값에 해당하는 값을 스트링으로 반환 스트링 배열 시 첫번째 값 반환
     * @param request
     * @param paramName
     * @param defaultValue
     * @return
     */
    public static String getConvParam(HttpServletRequest request, String paramName, String defaultValue) {
        Map<String, String[]> paramMap = request.getParameterMap();
        
        Object paramValue = paramMap.get(paramName);
        String retValue = null;
    
        if (paramValue != null) {
            if (String[].class.isAssignableFrom(paramValue.getClass()))
                retValue = ((String[])paramValue)[0];
            else {
                retValue = (String)paramValue;
            }
        }
        
        return ((!(StringUtils.hasText(retValue))) ? defaultValue : retValue);
    }
    
    /**
     * 리퀘스트 파라메터에서  키값에 해당하는 값을 스트링으로 반환 스트링 배열 반환
     * @param request
     * @param paramName
     * @return
     */
    public static String[] getConvParamArray(HttpServletRequest request, String paramName) {
        Map<String, String[]> paramMap = request.getParameterMap();
        Object value = paramMap.get(paramName);

        if (value != null) {
            if(String[].class.isAssignableFrom(value.getClass()))   {
                return (String[]) value;
            } else {
                return new String[]{(String)value};
            }
        }
        
        return null;
    }    
    
    /**
     * 리퀘스트 파라메터 값 그대로 Map<String, Object>로 반환 
     * @param request
     * @return
     */
    public static Map<String, Object> getConvParam(HttpServletRequest request)   {
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(request.getParameterMap());
        return params;
    }

    /**
     * 리퀘스트 파라메터 값을 적용하고자 하는 클래스 타입으로 변환하여 반환
     * @param request
     * @param type
     * @return
     */
    public static <T> T getConvParam(HttpServletRequest request, Class<T> type) {
        return getConvParam(request, type, null);
    }

    /**
     * 리퀘스트 파라메터 값을 적용하고자 하는 클래스 타입으로 변환하여 반환 (필터 적용가능)
     * @param request
     * @param type
     * @param filter
     * @return
     */
    public static <T> T getConvParam(HttpServletRequest request, Class<T> type, String filter) throws BusinessException {
        
        T object = BeanUtils.instantiate(type);
        Field[] fields = type.getDeclaredFields();

        for(Field field : fields){
            Object value = null;

            if(String[].class.isAssignableFrom(field.getType())){
                value = getConvParamArray(request, field.getName());
            } else{
                value = getConvParam(request, field.getName());
            }

            if(StringUtils.hasText(filter)) {
                if(!isSelectedProperty(filter, field.getName()))    {
                    continue;
                }

                if(isRequiredProperty(filter, field.getName())) {
                    if(value == null || (value instanceof String && !StringUtils.hasText((String) value)))  {
                        throw new BusinessException("Column[" + field.getName() + "] 는 요청[" + filter + "] 에 의해 필수값으로 설정되었습니다.");
                    }
                }
            }

            field.setAccessible(true);

            try {
                if (value != null) {
                    field.set(object, value);
                }
            } catch (NullPointerException ne) {
                LOGGER.info("NullPointerException: 에러가 발생했습니다.");
            } catch (IllegalArgumentException e) {
                //throw new BusinessException("ConvUtils.getConvParam - " + e.getMessage(), e);
                LOGGER.info("IllegalArgumentException: 에러가 발생했습니다.");
            } catch (IllegalAccessException e) {
                //throw new BusinessException("ConvUtils.getConvParam - " + e.getMessage(), e);
                LOGGER.info("IllegalAccessException: 에러가 발생했습니다.");
            }
        }

        return object;
    }
    
    /**
     * 리퀘스트 파라메터 값을 적용하고자 하는 클래스 타입으로 리스트 변환하여 반환
     * @param request
     * @param type
     * @return
     */
    public static <T> List<T> getConvParamList(HttpServletRequest request, Class<T> type) {
        return getConvParamList(request, type, null);
    }

    /**
     * 리퀘스트 파라메터 값을 적용하고자 하는 클래스 타입으로 리스트 변환하여 반환 (필터 적용가능)
     * @param request
     * @param type
     * @param filter
     * @return
     */
    @SuppressWarnings("unused")
    public static <T> List<T> getConvParamList(HttpServletRequest request, Class<T> type, String filter) throws BusinessException {
        
        List<T> resutlList = new ArrayList<T>();
        Field[] fields = type.getDeclaredFields();
        
        int listSize = 0;

        for(Field field : fields){
            String[] value = getConvParamArray(request, field.getName());
            
            if(value != null) {
                if(StringUtils.hasText(filter)) {
                    if(!isSelectedProperty(filter, field.getName()))    {
                        continue;
                    }
                    
                    if(isRequiredProperty(filter, field.getName())) {
                        if(value == null)  {
                            throw new BusinessException("Column[" + field.getName() + "] 는 요청[" + filter + "] 에 의해 필수값으로 설정되었습니다.");
                        }
                    }
                }
                
                listSize = value.length;
                break;
            }
        }
        
        for(int i=0; i<listSize; i++) {
            resutlList.add(BeanUtils.instantiate(type));
        }
        
        for(Field field : fields){
            String[] value = getConvParamArray(request, field.getName());

            if(StringUtils.hasText(filter)) {
                if(!isSelectedProperty(filter, field.getName()))    {
                    continue;
                }

                if(isRequiredProperty(filter, field.getName())) {
                    if(value == null)  {
                        throw new BusinessException("Column[" + field.getName() + "] 는 요청[" + filter + "] 에 의해 필수값으로 설정되었습니다.");
                    }
                }
            }

            field.setAccessible(true);

            try {
                if (value != null) {
                    if(listSize != value.length) {
                        throw new BusinessException("요청에서 넘어온 데이터 셋의 사이즈가 상의합니다.");
                    } else {
                        for(int i=0; i<listSize; i++) {
                            field.set(resutlList.get(i), value[i]);
                        }
                    }
                }
            } catch (NullPointerException ne) {
                LOGGER.info("NullPointerException 에러가 발생했습니다.");
            } catch (IllegalArgumentException e) {
                //throw new BusinessException("ConvUtils.getConvParam - " + e.getMessage(), e);
                LOGGER.info("IllegalArgumentException: 에러가 발생했습니다.");
            } catch (IllegalAccessException e) {
                //throw new BusinessException("ConvUtils.getConvParam - " + e.getMessage(), e);
                LOGGER.info("IllegalAccessException: 에러가 발생했습니다.");
            }
        }

        return resutlList;
    }

    /**
     * 선택된 프로퍼티 인지 유무
     * @param expr
     * @param property
     * @return
     */
    protected static boolean isSelectedProperty(String expr, String property)  {
        boolean result = true;

        if(expr != null)    {
            result = expr.contains(property);
        }

        return result;
    }

    /**
     * 필수 프로퍼티인지 유무
     * @param expr
     * @param property
     * @return
     */
    protected static boolean isRequiredProperty(String expr, String property)  {
        return expr.contains(String.format("%s+", property));
    }
    
    /**
     * ResultSet의 결과값을 맵 형식으로 형변환
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public static Map<String, Object> getResultSetConvMap(ResultSet resultSet) throws SQLException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        int columnCount = resultSet.getMetaData().getColumnCount();
        Object[] rowData;

        if (columnCount < 2) {
            throw new SQLException(
                    "resultSetToHashMap: At least two columns needed for conversion.");
        }
        
        String[] columnNms = getColumnNames(resultSet.getMetaData());

        while (resultSet.next()) {
            rowData = getRowData(resultSet, columnCount);
            for(int i=0; i<columnCount; i++) {
                resultMap.put(columnNms[i], rowData[i]);
            }
        }
        return resultMap;
    }
    
    /**
     * ResultSetMetaData 메타 정보에서 컬럼명을 스트링 배열로 반환
     * @param resultSetMetaData
     * @return
     * @throws SQLException
     */
    public static String[] getColumnNames(ResultSetMetaData resultSetMetaData) throws SQLException {
        int columnCount = resultSetMetaData.getColumnCount();
        
        //secure coding (배열의 크기 값이 음수인지 체크)
        if (columnCount < 0) {
            throw new NegativeArraySizeException(
                    "Integer Overflow or Wraparound: 배열의 크기가 음수입니다.");
        }
        
        String columnNames[] = new String[columnCount];

        for (int colIndex = 1; colIndex <= columnCount; colIndex++) {
            columnNames[colIndex - 1] = resultSetMetaData.getColumnName(colIndex);
        }

        return columnNames;
    }
    
    /**
     * ResultSet에서 데이터 값을 오브젝트 배열로 반환
     * @param resultSet
     * @param columnCount
     * @return
     * @throws SQLException
     */
    private static Object[] getRowData(ResultSet resultSet, int columnCount) throws SQLException {
        //secure coding (배열의 크기 값이 음수인지 체크)
        if(columnCount < 0) {
            throw new NegativeArraySizeException(
                    "Integer Overflow or Wraparound: 배열의 크기가 음수입니다.");
        }
            
        Object rowData[] = new String[columnCount];

        for (int colIndex = 1; colIndex <= columnCount; colIndex++) {
            rowData[colIndex - 1] = resultSet.getObject(colIndex);
        }
        
        return rowData;
    }
    
    /**
     * Json String Object 변환
     * @param data
     * @param type
     * @return
     * @author 유기목
     */
    public static <T> T getJsonStringConvObject(String data, Class<T> type) {
        T resultObj = BeanUtils.instantiate(type);
        
        if(data != null && !data.equals("")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                resultObj =mapper.readValue(data, type);
            }
            catch (JsonMappingException e) {
                if(!(resultObj instanceof ExceptionVO)) {
                    if(LOGGER.isInfoEnabled()) {
                        LOGGER.info("JsonMappingException: 객체 변환에 실패했습니다. to " + type );
                        LOGGER.info("JsonMappingException data : " + data);
                    }
                }
            }
            catch (JsonParseException e) {
                if(!(resultObj instanceof ExceptionVO)) {
                    if(LOGGER.isInfoEnabled()) {
                        LOGGER.info("JsonParseException: 객체 변환에 실패했습니다. to " + type);
                        LOGGER.info(data);
                    }
                }
            }
            catch (JsonGenerationException e) {
                if(!(resultObj instanceof ExceptionVO)) {
                    LOGGER.info("JsonGenerationException: 객체 변환에 실패했습니다."); 
                }
            }
            catch (IOException e) {
                if(!(resultObj instanceof ExceptionVO)) {
                    LOGGER.info("IOException: 객체 변환에 실패했습니다.");
                }
            }
        }/* else {
            if("HashMap".equals(((Class) type).getSimpleName())) { 
                resultObj = (T) new HashMap<String,Object>();
                LOGGER.debug(((Class) type).getSimpleName());
            }
        }*/
        
        return resultObj;
    }
    
    /**
     * Object Json String 변환
     * @param obj
     * @return
     * @author 유기목
     */
    public static String getObjectConvJsonString(Object obj) {
        String result = "";
        
        if(obj != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(Feature.INDENT_OUTPUT, true); // 이쁘게 변환
                StringWriter sw = new StringWriter();
                mapper.writeValue(sw, obj);
                result = sw.toString();
            }
            catch (JsonMappingException e) {
                LOGGER.info("JsonMappingException: JSON String 변환에 실패했습니다.");
            }
            catch (JsonParseException e) {
                LOGGER.info("JsonParseException: JSON String 변환에 실패했습니다.");
            }
            catch (JsonGenerationException e) {
                LOGGER.info("JsonGenerationException: JSON String 변환에 실패했습니다.");
            }
            catch (IOException e) {
                LOGGER.info("IOException: JSON String 변환에 실패했습니다.");
            }
        }
        
        return result;
    }  
    
    /**
     * Json String Map 변환 후 객체 변환 시 해당 메소드 이용
     * @param obj
     * @param type
     * @return
     */
    public static <T> T getMapConvObject(Object obj, Class<T> type) {
        T resultObj = BeanUtils.instantiate(type);
        
        if(obj != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                resultObj =mapper.readValue(mapper.writeValueAsString(obj), type);
            }
            catch (JsonMappingException e) {
                LOGGER.info("JsonMappingException: 객체 변환에 실패했습니다.");
            }
            catch (JsonParseException e) {
                LOGGER.info("JsonParseException: 객체 변환에 실패했습니다.");
            }
            catch (JsonGenerationException e) {
                LOGGER.info("JsonGenerationException: 객체 변환에 실패했습니다.");
            }
            catch (IOException e) {
                LOGGER.info("IOException: 객체 변환에 실패했습니다.");
            }
        }
        
        return resultObj;
    }
    /**
     * Json String JsonNode 변환
     * @param data
     * @return
     */
    public static JsonNode getJsonStringConvJsonNode(String data) {
        JsonNode node = null;
        
        if(data != null && !data.equals("")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                node =mapper.readValue(data, JsonNode.class);
            } catch (JsonMappingException e) {
                LOGGER.info("JsonMappingException: JsonNode 변환에 실패했습니다.");
            } catch (JsonParseException e) {
                LOGGER.info("JsonParseException: JsonNode 변환에 실패했습니다.");
            } catch (JsonGenerationException e) {
                LOGGER.info("JsonGenerationException: JsonNode 변환에 실패했습니다.");
            } catch (IOException e) {
                LOGGER.info("IOException: JsonNode 변환에 실패했습니다.");
            }
        }
        
        return node;
    }
    
    /**
     * Yaml to Json 변환
     * @param yamlString
     * @return
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> convertYamlToJson(String yamlString) {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper(new YAMLFactory());
        HashMap<String, Object> jsonMap = null;

        try {
            jsonMap = mapper.readValue(yamlString, HashMap.class);
            //jsonMap.remove("outputs");
        } catch (IOException e) {
            LOGGER.debug("Yaml to Json 변환 예외 발생");
            //LOGGER.info(e.getMessage());
        }

        return jsonMap;
    }
    
    /**
     * Json to Yaml 변환
     * @param jsonString
     * @return
     */
    public static String convertJsonToYaml(String jsonString) {
        String jsonAsYaml = null;

        try {
            //            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            //            com.fasterxml.jackson.databind.JsonNode jsonNode = mapper.readTree(jsonString);
            //            jsonAsYaml = new YAMLMapper().writeValueAsString( jsonNode );

            //            heat_template_version: '2015-04-30' 부분이 컨버팅 오류나므로 위 로직으로 수정
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode jsonNode = mapper.readTree(jsonString);
            String heat_template_version = inje.common.util.StringUtils.nullConvert(jsonNode.get("heat_template_version").asText());
            for(Iterator<String> it = jsonNode.fieldNames(); it.hasNext();) {
                String key = it.next();
                if("heat_template_version".equals(key)) {
                    it.remove();
                    break;
                }
            }
            if(!StringUtils.isEmpty(heat_template_version)) {
                com.fasterxml.jackson.databind.JsonNode tempNode = mapper.createObjectNode();
                ((ObjectNode)tempNode).put("heat_template_version", heat_template_version);
                ((ObjectNode)tempNode).setAll(((ObjectNode)jsonNode));
                jsonNode = tempNode;
            }
            jsonAsYaml = new YAMLMapper().configure(com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature.INDENT_ARRAYS, true).writeValueAsString( jsonNode );
        } catch (IOException e) {
            LOGGER.info("Json to Yaml 입출력 예외 발생");
        }
        return jsonAsYaml;
    }

	/**
	 * BigInteger 데이터를 Integer로 변환하는 유틸
	 * @param value
	 * @param limit
	 * @return
	 */
	public static Integer getIntegerValue(Object value, BigInteger limit) {
		Integer result = 0;
		if(limit == null) {
			limit = new BigInteger("100000");
		}
		if(value instanceof BigInteger) {
			BigInteger temp = (BigInteger) value;
			result = temp.compareTo(limit) == 1 ? limit.intValue() : temp.intValue();
		} else {
			result = (Integer) value;
		}
		return result;
	}

    /**
     * request 내용을 json 문자열로 변환하는 유틸
     * @param request
     * @return
     */
    public static String readJSONStringFromRequestBody(HttpServletRequest request) {
        StringBuffer json = new StringBuffer();
        String line = null;

        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }

        }catch(Exception e) {
            System.out.println("Error reading JSON string: " + e.toString());
        }
        return json.toString();
    }

    public static Map<String, Object> describe(Object target) {
        try {
            HashMap<String, Object> describe = (HashMap<String, Object>)org.apache.commons.beanutils.BeanUtils.describe(target);
            return describe;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new HashMap<String, Object>();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return new HashMap<String, Object>();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return new HashMap<String, Object>();
        }
    }
}