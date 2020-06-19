package com.wizard.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author hulujie
 * @description
 * @createTime 2020/6/15
 */
public class CommonUtil {

    public static String getNewPwd(){

        String pwd = DateUtils.dateToString(new Date(),"yyyyMMddmm");

        Calendar cal=Calendar.getInstance();
        int d = cal.get(Calendar.DATE);

        if(d % 2 == 0){
            pwd += "amplee";
        }else {
            pwd += "wizard";
        }

        return pwd;
    }

}
