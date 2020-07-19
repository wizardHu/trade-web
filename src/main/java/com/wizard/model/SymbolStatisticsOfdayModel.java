package com.wizard.model;

import com.wizard.util.DateUtils;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
public class SymbolStatisticsOfdayModel {

    private Long id;

    private String symbol;

    private Float balance;

    private Float upDownRange;

    private Date createTime;

    private String createTimeStr;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        if(createTime != null){
            setCreateTimeStr(DateUtils.formatDefaultDate(createTime));
        }
    }
}
