package com.example.quizylecture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
    private static final String TAG = "QuizyApp";
    private TextView questionTextView;
    private Button trueButton, falseButton;
    private Question[] questionBank = new Question[]{
            new Question("The sky is blue.", true),
            new Question("2 + 2 equals 5.", false),
            new Question("The earth is flat.", false),
            new Question("Fire is cold.", false),
            new Question("Water boils at 100°C.", true)
    };
    private int currentIndex = 0;

    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        updateQuestion();
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                moveToNextQuestion();
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                moveToNextQuestion();
            }
        });
    }

    private void updateQuestion() {

        questionTextView.setText(questionBank[currentIndex].getQuestionText());
    }

    private void moveToNextQuestion() {
        currentIndex++;
        // If there are more questions, update the UI; else, show results by launching ResultActivity.
        if (currentIndex < questionBank.length) {
            updateQuestion();
        } else {
            // All questions are answered, launch the result activity.
            Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
            resultIntent.putExtra(ResultActivity.EXTRA_SCORE, score);
            resultIntent.putExtra(ResultActivity.EXTRA_TOTAL, questionBank.length);
            startActivity(resultIntent);
            // Optionally, finish this activity if you don't want to allow going back.
            finish();
        }
    }


    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questionBank[currentIndex].isAnswerTrue();
        if (userAnswer == correctAnswer) {
            score++;  // Increment the score for a correct answer.
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Answer was Correct. Score: " + score);
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Answer was Incorrect.");
        }
    }

}
