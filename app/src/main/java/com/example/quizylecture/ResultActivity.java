package com.example.quizylecture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {

    public static final String EXTRA_SCORE = "com.example.quizylecture.score";
    public static final String EXTRA_TOTAL = "com.example.quizylecture.total";

    private TextView scoreTextView;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreTextView = findViewById(R.id.score_text_view);
        restartButton = findViewById(R.id.restart_button);

        // Get score and total from intent extras
        Intent intent = getIntent();
        int score = intent.getIntExtra(EXTRA_SCORE, 0);
        int total = intent.getIntExtra(EXTRA_TOTAL, 0);

        scoreTextView.setText("Your Score: " + score + "/" + total);

        // Set restart button listener to start MainActivity again
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restartIntent = new Intent(ResultActivity.this, MainActivity.class);
                // Clear the activity stack if you want a fresh start
                restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(restartIntent);
                finish();
            }
        });
    }
}
