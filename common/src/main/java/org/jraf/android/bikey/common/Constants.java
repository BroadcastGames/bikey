/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 * 
 * Copyright (C) 2013 Benoit 'BoD' Lubek (BoD@JRAF.org)
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
package org.jraf.android.bikey.common;

public class Constants {

    public static final String TAG = "bikey/";

    public static final String PREF_UNITS = "PREF_UNITS";
    public static final String PREF_UNITS_METRIC = "PREF_UNITS_METRIC";
    public static final String PREF_UNITS_IMPERIAL = "PREF_UNITS_IMPERIAL";
    public static final String PREF_UNITS_DEFAULT = PREF_UNITS_IMPERIAL;
    public static final String PREF_CATEGORY_HEART_RATE = "PREF_CATEGORY_HEART_RATE";
    public static final String PREF_HEART_RATE_SCAN = "PREF_HEART_RATE_SCAN";
    public static final String PREF_IMPORT = "PREF_IMPORT";
    public static final String PREF_EXPORT = "PREF_EXPORT";
    public static final String SYNC_WITH_GOOGLE_DRIVE = "SYNC_WITH_GOOGLE_DRIVE";

    public static final String PREF_LISTEN_TO_HEADSET_BUTTON = "PREF_LISTEN_TO_HEADSET_BUTTON";
    public static final boolean PREF_LISTEN_TO_HEADSET_BUTTON_DEFAULT = false;

    public static final String PREF_RECORD_CADENCE = "PREF_RECORD_CADENCE";
    public static final boolean PREF_RECORD_CADENCE_DEFAULT = false;

    public static final String PREF_CURRENT_RIDE_URI = "PREF_CURRENT_RIDE_URI";

    public static final String PREF_RIDE_MAP_TYPE = "PREF_RIDE_MAP_TYPE";
    public static final String PREF_RIDE_MAP_TYPE_NORMAL = "PREF_RIDE_MAP_TYPE_NORMAL";
    public static final String PREF_RIDE_MAP_TYPE_SATELLITE = "PREF_RIDE_MAP_TYPE_SATELLITE";
    public static final String PREF_RIDE_MAP_TYPE_TERRAIN = "PREF_RIDE_MAP_TYPE_TERRAIN";

    public static final String PREF_ANDROID_WEAR = "PREF_ANDROID_WEAR";
    public static final boolean PREF_ANDROID_WEAR_DEFAULT = false;
    public static final String PREF_PEBBLE = "PREF_PEBBLE";
    public static final boolean PREF_PEBBLE_DEFAULT = false;
}
