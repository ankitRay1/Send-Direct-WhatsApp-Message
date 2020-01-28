package com.ankit.apps.whatsappdirect.New.Utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by ankit on 11/09/18.
 */

public class CallLogHelper {

    public static Cursor getAllCallLogs(ContentResolver cr) {

        String strOrder = android.provider.CallLog.Calls.DATE + " DESC LIMIT 25";
        Uri callUri = Uri.parse("content://call_log/calls");
        Cursor curCallLogs = cr.query(callUri, null, null, null, strOrder);

        return curCallLogs;
    }
}
