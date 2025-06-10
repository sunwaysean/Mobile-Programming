package com.example.quizylecture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {

    private ArrayList<Question> questionList;
    private TextView questionText, skipCounter;
    private RadioGroup optionsGroup;
    private int currentIndex = 0;
    private Button nextButton, prevButton, submitButton, skipButton;
    private int skipsLeft = 2;
    private final int totalQuestions = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.question_text_view);
        optionsGroup = findViewById(R.id.options_group);
        submitButton = findViewById(R.id.submit_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        skipButton = findViewById(R.id.skip_button);
        skipCounter = findViewById(R.id.skip_counter);


        setupQuestionBank();
        showQuestion();  // Show the first question

        submitButton.setOnClickListener(v -> {
            Question current = questionList.get(currentIndex);

            // Already answered? Do nothing
            if (current.isAnswered()) {
                Toast.makeText(this, "You already submitted this answer.", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = optionsGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show();
                return;
            }

            current.setUserAnswerIndex(selectedId);

            if (current.isUserCorrect()) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrect.", Toast.LENGTH_SHORT).show();
            }

            showQuestion(); // Update UI to disable options
            if (isQuizComplete()) {
                goToResult();
            }

        });

        nextButton.setOnClickListener(v -> {
            if (currentIndex < questionList.size() - 1) {
                currentIndex++;
                showQuestion();
            }
        });

        prevButton.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                showQuestion();
            }
        });

        skipButton.setOnClickListener(v -> {
            Question current = questionList.get(currentIndex);
            if (!current.isAnswered() && skipsLeft > 0) {
                current.setUserAnswerIndex(-1); // Mark it as skipped explicitly
                skipsLeft--;

                skipCounter.setText("Skips left: " + skipsLeft);
                skipButton.setText("Skip (" + skipsLeft + " left)");

                if (skipsLeft == 0) {
                    skipButton.setEnabled(false);
                }

                currentIndex = (currentIndex + 1) % questionList.size();
                showQuestion();
            } else {
                Toast.makeText(this, "No skips left or already answered.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // STEP 2: Question bank setup
    private void setupQuestionBank() {
        questionList = new ArrayList<>();

        questionList.add(new Question(
                "What is the sum of the infinite series: 1 − ½ + ¼ − ⅛ + ... ?",
                new String[]{"1", "0", "2", "Does not converge"},
                0,
                "This is a converging geometric series. The sum is a / (1 - r) = 1 / (1 + 0.5) = 2/3 ≈ 1."
        ));

        questionList.add(new Question(
                "Evaluate lim (x → 0) [sin(3x) / x]",
                new String[]{"0", "1", "3", "Undefined"},
                2,
                "Using the standard limit lim x→0 [sin(ax)/x] = a, so this becomes 3."
        ));

        questionList.add(new Question(
                "How many integers between 1 and 1000 are divisible by neither 2 nor 5?",
                new String[]{"400", "500", "300", "600"},
                0,
                "Use the inclusion-exclusion principle. Total - divisible by 2 or 5."
        ));

        questionList.add(new Question(
                "Find the determinant of the matrix: [[2, -1], [4, 3]]",
                new String[]{"10", "-10", "11", "5"},
                0,
                "Det = 2×3 - (-1)×4 = 6 + 4 = 10."
        ));

        questionList.add(new Question(
                "P(A)=0.6, P(B)=0.5, P(A∩B)=0.3. What is P(A ∪ B)?",
                new String[]{"0.8", "1.1", "0.9", "0.7"},
                0,
                "P(A ∪ B) = P(A) + P(B) - P(A ∩ B) = 0.6 + 0.5 - 0.3 = 0.8"
        ));

        questionList.add(new Question(
                "Smallest positive integer x such that 3x ≡ 1 mod 7?",
                new String[]{"1", "2", "3", "5"},
                3,
                "3×5 = 15 mod 7 = 1. So x = 5."
        ));

        questionList.add(new Question(
                "Solve: log₂(x² − 1) = 3",
                new String[]{"x = 4", "x = ±3", "x = 3", "x = 2"},
                1,
                "x² - 1 = 8 → x² = 9 → x = ±3"
        ));

        Collections.shuffle(questionList);
    }

    private void showQuestion() {
        Question current = questionList.get(currentIndex);

        // Set question text
        questionText.setText("Q" + (currentIndex + 1) + ": " + current.getQuestionText());

        // Clear old options
        optionsGroup.removeAllViews();

        // Add new options
        String[] options = current.getOptions();
        for (int i = 0; i < options.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(options[i]);
            rb.setId(i);
            rb.setEnabled(current.getUserAnswerIndex() == null);
            optionsGroup.addView(rb);
        }
// Disable submit button if skipped
        if (current.getUserAnswerIndex() != null && current.getUserAnswerIndex() == -1) {
            submitButton.setEnabled(false); // skipped, disable submit
        } else if (current.getUserAnswerIndex() == null) {
            submitButton.setEnabled(true); // not answered yet, allow submit
        } else {
            submitButton.setEnabled(false); // already answered
        }

        // Pre-select answer if already answered
        if (current.getUserAnswerIndex() != null) {
            optionsGroup.check(current.getUserAnswerIndex());
        }
    }

    private boolean isQuizComplete() {
        for (Question q : questionList) {
            if (q.getUserAnswerIndex() == null) {
                return false;
            }
        }
        return true;
    }



    private void goToResult() {
        int correct = 0, answered = 0, skipped = 0;

        for (Question q : questionList) {
            Integer answer = q.getUserAnswerIndex();
            if (answer == null) {
                // Unanswered — shouldn't happen unless something is broken
                continue;
            } else if (answer == -1) {
                skipped++;
            } else {
                answered++;
                if (q.isUserCorrect()) {
                    correct++;
                }
            }
        }

        int percent = (int) ((correct / (float) totalQuestions) * 100);

        Toast.makeText(this, "Final Score: " + percent + "%", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("questionList", questionList);
        intent.putExtra("scorePercent", percent);
        intent.putExtra("answered", answered);
        intent.putExtra("skipped", skipped);
        intent.putExtra("correct", correct);
        intent.putExtra("total", totalQuestions);
        startActivity(intent);
        finish();
    }
}
