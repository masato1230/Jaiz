package com.jp_funda.jaiz.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lesson {
    private int lessonNumber;
    private String lessonName;
    private String lessonNameJP;
    private ArrayList<String> words;
    private ArrayList<String> wordsJP;

    public Lesson() {
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonNameJP() {
        return lessonNameJP;
    }

    public void setLessonNameJP(String lessonNameJP) {
        this.lessonNameJP = lessonNameJP;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<String> getWordsJP() {
        return wordsJP;
    }

    public void setWordsJP(ArrayList<String> wordsJP) {
        this.wordsJP = wordsJP;
    }
}
