package com.example.midtermexam;

import java.io.Serializable;

public class Account implements Serializable {
    String accountNumber;
    int balance;
    String bankName;
    String customer;

    public Account(String accountNumber, int balance, String bankName, String customer) {
        this.accountNumber = accountNumber;
        if (balance>=0){
            this.balance = balance;
        }
        this.bankName = bankName;
        this.customer = customer;
    }
}
