package com.example.quizylecture;
import android.app.Activity;
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
        currentIndex = (currentIndex + 1) % questionBank.length;
        Log.d(TAG, "Current index: " + currentIndex);
        updateQuestion();
        Toast.makeText(this, "Next Question Loading...", Toast.LENGTH_SHORT).show();
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questionBank[currentIndex].isAnswerTrue();
        if (userAnswer == correctAnswer) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Answer was Correct.");
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Answer was Incorrect.");
        }
    }
}