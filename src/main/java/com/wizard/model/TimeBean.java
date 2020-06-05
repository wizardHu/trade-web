package com.wizard.model;

import lombok.Data;

@Data
public class TimeBean {

    private String begin;

    private String end;

    public TimeBean(String begin,String end){
        this.begin = begin;
        this.end = end;
    }

}
