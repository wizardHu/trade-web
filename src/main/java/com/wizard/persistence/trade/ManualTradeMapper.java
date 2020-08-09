package com.wizard.persistence.trade;

import com.wizard.model.ManualTradeBean;
import com.wizard.model.from.ManualTradeAdd;
import com.wizard.model.from.ManualTradeUpdate;

import java.util.List;

public interface ManualTradeMapper {

    int insertRecord(ManualTradeAdd manualTradeAdd);

    List<ManualTradeBean> getAll();

    int modManualTrade(ManualTradeUpdate manualTradeUpdate);

    int delById(Integer id);

    ManualTradeBean getById(Integer id);

}
