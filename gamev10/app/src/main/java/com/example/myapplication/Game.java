package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class Game extends AppCompatActivity {

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(9);
    }

    public Karten setCardImage(ImageView image) {
        Karten card = Karten.values()[getRandomNumber()];
        //Log.d("TAG", card.getPath());
        switch (card.getPath()) {
            case "cerberus10.jpg":
                image.setImageResource(R.drawable.cerberus10);
                break;
            case "charmander7.jpg":
                image.setImageResource(R.drawable.charmander7);
                break;
            case "deathwing12.jpg":
                image.setImageResource(R.drawable.deathwing12);
                break;
            case "delfinu10.jpg":
                image.setImageResource(R.drawable.delfinu10);
                break;
            case "fox5.jpg":
                image.setImageResource(R.drawable.fox5);
                break;
            case "gorilla12.jpg":
                image.setImageResource(R.drawable.gorilla12);
                break;
            case "penguin6.jpg":
                image.setImageResource(R.drawable.penguin6);
                break;
            case "rechin12.jpg":
                image.setImageResource(R.drawable.rechin12);
                break;
            case "tiger10.jpg":
                image.setImageResource(R.drawable.tiger10);
                break;
        }
        return card;
    }

    public ArrayList<ImageView> getPlayerDeck() {
        ArrayList<ImageView> playerCards = new ArrayList<ImageView>();

        playerCards.add((ImageView) findViewById(R.id.lowercard3));
        playerCards.add((ImageView) findViewById(R.id.lowercard4));
        playerCards.add((ImageView) findViewById(R.id.lowercard5));
        playerCards.add((ImageView) findViewById(R.id.lowercard6));
        playerCards.add((ImageView) findViewById(R.id.lowercard7));

        return playerCards;
    }

    public void changeScore(ImageView playerCard, ArrayList<Karten> playerAssignedCards, ArrayList<ImageView> playerCards) {
        for (int i = 0; i < playerCards.size(); i++) {
            String s1 = playerCard.getDrawable().toString();
            String s2 = playerCards.get(i).getDrawable().toString();
            if (s1.equals(s2))
                System.out.println(i);
            System.out.println(s1 + "     ==     " + s2);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final ImageView chosenCard = findViewById(R.id.activePlayerCard);
        final ImageView computerCard = findViewById(R.id.activeComputerCard);
        TextView computerScoreView = findViewById(R.id.scoreComputer);
        TextView playerScoreView = findViewById(R.id.scorePlayer);

        int computerScore = Integer.parseInt(computerScoreView.getText().toString());
        int playerScore = Integer.parseInt(computerScoreView.getText().toString());

        final ArrayList<ImageView> playerCards = getPlayerDeck();
        final ArrayList<Karten> playerAssignedCards = new ArrayList<>();

        for (int i = 0; i < playerCards.size(); i++) {
            final ImageView card = playerCards.get(i);
            Karten assignedCard = setCardImage(card);
            playerAssignedCards.add(assignedCard);

            //add onClickListener
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //System.out.println(card.getDrawable());
                    chosenCard.setImageDrawable(card.getDrawable());
                    setCardImage(computerCard);
                    changeScore(chosenCard, playerAssignedCards, playerCards);
                    setCardImage(card);

                }
            });
        }

    }
}