package com.jp_funda.jaiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jp_funda.jaiz.models.UserLessonStatus;

import java.util.ArrayList;


public class UserDatabaseHandler extends SQLiteOpenHelper {
    public UserDatabaseHandler(@Nullable Context context) {
        super(context, UserDBConstants.DB_NAME, null, UserDBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_LESSON_TABLE = "CREATE TABLE " + UserDBConstants.TABLE_NAME + "("
                + UserDBConstants.KEY_LESSON_NUMBER + " INTEGER PRIMARY KEY,"
                + UserDBConstants.KEY_ALL_WORDS + " TEXT,"
                + UserDBConstants.KEY_LEARNED_WORDS + " TEXT,"
                + UserDBConstants.KEY_unLearned_WORDS + " TEXT,"
                + UserDBConstants.KEY_NOT_GOOD_WORDS + " TEXT"
                + ")";
        db.execSQL(CREATE_USER_LESSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDBConstants.TABLE_NAME);
        onCreate(db);
    }

    public long addOrUpdateLessonStatus(UserLessonStatus lessonStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // lessonNumber
        values.put(UserDBConstants.KEY_LESSON_NUMBER, lessonStatus.getLessonNumber());

        // words
        StringBuilder wordsStringBuilder = new StringBuilder();
        for (String word: lessonStatus.getWords()) {
            wordsStringBuilder.append(word + ",");
            // Delete extra ","
        }
        wordsStringBuilder.setLength(wordsStringBuilder.length()-1);
        values.put(UserDBConstants.KEY_ALL_WORDS, wordsStringBuilder.toString());

        // learnedWords
        StringBuilder learnedWordsStringBuilder = new StringBuilder();
        if (lessonStatus.getLearnedWords() != null) {
            for (String learnedWord: lessonStatus.getLearnedWords()) {
                learnedWordsStringBuilder.append(learnedWord + ",");
                // Delete extra ","
                learnedWordsStringBuilder.setLength(learnedWordsStringBuilder.length()-1);
            }
        }
        values.put(UserDBConstants.KEY_LEARNED_WORDS, learnedWordsStringBuilder.toString());

        // unLearnedWords
        StringBuilder unLearnedWordsStringBuilder = new StringBuilder();
        if (lessonStatus.getUnlearnedWords() != null) {
            for (String unLearnedWord: lessonStatus.getUnlearnedWords()) {
                unLearnedWordsStringBuilder.append(unLearnedWord + ",");
            }
            // Delete extra ","
            unLearnedWordsStringBuilder.setLength(unLearnedWordsStringBuilder.length()-1);
        }

        values.put(UserDBConstants.KEY_unLearned_WORDS, unLearnedWordsStringBuilder.toString());

        // notGoodWords
        StringBuilder notGoodWordsStringBuilder = new StringBuilder();
        if (lessonStatus.getNotGoodWords() != null) {
            for (String notGoodWord: lessonStatus.getNotGoodWords()) {
                notGoodWordsStringBuilder.append(notGoodWord + ",");
            }
            // Delete extra ","
            notGoodWordsStringBuilder.setLength(notGoodWordsStringBuilder.length()-1);
        }
        values.put(UserDBConstants.KEY_NOT_GOOD_WORDS, notGoodWordsStringBuilder.toString());

        // if learned words is null or empty then update learned words with all words
        if (learnedWordsStringBuilder.length() < 1) {
            values.put(UserDBConstants.KEY_unLearned_WORDS, wordsStringBuilder.toString());
        }

        return db.insertWithOnConflict(
                UserDBConstants.TABLE_NAME,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE
        );
    }

    public UserLessonStatus getUserLessonStatus(int lessonNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserLessonStatus lessonStatus = new UserLessonStatus();

        Cursor cursor = db.query(
                UserDBConstants.TABLE_NAME,
                new String[] {UserDBConstants.KEY_unLearned_WORDS, UserDBConstants.KEY_ALL_WORDS, UserDBConstants.KEY_LEARNED_WORDS, UserDBConstants.KEY_unLearned_WORDS, UserDBConstants.KEY_NOT_GOOD_WORDS},
                UserDBConstants.KEY_LESSON_NUMBER + "=?",
                new String[] {String.valueOf(lessonNumber)},
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }
        // lessonNumber
        // lessonStatus.setLessonNumber(cursor.getInt(cursor.getColumnIndex(UserDBConstants.KEY_LESSON_NUMBER)));
        lessonStatus.setLessonNumber(lessonNumber);
        // words
        ArrayList<String> words = new ArrayList<>();
        String wordsString = cursor.getString(cursor.getColumnIndex(UserDBConstants.KEY_ALL_WORDS));
        String[] wordsStringSplit = wordsString.split(",");
        for (String word: wordsStringSplit) {
            words.add(word);
        }
        lessonStatus.setWords(words);
        // learnedWords
        ArrayList<String> learnedWords = new ArrayList<>();
        String learnedWordsString = cursor.getString(cursor.getColumnIndex(UserDBConstants.KEY_LEARNED_WORDS));
        if (!learnedWordsString.equals("") && (learnedWordsString != null)) {
            String[] learnedWordsStringSplit = learnedWordsString.split(",");
            for (String learnedWord: learnedWordsStringSplit) {
                learnedWords.add(learnedWord);
            }
        }
        lessonStatus.setLearnedWords(learnedWords);
        // unLearnedWords
        ArrayList<String> unLearnedWords = new ArrayList<>();
        String unLearnedWordsString = cursor.getString(cursor.getColumnIndex(UserDBConstants.KEY_unLearned_WORDS));
        if (!unLearnedWordsString.equals("") && (unLearnedWordsString != null)) {
            String[] unLearnedWordsStringSplit = unLearnedWordsString.split(",");
            for (String unLearnedWord: unLearnedWordsStringSplit) {
                unLearnedWords.add(unLearnedWord);
            }
        }
        lessonStatus.setUnlearnedWords(unLearnedWords);
        // notGoodWords
        ArrayList<String> notGoodWords = new ArrayList<>();
        String notGoodWordsString = cursor.getString(cursor.getColumnIndex(UserDBConstants.KEY_NOT_GOOD_WORDS));
        if (!notGoodWordsString.equals("") && (notGoodWordsString != null)) {
            String[] notGoodWordsStringSplit = notGoodWordsString.split(",");
            for (String notGoodWord: notGoodWordsStringSplit) {
                notGoodWords.add(notGoodWord);
            }
        }
        lessonStatus.setNotGoodWords(notGoodWords);

        return lessonStatus;
    }
}
