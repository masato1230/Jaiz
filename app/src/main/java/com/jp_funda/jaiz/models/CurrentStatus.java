package com.jp_funda.jaiz.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CurrentStatus {
    private int problemIndex= 0;
    // 1~4の値
    private int answerInt;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private ArrayList<String> correctlyAnsweredWords;
    private ArrayList<String> incorrectlyAnsweredWords;

    public int getProblemIndex() {
        return problemIndex;
    }

    public void setProblemIndex(int problemIndex) {
        this.problemIndex = problemIndex;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public int getAnswerInt() {
        return answerInt;
    }

    public void setAnswerInt(int answerInt) {
        this.answerInt = answerInt;
    }

    public ArrayList<String> getCorrectlyAnsweredWords() {
        return correctlyAnsweredWords;
    }

    public void setCorrectlyAnsweredWords(ArrayList<String> correctlyAnsweredWords) {
        this.correctlyAnsweredWords = correctlyAnsweredWords;
    }

    public ArrayList<String> getIncorrectlyAnsweredWords() {
        return incorrectlyAnsweredWords;
    }

    public void setIncorrectlyAnsweredWords(ArrayList<String> incorrectlyAnsweredWords) {
        this.incorrectlyAnsweredWords = incorrectlyAnsweredWords;
    }

    public void cleanCurrentStatus() {
        problemIndex = 0;
        answer1 = null;
        answer2 = null;
        answer3 = null;
        answer4 = null;
        correctlyAnsweredWords = null;
        incorrectlyAnsweredWords = null;
    }
}
