package com.example.android.kabaddiscorekeeper;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {
    int scoreA = 0,
            scoreB = 0,
            noOfPlayersA = 7,
            noOfPlayersB = 7,
            playerCount = 7,
            emptyA = 0,
            emptyB = 0,
            matchTime = 20,
            superTackle = 3;
    boolean timeRun = true,
            matchTimeLeft = true,
            teamARaid = true;

    CountDownTimer timeCountDownTimer = new CountDownTimer(30000, 1000) {

        public void onTick(long millisUntilFinished) {
            timeRun = false;
            NumberFormat f = new DecimalFormat("00");
            TextView timeView = (TextView) findViewById(R.id.time);
            timeView.setText("" + f.format(millisUntilFinished / 1000));
        }

        public void onFinish() {
            TextView timeView = (TextView) findViewById(R.id.time);
            timeView.setText("00");
            timeRun = true;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void timeKeeper() {
        if(timeRun) {
            timeCountDownTimer.start();
        }
        else{
            timeCountDownTimer.cancel();
            timeRun = true ;
        }
        if(matchTimeLeft && matchTime != 0 ){
            CountDownTimer matchTimeCountDown = new CountDownTimer(matchTime*60000 , 1000) {

                public void onTick(long millisUntilFinished) {
                    matchTimeLeft = false;

                    NumberFormat f = new DecimalFormat("00");
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;

                    TextView matchTime = (TextView) findViewById(R.id.matchTime);
                    matchTime.setText("Match Time Left : " + f.format(min) + ":" + f.format(sec));
                }

                public void onFinish() {
                    TextView matchTime = (TextView) findViewById(R.id.matchTime);
                    matchTime.setText("00:00");
                    matchTimeLeft = true;
                }
            };
            matchTimeCountDown.start();
            }
        }

    /**
     * to display team A score
     *
     * @param score
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * to display team B score
     *
     * @param score
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /*
    * to indicate no of players inside game currently
     */
    public void incrementPlayersA(int x) {
        noOfPlayersA += x;
        noOfPlayersB -= x;
        if (noOfPlayersA > playerCount) {
            noOfPlayersA = playerCount;
        }
        if (noOfPlayersB <= 0) {
            noOfPlayersB = playerCount;
            scoreA += 2 ;
            displayForTeamA(scoreA);
            Toast.makeText(getApplicationContext(), " Team B All Out  ", Toast.LENGTH_SHORT).show();
        }
        setPlayerInColorA();
        setPlayerInColorB();
    }

    public void incrementPlayersB(int x) {
        noOfPlayersB += x;
        noOfPlayersA -= x;
        if (noOfPlayersB > playerCount) {
            noOfPlayersB = playerCount;
        }
        if (noOfPlayersA <= 0) {
            noOfPlayersA = playerCount;
            scoreB +=2 ;
            displayForTeamB(scoreB);
            Toast.makeText(getApplicationContext(), " Team A All Out ", Toast.LENGTH_SHORT).show();
        }
        setPlayerInColorA();
        setPlayerInColorB();
    }

/**
 *  Indicates which team has to raid
 */
public void raidTeamAFalse () {
    teamARaid = false ;
    TextView raidView = (TextView) findViewById(R.id.raid);
    raidView.setText("B - Raid");
}
    public void raidTeamATrue () {
        teamARaid = true ;
        TextView raidView = (TextView) findViewById(R.id.raid);
        raidView.setText("A - Raid");
    }
    /**
     * team A score increment
     *
     * @param View
     */
    public void increase1a(View View) {
        scoreA = scoreA + 1;
        displayForTeamA(scoreA);
        timeKeeper();
        if(!teamARaid && noOfPlayersA <= superTackle){
            scoreA += 1;
            displayForTeamA(scoreA);
            Toast.makeText(getApplicationContext(), " Team A Super Tackle", Toast.LENGTH_SHORT).show();
            raidTeamATrue();
            incrementPlayersA(1);
            return;
        }
        incrementPlayersA(1);
        if(teamARaid){
            emptyA = 0 ;
            raidTeamAFalse();
        }else raidTeamATrue();
    }

    public void increaseCommonA(int x){
        if(noOfPlayersB >= x  && teamARaid){
            scoreA = scoreA + x;
            displayForTeamA(scoreA);
            incrementPlayersA(x);
            emptyA = 0 ;
            raidTeamAFalse ();
            timeKeeper();
        }
    }

    public void increase2a(View View) {
        increaseCommonA(2);
      }

    public void increase3a(View View) {
        increaseCommonA(3);
    }

    public void increase4a(View View) {
        increaseCommonA(4);
    }

    public void increase5a(View View) {
        increaseCommonA(5);
    }

    public void increase6a(View View) {
        increaseCommonA(6);
    }

    public void increase7a(View View) {
        increaseCommonA(7);
    }

    public void bonusA(View View) {
        scoreA = scoreA + 1;
        emptyA = 0 ;
        displayForTeamA(scoreA);
    }

    public void emptyRaidA(View View){
        if(teamARaid){
        emptyA += 1 ;
        if(emptyA == 2){
            Toast.makeText(getApplicationContext(), "Team A : Next Raid - DO or DIE", Toast.LENGTH_LONG).show();
            emptyA = 0 ;
        }
        raidTeamAFalse ();
    }}
    /**
     * team B score increment
     *
     * @param View
     */
    public void increase1b(View View) {
        scoreB = scoreB + 1;
        displayForTeamB(scoreB);
        timeKeeper();
        if(teamARaid && noOfPlayersB <= superTackle){
            scoreB += 1;
            displayForTeamB(scoreB);
            Toast.makeText(getApplicationContext(), " Team B Super Tackle", Toast.LENGTH_SHORT).show();
            raidTeamAFalse();
            incrementPlayersB(1);
            return;
        }
        incrementPlayersB(1);
        if(!teamARaid){
            emptyB = 0 ;
            raidTeamATrue();
        }else raidTeamAFalse();
    }

    public void increaseCommonB(int x){
        if(noOfPlayersA >= x && !teamARaid){
            scoreB = scoreB + x;
            displayForTeamB(scoreB);
            incrementPlayersB(x);
            emptyB = 0 ;
            raidTeamATrue();
            timeKeeper();
        }
    }

    public void increase2b(View View) {
        increaseCommonB(2);
      }

    public void increase3b(View View) {
        increaseCommonB(3);
    }

    public void increase4b(View View) {
        increaseCommonB(4);
    }

    public void increase5b(View View) {
        increaseCommonB(5);
    }

    public void increase6b(View View) {
        increaseCommonB(6);
    }

    public void increase7b(View View) {
        increaseCommonB(7);
    }

    public void bonusB(View View) {
        scoreB = scoreB + 1;
        emptyB = 0 ;
        displayForTeamB(scoreB);
    }

    public void emptyRaidB(View View){
        if(!teamARaid){
        emptyB += 1 ;
        if(emptyB == 2){
            Toast.makeText(getApplicationContext(), "Team B : Next Raid - DO or DIE", Toast.LENGTH_LONG).show();
            emptyB = 0 ;
        }
        raidTeamATrue();
    }}

    /**
     * To RESET the scores
     *
     * @param View
     */
    public void reset(View View) {
        scoreA = 0 ;
        scoreB = 0 ;
        emptyB = 0 ;
        emptyA = 0 ;
        raidTeamATrue();
        displayForTeamA(scoreA);
        displayForTeamB(scoreB);
        noOfPlayersA = playerCount;
        noOfPlayersB = playerCount;
        setPlayerInColorA();
        setPlayerInColorB();
        timeCountDownTimer.cancel();
       //atchTimeCountDown.cancel();
        TextView matchTime = (TextView) findViewById(R.id.matchTime);
        matchTime.setText("00:00");
        TextView timeView = (TextView) findViewById(R.id.time);
        timeView.setText("00");
        matchTimeLeft = true;
        timeRun = true;

    }


    /**
     * To indicate the players currently in the team
     */

    public void setPlayerInColorA() {
        switch (noOfPlayersA) {
            case 1:
                View view = findViewById(R.id.a1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a2);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a3);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a4);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a5);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a7);
                view.setBackgroundColor(RED);
                break;
            case 2:
                view = findViewById(R.id.a1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a3);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a4);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a5);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a7);
                view.setBackgroundColor(RED);
                break;
            case 3:
                view = findViewById(R.id.a1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a4);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a5);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a7);
                view.setBackgroundColor(RED);
                break;
            case 4:
                view = findViewById(R.id.a1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a4);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a5);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a7);
                view.setBackgroundColor(RED);
                break;
            case 5:
                view = findViewById(R.id.a1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a4);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a5);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.a7);
                view.setBackgroundColor(RED);
                break;
            case 6:
                view = findViewById(R.id.a1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a4);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a5);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a6);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a7);
                view.setBackgroundColor(RED);
                break;
            case 7:
                view = findViewById(R.id.a1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a4);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a5);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a6);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.a7);
                view.setBackgroundColor(GREEN);
                break;
            default:
                break;

        }
    }

    public void setPlayerInColorB() {
        switch (noOfPlayersB) {
            case 1:
                View view = findViewById(R.id.b1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b2);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b3);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b4);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b5);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b7);
                view.setBackgroundColor(RED);
                break;
            case 2:
                view = findViewById(R.id.b1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b3);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b4);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b5);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b7);
                view.setBackgroundColor(RED);
                break;
            case 3:
                view = findViewById(R.id.b1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b4);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b5);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b7);
                view.setBackgroundColor(RED);
                break;
            case 4:
                view = findViewById(R.id.b1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b4);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b5);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b7);
                view.setBackgroundColor(RED);
                break;
            case 5:
                view = findViewById(R.id.b1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b4);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b5);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b6);
                view.setBackgroundColor(RED);
                view = findViewById(R.id.b7);
                view.setBackgroundColor(RED);
                break;
            case 6:
                view = findViewById(R.id.b1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b4);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b5);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b6);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b7);
                view.setBackgroundColor(RED);
                break;
            case 7:
                view = findViewById(R.id.b1);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b2);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b3);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b4);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b5);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b6);
                view.setBackgroundColor(GREEN);
                view = findViewById(R.id.b7);
                view.setBackgroundColor(GREEN);
                break;
            default:
                break;

        }
    }

    /**
     * No of players
     * @param playerCount
     */
    private void displayPlayers(int playerCount) {
        TextView playerCountTextView = (TextView) findViewById(
                R.id.playerCount_text_view);
        playerCountTextView.setText("" + playerCount);
    }


    public void increment(View View) {
        playerCount += 1;
        if (playerCount > 7) playerCount = 7;
        displayPlayers(playerCount);
    }

    public void decrement(View View) {
        playerCount -= 1;
        if (playerCount < 2) playerCount = 2;
        displayPlayers(playerCount);
    }

    /**
     * sets Super Tackle limit
     * @param superTackle
     */
    private void displaySuperTackle(int superTackle) {
        TextView superTackleTextView = (TextView) findViewById(
                R.id.superTackle_text_view);
        superTackleTextView.setText("" + superTackle);
    }


    public void incrementSuperTackle(View View) {
        superTackle += 1;
        if (superTackle > 7) superTackle = 7;
        displaySuperTackle(superTackle);
    }

    public void decrementSuperTackle(View View) {
        superTackle -= 1;
        if (superTackle < 0) superTackle = 0;
        displaySuperTackle(superTackle);
    }

    /**
     * to SET Match Time
     * @param matchTime
     */
    private void displayMatchTime(int matchTime) {
        TextView MatchTimeTextView = (TextView) findViewById(
                R.id.setMatchTime_text_view);
        MatchTimeTextView.setText("" + matchTime);
    }


    public void incrementMatchTime(View View) {
        matchTime += 5;

        if (matchTime > 60) matchTime = 60;
        displayMatchTime(matchTime);
    }

    public void decrementMatchTime(View View) {
        matchTime -= 5;

        if (matchTime < 0) matchTime = 0;
        displayMatchTime(matchTime);
    }



}

