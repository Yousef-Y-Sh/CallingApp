package com.example.callingapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class Utils {

    public static String COLLECTION = "Laboratory";
    public static String COLLECTIONComplete = "CompleteAudit";
    public static String COLLECTIONUnComplete = "UnCompleteAudit";
    public static String Data = "data";


//    public static void _bubbleSort(ArrayList<Audit> movies) {
//        for (int a = 1; a < movies.size(); a++) {
//            for (int b = 0; b < movies.size() - a; b++) {
//                if (((movies.get(b).getPercent()) < ((movies.get(b + 1).getPercent())))) {
//                    Collections.swap(movies, b, b + 1);
//                    Log.e("MOH", "SWAP");
//                }
//            }
//        }
//    }

    public static void _Backpress(Activity activity) {
        activity.onBackPressed();
    }

    public static View _ShowDialog(Activity activity, int layout) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        View itemView = LayoutInflater.from(activity).inflate(layout, null);
        builder.setView(itemView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        return itemView;
    }

    public static void _Intent(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        activity.startActivity(intent);
    }

    public static void _Intent(Activity activity, Class aClass, Bundle bundle) {
        Intent intent = new Intent(activity, aClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static void _IntentClearTask(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @SuppressLint("Range")
    public static String _getFileName(Activity activity, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static void _PickImage(Activity activity, int reuestCode) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(i, reuestCode);
    }

    public static void _PickFileFromPhone(Activity activity, String type, int requestcode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        activity.startActivityForResult(intent, requestcode);
    }

    public static void _Toast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void _EmptyText(EditText[] arr) {
        for (EditText editText : arr) {
            editText.setText("");
        }
    }

    public static void _EmptyText(TextView[] arr) {
        for (TextView editText : arr) {
            editText.setText("");
        }
    }

    public static Integer _toInteger(EditText editText) {
        return Integer.parseInt(editText.getText().toString());
    }

    public static Double _double(EditText editText) {
        return Double.parseDouble(editText.getText().toString());
    }

    public static String _getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String _getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static String _getText(Spinner spinner) {
        return spinner.getSelectedItem().toString().trim();
    }

    public static boolean _validateText(EditText editText) {
        if (_getText(editText).equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean _validateText(TextView editText) {
        if (_getText(editText).equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static void _showSnackBar(Activity activity, String message, String action, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, listener);
        snackbar.show();

    }

    public static void _showSnackBar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

//    public static void _loginFirebseWithEmail(Activity activity, String username, String password, Class aClass) {
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    _Intent(activity, aClass);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull @NotNull Exception e) {
//                _Toast(activity, e.getMessage());
//            }
//        });
//    }

}
