package com.wizard.persistence.trade;

import com.wizard.model.TransactionConfigModel;
import com.wizard.model.from.TransactionConfigAdd;
import com.wizard.model.from.TransactionConfigQuery;
import com.wizard.model.from.TransactionConfigUpdate;

import java.util.List;

public interface TransactionConfigMapper {

    List<TransactionConfigModel> getTransactionConfigRecord(TransactionConfigQuery query);

    int getTransactionConfigRecordCount(TransactionConfigQuery query);

    int modTransactionConfigModel(TransactionConfigUpdate transactionConfigUpdate);

    int addRecord(TransactionConfigAdd transactionConfigAdd);
}
