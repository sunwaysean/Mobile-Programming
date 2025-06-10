package com.example.quizylecture;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class ReviewActivity extends Activity {

    private TextView questionText, explanationText;
    private RadioGroup optionsGroup;
    private Button prevButton, nextButton, backButton;

    private ArrayList<Question> questionList;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);  // ✅ now matches your XML

        // Connect views
        questionText = findViewById(R.id.review_question_text);
        explanationText = findViewById(R.id.explanation_text);
        optionsGroup = findViewById(R.id.review_options_group);
        prevButton = findViewById(R.id.review_prev_button);
        nextButton = findViewById(R.id.review_next_button);
        backButton = findViewById(R.id.back_to_result_button);

        // Get data
        questionList = (ArrayList<Question>) getIntent().getSerializableExtra("questionList");

        if (questionList != null && !questionList.isEmpty()) {
            showQuestion();
        }

        // Navigation
        prevButton.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                showQuestion();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentIndex < questionList.size() - 1) {
                currentIndex++;
                showQuestion();
            }
        });

        backButton.setOnClickListener(v -> {
            finish(); // simply finish this activity to return to ResultActivity
        });
    }

    private void showQuestion() {
        Question current = questionList.get(currentIndex);

        questionText.setText("Q" + (currentIndex + 1) + ": " + current.getQuestionText());
        explanationText.setText("Explanation: " + current.getExplanation());

        optionsGroup.removeAllViews();
        String[] options = current.getOptions();

        for (int i = 0; i < options.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(options[i]);
            rb.setId(i);
            rb.setEnabled(false); // disable editing in review mode

            // Color highlight logic
            if (current.getUserAnswerIndex() == null || current.getUserAnswerIndex() == -1) {
                rb.setTextColor(getResources().getColor(android.R.color.darker_gray)); // skipped
            } else if (i == current.getUserAnswerIndex()) {
                if (current.isUserCorrect()) {
                    rb.setTextColor(getResources().getColor(android.R.color.holo_green_dark)); // correct
                } else {
                    rb.setTextColor(getResources().getColor(android.R.color.holo_red_dark)); // incorrect
                }
            }

            optionsGroup.addView(rb);
        }

        // Check selected option if available
        if (current.getUserAnswerIndex() != null && current.getUserAnswerIndex() >= 0) {
            optionsGroup.check(current.getUserAnswerIndex());
        }
    }
}
