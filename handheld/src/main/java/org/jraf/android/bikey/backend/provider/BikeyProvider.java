/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2013-2015 Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jraf.android.bikey.backend.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jraf.android.bikey.BuildConfig;
import org.jraf.android.bikey.backend.provider.base.BaseContentProvider;
import org.jraf.android.bikey.backend.provider.log.LogColumns;
import org.jraf.android.bikey.backend.provider.ride.RideColumns;

public class BikeyProvider extends BaseContentProvider {
    private static final String TAG = BikeyProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "org.jraf.android.bikey.backend.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_LOG = 0;
    private static final int URI_TYPE_LOG_ID = 1;

    private static final int URI_TYPE_RIDE = 2;
    private static final int URI_TYPE_RIDE_ID = 3;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, LogColumns.TABLE_NAME, URI_TYPE_LOG);
        URI_MATCHER.addURI(AUTHORITY, LogColumns.TABLE_NAME + "/#", URI_TYPE_LOG_ID);
        URI_MATCHER.addURI(AUTHORITY, RideColumns.TABLE_NAME, URI_TYPE_RIDE);
        URI_MATCHER.addURI(AUTHORITY, RideColumns.TABLE_NAME + "/#", URI_TYPE_RIDE_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return BikeySQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_LOG:
                return TYPE_CURSOR_DIR + LogColumns.TABLE_NAME;
            case URI_TYPE_LOG_ID:
                return TYPE_CURSOR_ITEM + LogColumns.TABLE_NAME;

            case URI_TYPE_RIDE:
                return TYPE_CURSOR_DIR + RideColumns.TABLE_NAME;
            case URI_TYPE_RIDE_ID:
                return TYPE_CURSOR_ITEM + RideColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_LOG:
            case URI_TYPE_LOG_ID:
                res.table = LogColumns.TABLE_NAME;
                res.idColumn = LogColumns._ID;
                res.tablesWithJoins = LogColumns.TABLE_NAME;
                if (RideColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + RideColumns.TABLE_NAME + " AS " + LogColumns.PREFIX_RIDE + " ON " + LogColumns.TABLE_NAME + "." + LogColumns.RIDE_ID + "=" + LogColumns.PREFIX_RIDE + "." + RideColumns._ID;
                }
                res.orderBy = LogColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_RIDE:
            case URI_TYPE_RIDE_ID:
                res.table = RideColumns.TABLE_NAME;
                res.idColumn = RideColumns._ID;
                res.tablesWithJoins = RideColumns.TABLE_NAME;
                res.orderBy = RideColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_LOG_ID:
            case URI_TYPE_RIDE_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
