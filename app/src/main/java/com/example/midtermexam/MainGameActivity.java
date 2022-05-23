package com.example.midtermexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameActivity extends AppCompatActivity {



    int currentBet = 25;
    static int playerOneBank = 0;
    static int playerTwoBank = 0;

    List<Integer> items;

    int[] diceImages = new int[]{
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6
    };
    int playerOneScore = 0;
    int playerTwoScore = 0;
    int chancesLeftPlayerOne = 3;
    int chancesLeftPlayerTwo = 3;

    Random random = new Random();

    ImageView dice11;
    ImageView dice12;
    ImageView dice13;
    ImageView dice21;
    ImageView dice22;
    ImageView dice23;
    Button rollDicePlayer1;
    Button rollDicePlayer2;
    Button restartButton;

    TextView tvPlayerOneScore;
    TextView tvPlayerTwoScore;

    TextView tvChancesLeftPlayerOne;
    TextView tvChancesLeftPlayerTwo;

    TextView tvPlayerOneBank;
    TextView tvPlayerTwoBank;

    TextView tvWinnerMessage;

    Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        dice11 = findViewById(R.id.dice11);
        dice12 = findViewById(R.id.dice12);
        dice13 = findViewById(R.id.dice13);
        dice21 = findViewById(R.id.dice21);
        dice22 = findViewById(R.id.dice22);
        dice23 = findViewById(R.id.dice23);

        tvPlayerOneScore = findViewById(R.id.player1ScoreTextField);
        tvPlayerTwoScore = findViewById(R.id.player2ScoreTextField);

        tvChancesLeftPlayerOne = findViewById(R.id.player1ChancesTextField);
        tvChancesLeftPlayerTwo = findViewById(R.id.player2ChancesTextField);

        tvPlayerOneBank = findViewById(R.id.bankTextField1);
        tvPlayerOneBank.setText(Integer.toString(playerOneBank));
        tvPlayerTwoBank = findViewById(R.id.bankTextField2);
        tvPlayerTwoBank.setText(Integer.toString(playerTwoBank));

        rollDicePlayer1 = findViewById(R.id.rollDicesPlayer1button);
        rollDicePlayer2 = findViewById(R.id.rollDicesPlayer2button);

        tvWinnerMessage = findViewById(R.id.winnerMessageTextBox);
        restartButton = findViewById(R.id.restartButton);

        spinner = findViewById(R.id.spinner);

        items = new ArrayList<>();
        items.add(10);
        items.add(25);
        items.add(50);
        items.add(100);

        spinner.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentBet = items.get(i);
                checkBetAmount(currentBet, playerOneBank, playerTwoBank);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                checkBetAmount(currentBet, playerOneBank, playerTwoBank);
            }
        });

        rollDicePlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (chancesLeftPlayerOne>0) {
                        rollDice();
                    }
                    if(chancesLeftPlayerOne == 0){
                        rollDicePlayer1.setEnabled(false);
                    }
                    displayWinner(playerOneScore, playerTwoScore, chancesLeftPlayerOne, chancesLeftPlayerTwo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rollDicePlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (chancesLeftPlayerTwo>0) {
                        rollDice1();
                    }
                    if(chancesLeftPlayerTwo == 0) {
                        rollDicePlayer2.setEnabled(false);
                    }
                    displayWinner(playerOneScore, playerTwoScore, chancesLeftPlayerOne, chancesLeftPlayerTwo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });


    }


    private void checkBetAmount(int bet, int balance1, int balance2){
        if (balance1 - bet >= 0 && balance2 - bet >= 0){
            rollDicePlayer1.setEnabled(true);
            rollDicePlayer2.setEnabled(true);
        } else if (balance1 - bet < 0 && balance2 - bet < 0){
            Toast.makeText(this, "Both players don't have enough $ visit the bank", Toast.LENGTH_SHORT).show();
            rollDicePlayer1.setEnabled(false);
            rollDicePlayer2.setEnabled(false);

        }else if (balance1 - bet < 0){
            Toast.makeText(this, "Player 1 doesn't have enough $ visit the bank", Toast.LENGTH_SHORT).show();
            rollDicePlayer1.setEnabled(false);
            rollDicePlayer2.setEnabled(false);

        } else if (balance2 - bet < 0){
            Toast.makeText(this, "Player 2 doesn't have enough $ visit the bank", Toast.LENGTH_SHORT).show();
            rollDicePlayer1.setEnabled(false);
            rollDicePlayer2.setEnabled(false);

        }


    }


    private void displayWinner(int scoreOne, int scoreTwo, int chanceOne, int chanceTwo) {
        if (scoreOne > 34 && scoreTwo > 34) {
            restartButton.setVisibility(View.VISIBLE);
            tvWinnerMessage.setText("We have 2 winners!");
        } else if (scoreOne > 34 && chanceTwo > 0) {
            tvWinnerMessage.setText("So far Player 1 winning");
        } else if (scoreTwo>34 && chanceOne >0){
            tvWinnerMessage.setText("So far Player 2 winning");
        }else if (scoreOne>34 && chanceTwo==0){
            restartButton.setVisibility(View.VISIBLE);
            tvWinnerMessage.setText("Player 1 WON!!!");
            playerOneBank += currentBet;
            playerTwoBank -= currentBet;
            updateBankValue();
        }else if (scoreTwo>34 && chanceOne==0) {
            restartButton.setVisibility(View.VISIBLE);
            tvWinnerMessage.setText("Player 2 WON!!!");
            playerTwoBank += currentBet;
            playerOneBank -= currentBet;
            updateBankValue();
        }else if (chanceOne == 0 & chanceTwo == 0){
            restartButton.setVisibility(View.VISIBLE);
            tvWinnerMessage.setText("No Winner:(");
        }
    }

    private void restart(){
        restartButton.setVisibility(View.VISIBLE);
    }
    private void rollDice(){

        int die11 = random.nextInt(6) + 1;
        playerOneScore += die11;
        int die12 = random.nextInt(6) + 1;
        playerOneScore += die12;
        int die13 = random.nextInt(6) + 1;
        playerOneScore += die13;
        dice11.setImageResource(diceImages[die11 - 1]);
        dice12.setImageResource(diceImages[die12 - 1]);
        dice13.setImageResource(diceImages[die13 - 1]);
        tvPlayerOneScore.setText(String.valueOf(playerOneScore));
        chancesLeftPlayerOne--;
        tvChancesLeftPlayerOne.setText(String.valueOf(chancesLeftPlayerOne));
    }
    private void rollDice1(){
        int die21 = random.nextInt(6) + 1;
        playerTwoScore += die21;
        int die22 = random.nextInt(6) + 1;
        playerTwoScore += die22;
        int die23 = random.nextInt(6) + 1;
        playerTwoScore += die23;
        dice21.setImageResource(diceImages[die21 - 1]);
        dice22.setImageResource(diceImages[die22 - 1]);
        dice23.setImageResource(diceImages[die23 - 1]);
        tvPlayerTwoScore.setText(String.valueOf(playerTwoScore));
        chancesLeftPlayerTwo--;
        tvChancesLeftPlayerTwo.setText(String.valueOf(chancesLeftPlayerTwo));
    }
    private void updateBankValue(){

        tvPlayerOneBank.setText(Integer.toString(playerOneBank));

        tvPlayerTwoBank.setText(Integer.toString(playerTwoBank));
    }


    static void depositToGame(int player, int amount){
        if (player == 1) {
            playerOneBank += amount;
        } else if (player ==2){
            playerTwoBank += amount;
        }
    }

    static void withdrawFromGame (int player){
        if (player == 1) {
            playerOneBank = 0;
        } else if (player ==2){
            playerTwoBank = 0;
        }
    }


}