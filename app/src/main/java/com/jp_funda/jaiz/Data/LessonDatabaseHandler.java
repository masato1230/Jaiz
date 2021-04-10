package com.jp_funda.jaiz.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LessonDatabaseHandler extends SQLiteOpenHelper {
    private static String DB_NAME = "lessons";
    private static String DB_NAME_ASSET = "lessons.sqlite3";
    private static final int DATABASE_VERSION = 1;
    private  Context context;
    private final File mDatabasePath;

    public LessonDatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.mDatabasePath = context.getDatabasePath(DB_NAME);
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
                    checkDb.setVersion(DATABASE_VERSION);
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
        int newVersion = DATABASE_VERSION;

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
}
