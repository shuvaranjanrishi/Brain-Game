package com.example.asus.braingame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startBtn,button0,button1,button2,button3,playAgainBtn;
    TextView sumTextView,resultTextView,pointTextView,timerTextView;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswers;

    int score = 0;
    int numberOfQuestions = 0;
    RelativeLayout gameRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startBtnId);
        playAgainBtn = findViewById(R.id.playAgainBtnId);

        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextViewId);
        pointTextView = findViewById(R.id.pointTextView);
        timerTextView = findViewById(R.id.timerTextView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        gameRelativeLayout = findViewById(R.id.gameRelativeLayout);
    }

    //if time is out the play again button will be visible and start play again if click
    public void playAgainBtnMethod(View view){
        //resets all records after click play again button.
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        pointTextView.setText("0/0");
        resultTextView.setText("Result Status");

        playAgainBtn.setVisibility(View.INVISIBLE);

        button0.setClickable(true);
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);

        //generate new question after click play again button
        generateQuestion();

        //call the timer
        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUnitFinished) {
                timerTextView.setText(String.valueOf(millisUnitFinished/1000)+"s");
            }

            @Override
            public void onFinish() {

                float per;
                per=(((float)score/(float)numberOfQuestions)*(float)100);
                String formatedString = new DecimalFormat("##.##").format(per);

                playAgainBtn.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your Score: "+Integer.toString(score)+"/"+Integer.toString(numberOfQuestions)+"\nPercentage: "+formatedString+" %");

                button0.setClickable(false);
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);
            }
        }.start();    }


    public void generateQuestion(){
            Random random = new Random();

            int a = random.nextInt(21);
            int b = random.nextInt(21);

            sumTextView.setText(Integer.toString(a)+" + "+Integer.toString(b));

            locationOfCorrectAnswers = random.nextInt(4);
            answers.clear();
            int incorrectAnswer;

            for(int i=0; i<4; i++) {
                if (i == locationOfCorrectAnswers) {
                    answers.add(a + b);
                } else {
                    incorrectAnswer = random.nextInt(41);
                    while (incorrectAnswer == (a + b)) {
                        incorrectAnswer = random.nextInt(41);
                    }
                    answers.add(incorrectAnswer);
                }
            }

            button0.setText(Integer.toString(answers.get(0)));
            button1.setText(Integer.toString(answers.get(1)));
            button2.setText(Integer.toString(answers.get(2)));
            button3.setText(Integer.toString(answers.get(3)));
        }

    //get the correct answer by clicking on the button and show correct message and increase score.
    public void chooseAnswer(View view) {
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswers))){
            score++;
            resultTextView.setText("Correct!");
        }else{
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        pointTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        generateQuestion();
    }


    public void startBtnMethod(View view){

        startBtn.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);

        playAgainBtnMethod(playAgainBtn);
    }
}
