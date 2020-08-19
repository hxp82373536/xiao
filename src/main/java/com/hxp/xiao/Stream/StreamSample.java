package com.hxp.xiao.Stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: hxp
 * @Date: 2020/8/19 15:05
 * @Describe: java8 Stream的用法示例
 */
public class StreamSample {

    Map<String, String> map = new HashMap<String, String>(){
        {
            put("1", "1");
            put("2", "2");
        }
    };

    /**
     * map使用stream,根据key取value(根据value取key也同理)
     * 1.流中间操作链：
     * filter表示过滤
     * map表示对值进行再加工，映射成新的元素，例如返回value+"aaa"
     * distinct表示去重
     * limit表示限制流长度
     * skip表示丢掉前n个元素，通常配合limit使用
     * 2.findFirst返回的是Optional，需要配合Optional使用
     * 3.双冒号::表示方法引用，是一种函数式接口的另一种书写方式，此处表示调用map.entry的getvalue方法返回流
     * @param key
     * @return
     */
    private String getMapValue(String key){
        //map没有转化为stream的方法，但是map的key,value和entryset都可以
        //Set<Map.Entry<String, String>> entries = map.entrySet();
        //Set<String> keySet = map.keySet();
        //Collection<String> values = map.values();
        return map.entrySet().stream().filter(e -> key.equals(e.getKey())).distinct().map(Map.Entry::getValue).findFirst().orElse("");
    }

    /**
     * map使用stream,根据key取value,返回多个值的list
     * collect是将流转化为集合的方法
     * 可以以下自定义要转的类型stream.collect(Collectors.toCollection(LinkedList::new))
     * @param key
     * @return
     */
    private  List<String> getMapValueList(String key){
        return map.entrySet().stream().filter(e -> key.equals(e.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    /**
     * map使用stream,根据key取value,使用for-each打印全部的值，也可以在foreach中做其他操作
     * @param key
     */
    private void getMapValuebyForeach(String key){
        map.entrySet().stream().filter(e -> key.equals(e.getKey())).map(Map.Entry::getValue).forEach(i -> System.out.println("i is even"+i));
    }

}
