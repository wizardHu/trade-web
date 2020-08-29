package com.wizard.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MACDUtil {

    private static Map<String, List<Float>> macdMap = new HashMap<>();
    private static Map<String, List<Float>> emaMap = new HashMap<>();
    private static Map<String, List<Float>> difMap = new HashMap<>();
    private static Map<String, List<Float>> deaMap = new HashMap<>();

    public static void add(String symbol,float price){

        List<Float> emaList12 = emaMap.get(symbol+"-12");
        List<Float> emaList26 = emaMap.get(symbol+"-26");
        List<Float> difList = difMap.get(symbol);
        List<Float> deaList = deaMap.get(symbol);
        List<Float> macdList = macdMap.get(symbol);

        if(CollectionUtils.isEmpty(emaList12)){//首次初始化

            emaList12 = new ArrayList<>();
            emaList26 = new ArrayList<>();
            difList = new ArrayList<>();
            deaList = new ArrayList<>();
            macdList = new ArrayList<>();

            emaList12.add(price);
            emaList26.add(price);
            deaList.add(0f);
        }else {

           float ema12 = 2.0f / (12.0f + 1.0f) * price + 11.0f / (12.0f + 1.0f) * emaList12.get(emaList12.size()-1);
           float ema26 = 2.0f/(26.0f+1.0f) * price + 25.0f/(26.0f+1.0f) * emaList26.get(emaList26.size()-1);
           emaList12.add(ema12);
           emaList26.add(ema26);
        }

        float dif = emaList12.get(emaList12.size()-1) - emaList26.get(emaList26.size()-1);
        difList.add(dif);

        float dea = deaList.get(deaList.size()-1)*8.0f/10.0f+dif*2.0f/10.0f;
        deaList.add(dea);

        float macd = dif - dea;
        macdList.add(macd);

        if(emaList12.size() > 10){
            emaList12 = emaList12.subList(emaList12.size()-10,emaList12.size());
            emaList26 = emaList26.subList(emaList26.size()-10,emaList26.size());
            difList = difList.subList(difList.size()-10,difList.size());
            deaList = deaList.subList(deaList.size()-10,deaList.size());
            macdList = macdList.subList(macdList.size()-10,macdList.size());
        }


        emaMap.put(symbol+"-12",emaList12);
        emaMap.put(symbol+"-26",emaList26);
        difMap.put(symbol,difList);
        deaMap.put(symbol,deaList);
        macdMap.put(symbol,macdList);

    }

    public static boolean check(String symbol,float price){

        List<Float> difList = difMap.get(symbol);
        List<Float> deaList = deaMap.get(symbol);
        List<Float> macdList = macdMap.get(symbol);

        if(difList == null && difList.size() < 5)
            return false;

        float difLast3 = difList.get(difList.size()-3);
        float difLast2 = difList.get(difList.size()-2);
        float difNow = difList.get(difList.size()-1);

        float deaLast3 = deaList.get(deaList.size()-3);
        float deaLast2 = deaList.get(deaList.size()-3);
        float deaNow = deaList.get(deaList.size()-3);

        float gap = difNow*-1.0f / price;

        if(difLast3 < deaLast3 && difLast2 < deaLast2 && difNow > deaNow && difNow < 0 && gap > 0.011f){
            return true;
        }


        return false;
    }


    public static void main(String[] args) {

        add("aa", 1.0f);
        add("aa", 2.0f);
        add("aa", 3.0f);
        add("aa", 4.0f);
        add("aa", 5.0f);
        add("aa", 3.0f);
        add("aa", 6.0f);
        add("aa", 7.0f);
        add("aa", 8.0f);
        add("aa", 9.0f);
        add("aa", 10.0f);


        System.out.println(emaMap);
        System.out.println(difMap);
        System.out.println(deaMap);
        System.out.println(macdMap);
        System.out.println(check("aa", 12));

    }

}
