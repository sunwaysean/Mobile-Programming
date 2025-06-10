package com.example.quizylecture;

import java.io.Serializable;

public class Question implements Serializable {
    private String questionText;
    private String[] options;
    private int correctIndex;
    private String explanation;
    private Integer userAnswerIndex = null;

    public Question(String questionText, String[] options, int correctIndex, String explanation) {
        this.questionText = questionText;
        this.options = options;
        this.correctIndex = correctIndex;
        this.explanation = explanation;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public String getExplanation() {
        return explanation;
    }

    public Integer getUserAnswerIndex() {
        return userAnswerIndex;
    }

    public void setUserAnswerIndex(int index) {
        this.userAnswerIndex = index;
    }

    public boolean isAnswered() {
        return userAnswerIndex != null && userAnswerIndex >= 0;
    }

    public boolean isUserCorrect() {
        return isAnswered() && userAnswerIndex == correctIndex;
    }
}
