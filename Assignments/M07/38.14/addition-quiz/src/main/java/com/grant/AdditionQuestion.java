package com.grant;

public class AdditionQuestion {
    int lhs;
    int rhs;

    public AdditionQuestion() {
        lhs = (int) (Math.random() * 101);
        rhs = (int) (Math.random() * 101);
    }

    public int getAnswer() {
        return lhs + rhs;
    }

    public String getQuestion() {
        return lhs + " + " + rhs + " = ";
    }

    public String getResultString(int userAnswer) {
        int answer = getAnswer();
        return userAnswer == answer ? "Correct!" : ("Incorrect. Correct answer: " + answer);
    }
}
