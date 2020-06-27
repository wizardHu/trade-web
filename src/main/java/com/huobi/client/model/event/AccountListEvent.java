package com.huobi.client.model.event;

import com.huobi.client.model.Account;

import java.util.List;

/**
 * The account list event information received by request of account list.
 */

public class AccountListEvent {

  private Long timestamp;

  private List<Account> accountList;

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public List<Account> getAccountList() {
    return accountList;
  }

  public void setAccountList(List<Account> accountList) {
    this.accountList = accountList;
  }
}
