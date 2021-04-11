package com.jp_funda.jaiz.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserLessonStatus {
    private int lessonNumber;
    private ArrayList<String> words;
    private ArrayList<String> learnedWords;
    private ArrayList<String> unlearnedWords;
    private ArrayList<String> notGoodWords;

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<String> getLearnedWords() {
        return learnedWords;
    }

    public void setLearnedWords(ArrayList<String> learnedWords) {
        this.learnedWords = learnedWords;
    }

    public ArrayList<String> getUnlearnedWords() {
        return unlearnedWords;
    }

    public void setUnlearnedWords(ArrayList<String> unlearnedWords) {
        this.unlearnedWords = unlearnedWords;
    }

    public ArrayList<String> getNotGoodWords() {
        return notGoodWords;
    }

    public void setNotGoodWords(ArrayList<String> notGoodWords) {
        this.notGoodWords = notGoodWords;
    }
}
