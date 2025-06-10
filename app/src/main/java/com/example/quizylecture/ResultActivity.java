package com.example.quizylecture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends Activity {

    private TextView resultSummary;
    private Button restartButton, reviewButton;
    private ArrayList<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultSummary = findViewById(R.id.result_summary);
        restartButton = findViewById(R.id.restart_button);
        reviewButton = findViewById(R.id.review_button);

        Intent intent = getIntent();
        int scorePercent = intent.getIntExtra("scorePercent", 0);
        int answered = intent.getIntExtra("answered", 0);
        int skipped = intent.getIntExtra("skipped", 0);
        int correct = intent.getIntExtra("correct", 0);
        int total = intent.getIntExtra("total", 0);

        questionList = (ArrayList<Question>) intent.getSerializableExtra("questionList");

        resultSummary.setText(
                "Final Score: " + scorePercent + "%\n\n" +
                        "Total Questions: " + total + "\n" +
                        "Answered: " + answered + "\n" +
                        "Skipped: " + skipped + "\n" +
                        "Correct: " + correct
        );

        restartButton.setOnClickListener(v -> {
            Intent restartIntent = new Intent(this, MainActivity.class);
            restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(restartIntent);
            finish();
        });

        reviewButton.setOnClickListener(v -> {
            Intent reviewIntent = new Intent(this, ReviewActivity.class);
            reviewIntent.putExtra("questionList", questionList);
            startActivity(reviewIntent);
        });
    }
}
