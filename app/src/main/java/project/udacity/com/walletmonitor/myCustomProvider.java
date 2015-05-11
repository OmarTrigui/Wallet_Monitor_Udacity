package project.udacity.com.walletmonitor;

/**
 * Created by omar on 1/13/15.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class myCustomProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.udacity.provider.Project";
    static final String URL = "content://" + PROVIDER_NAME + "/Items";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String ITEM_ID = "_id";
    static final String ITEM = "item";
    static final String DESC = "desc";
    static final String PRICE = "price";
    static final String DT = "dt";


    private static HashMap<String, String> ITEMS_PROJECTION_MAP;

    static final int IT = 1;
    static final int IT_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "Items", IT);
        uriMatcher.addURI(PROVIDER_NAME, "Items/#", IT_ID);
    }

    static SQLiteDatabase db;
    static final String DATABASE_NAME = "MyWallet";
    static final String ITEMS_TABLE_NAME = "MyItems";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + ITEMS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    " item TEXT NOT NULL, " +
                    " desc TEXT NOT NULL, " +
                    " dt TEXT NOT NULL, " +
                    " price INTEGER NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE_NAME);
            onCreate(db);
        }


    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowID = db.insert(ITEMS_TABLE_NAME, "", values);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(ITEMS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case IT:
                qb.setProjectionMap(ITEMS_PROJECTION_MAP);
                break;
            case IT_ID:
                qb.appendWhere(ITEM_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs,
                null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;

        switch (uriMatcher.match(uri)) {
            case IT:
                count = db.delete(ITEMS_TABLE_NAME, selection, selectionArgs);
                break;
            case IT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(ITEMS_TABLE_NAME, ITEM_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count;

        switch (uriMatcher.match(uri)) {
            case IT:
                count = db.update(ITEMS_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case IT_ID:
                count = db.update(ITEMS_TABLE_NAME, values, ITEM_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}