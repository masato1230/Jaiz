package com.jp_funda.jaiz.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDataBaseHandler extends SQLiteOpenHelper {
    public UserDataBaseHandler(@Nullable Context context) {
        super(context, UserDBConstants.DB_NAME, null, UserDBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_LESSON_TABLE = "CREATE TABLE " + UserDBConstants.TABLE_NAME + "("
                + UserDBConstants.KEY_LESSON_NUMBER + " INTEGER,"
                + UserDBConstants.KEY_ALL_WORDS + " TEXT,"
                + UserDBConstants.KEY_LEARNED_WORDS + " TEXT,"
                + UserDBConstants.KEY_UNLEARNED_WORDS + " TEXT,"
                + UserDBConstants.KEY_NOT_GOOD_WORDS + " TEXT"
                + ")";
        db.execSQL(CREATE_USER_LESSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDBConstants.TABLE_NAME);
        onCreate(db);
    }

    public long addOrUpdate()
}
