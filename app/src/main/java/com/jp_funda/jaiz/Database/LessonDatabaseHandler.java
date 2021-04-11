package com.jp_funda.jaiz.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jp_funda.jaiz.models.Lesson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class LessonDatabaseHandler extends SQLiteOpenHelper {
    private static String DB_NAME_ASSET = "lessons.sqlite3";
    private  Context context;
    private final File mDatabasePath;

    public LessonDatabaseHandler(@Nullable Context context) {
        super(context, LessonDBConstants.DB_NAME, null, LessonDBConstants.DB_VERSION);
        this.context = context;
        this.mDatabasePath = context.getDatabasePath(LessonDBConstants.DB_NAME);
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabaseExists();

        if (dbExist) {
            // すでにデータベースは作成されている
        } else {
            // このメソッドを呼ぶことで、空のデータベースがアプリのデフォルトシステムパスに作られる
            SQLiteDatabase db = getReadableDatabase();
            db.close();

            try {
                // asset に格納したデータベースをコピーする
                copyDatabaseFromAsset();

                String dbPath = mDatabasePath.getAbsolutePath();
                SQLiteDatabase checkDb = null;
                try {
                    checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
                } catch (SQLiteException e) {
                }

                if (checkDb != null) {
                    checkDb.setVersion(LessonDBConstants.DB_VERSION);
                    checkDb.close();
                }

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * 再コピーを防止するために、すでにデータベースがあるかどうか判定する
     *
     * @return 存在している場合 {@code true}
     */
    private boolean checkDatabaseExists() {
        String dbPath = mDatabasePath.getAbsolutePath();

        SQLiteDatabase checkDb = null;
        try {
            checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // データベースはまだ存在していない
        }

        if (checkDb == null) {
            // データベースはまだ存在していない
            return false;
        }

        int oldVersion = checkDb.getVersion();
        int newVersion = LessonDBConstants.DB_VERSION;

        if (oldVersion == newVersion) {
            // データベースは存在していて最新
            checkDb.close();
            return true;
        }

        // データベースが存在していて最新ではないので削除
        File f = new File(dbPath);
        f.delete();
        return false;
    }

    /**
     * asset に格納したデーだベースをデフォルトのデータベースパスに作成したからのデータベースにコピーする
     */
    private void copyDatabaseFromAsset() throws IOException{

        // asset 内のデータベースファイルにアクセス
        InputStream mInput = context.getAssets().open(DB_NAME_ASSET);

        // デフォルトのデータベースパスに作成した空のDB
        OutputStream mOutput = new FileOutputStream(mDatabasePath);

        // コピー
        byte[] buffer = new byte[1024];
        int size;
        while ((size = mInput.read(buffer)) > 0) {
            mOutput.write(buffer, 0, size);
        }

        // Close the streams
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Lesson getLesson(int lessonNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Lesson lesson = new Lesson();
        Cursor cursor = db.query(
                LessonDBConstants.TABLE_NAME,
                new String[] {LessonDBConstants.KEY_LESSON_NUMBER, LessonDBConstants.KEY_LESSON_NAME, LessonDBConstants.KEY_LESSON_NAME_JP, LessonDBConstants.KEY_WORDS, LessonDBConstants.KEY_WORDS_JP},
                LessonDBConstants.KEY_LESSON_NUMBER + "=?",
                new String[] {String.valueOf(lessonNumber)},
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToNext();
        }
        // lessonNumber
        lesson.setLessonNumber(cursor.getInt(cursor.getColumnIndex(LessonDBConstants.KEY_LESSON_NUMBER)));
        // lessonName
        lesson.setLessonName(cursor.getString(cursor.getColumnIndex(LessonDBConstants.KEY_LESSON_NAME)));
        // lessonNameJP
        lesson.setLessonNameJP(cursor.getString(cursor.getColumnIndex(LessonDBConstants.KEY_LESSON_NAME_JP)));
        // words
        ArrayList<String> words = new ArrayList<>();
        String wordsString = cursor.getString(cursor.getColumnIndex(LessonDBConstants.KEY_WORDS));
        String[] wordsStringSplit = wordsString.split(",");
        for (String word: wordsStringSplit) {
            words.add(word);
        }
        lesson.setWords(words);
        // wordsJP
        ArrayList<String> wordsJP = new ArrayList<>();
        String wordsJPString = cursor.getString(cursor.getColumnIndex(LessonDBConstants.KEY_WORDS_JP));
        String[] wordsJPStringSplit = wordsJPString.split(",");
        for (String wordJP: wordsJPStringSplit) {
            wordsJP.add(wordJP);
        }
        lesson.setWordsJP(wordsJP);
        return lesson;
    }
}
