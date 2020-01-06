package app.com.firebaseauth.utils;

import android.widget.EditText;

public class Form {
    public static void clear(EditText editText) {
        if(editText.getText().toString() != null) {
            editText.clearFocus();
            editText.setText("");
        }
    }
}
