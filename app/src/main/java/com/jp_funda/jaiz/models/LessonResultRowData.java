package com.jp_funda.jaiz.models;

public class LessonResultRowData {
    private String wordJP;
    private String wordEN;
    private boolean isGood;

    public String getWordJP() {
        return wordJP;
    }

    public void setWordJP(String wordJP) {
        this.wordJP = wordJP;
    }

    public String getWordEN() {
        return wordEN;
    }

    public void setWordEN(String wordEN) {
        this.wordEN = wordEN;
    }

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }
}
