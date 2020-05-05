package com.wizard.model.from;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionConfigQuery extends CommonQueryBase{

    private Integer status;

}
