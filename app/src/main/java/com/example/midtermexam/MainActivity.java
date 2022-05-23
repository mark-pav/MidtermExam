package com.example.midtermexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static Account player1bankAccount = new Account("123456789", 510, "Chase", "Player1");
    public static Account player2bankAccount = new Account("987654321", 910, "KeyBank", "Player2");


    //
    public static void updateAccount (int player, Account data){
        if (player == 1) {
            player1bankAccount.balance = data.balance;
        } else if (player ==2){
            player2bankAccount.balance = data.balance;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.bankButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), BankPageActivity.class);
                 intent1.putExtra("player1account", player1bankAccount);
                 intent1.putExtra("player2account", player2bankAccount);

                startActivity(intent1);

            }
        });

        findViewById(R.id.startGameButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainGameActivity.class);

                startActivity(intent);
            }
        });



    }
}