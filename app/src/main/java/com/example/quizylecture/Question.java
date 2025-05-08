package com.example.quizylecture;

public class Question {
    private String questionText;
    private boolean answerTrue;
    public Question(String questionText, boolean answerTrue) {
        this.questionText = questionText;
        this.answerTrue = answerTrue;
    }

    public String getQuestionText() {
        return questionText;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }
}