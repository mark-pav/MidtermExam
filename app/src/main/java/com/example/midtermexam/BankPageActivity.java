package com.example.midtermexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BankPageActivity extends AppCompatActivity {

    int availableFromGame1 = MainGameActivity.playerOneBank;
    int availableFromGame2 = MainGameActivity.playerTwoBank;

    TextView tvAvailableFromGame1;
    TextView tvAvailableFromGame2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_page);

        Account player1 = (Account) getIntent().getSerializableExtra("player1account");
        Account player2 = (Account) getIntent().getSerializableExtra("player2account");

        TextView accountNumber1 = (TextView) findViewById(R.id.player1accNumber);
        accountNumber1.setText(player1.accountNumber);
        TextView balance1 = (TextView) findViewById(R.id.plaer1balance);
        balance1.setText("$" + Double.toString(player1.balance));
        TextView bankName1 = (TextView) findViewById(R.id.plaer1bankName);
        bankName1.setText(player1.bankName);
        TextView customer1Name = (TextView) findViewById(R.id.plaer1CustomerName);
        customer1Name.setText(player1.customer);
        tvAvailableFromGame1 = findViewById(R.id.AvailableFromGameTVPlayer1);
        tvAvailableFromGame1.setText(Integer.toString(availableFromGame1));

        TextView accountNumber2 = (TextView) findViewById(R.id.plaer2accNumber);
        accountNumber2.setText(player2.accountNumber);
        TextView balance2 = (TextView) findViewById(R.id.plaer2balance);
        balance2.setText("$" + Double.toString(player2.balance));
        TextView bankName2 = (TextView) findViewById(R.id.plaer2bankName);
        bankName2.setText(player2.bankName);
        TextView customer2Name = (TextView) findViewById(R.id.plaer2CustomerName);
        customer2Name.setText(player2.customer);
        tvAvailableFromGame2 = findViewById(R.id.AvailableFromGameTVPlayer2);
        tvAvailableFromGame2.setText(Integer.toString(availableFromGame2));

        findViewById(R.id.Player1WithdrawButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdraw(player1, 50);
                MainActivity.updateAccount(1, player1);
                MainGameActivity.depositToGame(1, 50);
                recreate();
            }
        });

        findViewById(R.id.Player1DepositButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deposit(player1, availableFromGame1);
                MainActivity.updateAccount(1, player1);
                MainGameActivity.withdrawFromGame(1);
                recreate();
            }
        });

        findViewById(R.id.Player2WithdrawButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdraw(player2, 50);
                MainActivity.updateAccount(2, player2);
                MainGameActivity.depositToGame(2, 50);
                recreate();
            }
        });

        findViewById(R.id.Player2DepositButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deposit(player2, availableFromGame2);
                MainActivity.updateAccount(2, player2);
                MainGameActivity.withdrawFromGame(2);
                recreate();
            }
        });



    }

    private void deposit (Account account, int amount) {
        if (amount > 0){
            account.balance += amount;
        }
    }

    private void withdraw (Account account, int amount){
        if (account.balance-amount >= 0){
            account.balance = account.balance - amount;
        }
    }
}