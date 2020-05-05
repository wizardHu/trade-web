package com.wizard.persistence.trade;

import com.wizard.model.TransactionConfigModel;
import com.wizard.model.from.TransactionConfigQuery;

import java.util.List;

public interface TransactionConfigMapper {

    List<TransactionConfigModel> getTransactionConfigRecord(TransactionConfigQuery query);

    int getTransactionConfigRecordCount(TransactionConfigQuery query);

}
