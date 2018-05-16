package com.build.gson.net;

import com.build.entity.Base;
import com.build.config.JsonKey;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTypeAdapter<T extends Base>
    extends TypeAdapter<T>{

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        out.beginObject();
        Method[] methods = value.getClass().getDeclaredMethods();
        for(Method method : methods){
            String methodName = method.getName();
            if( methodName.startsWith("get") ){
                JsonKey jsonKey = method.getAnnotation(JsonKey.class);
                try {
                    /**
                     * list集合解析
                     */
                   if(jsonKey.isCollection() ){
                       List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
                       List<?> list = (List<?>) method.invoke(value);
                       /**
                        * 解析list
                        */
                       String jsonKeyVal = jsonKey.value();
                        for( Object obj : list ){
                            Map<String,Object> fieldClassKeyVal =  new HashMap<String,Object>();
                            Method[] fieldClassMethodArr = obj.getClass().getDeclaredMethods();
                            for( Method  fieldClassMethod : fieldClassMethodArr ){
                                String fieldClassMethodName = fieldClassMethod.getName();
                                JsonKey fieldClassJsonKey = fieldClassMethod.getAnnotation(JsonKey.class);
                                /**
                                 * 取消子级list的解析
                                 */
                                if( fieldClassMethodName.startsWith("get") && !fieldClassJsonKey.isCollection() ) {
                                    Object fieldClassObject =  fieldClassMethod.invoke(obj);
                                    fieldClassKeyVal.put( fieldClassJsonKey.value(),fieldClassObject );//收集数据
                                }
                            }
                            mapList.add( fieldClassKeyVal );
                        }
                        Gson gson = new Gson();
                        out.name(jsonKeyVal).jsonValue(gson.toJson(mapList));
                   }else {
                       Object obj = method.invoke(value);
                       out.name(jsonKey.value()).value(String.valueOf(obj));
                   }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        out.endObject();
    }

    @Override
    public T read(JsonReader in) throws IOException {
        return null;
    }
}
