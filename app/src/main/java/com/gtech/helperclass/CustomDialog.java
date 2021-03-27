package com.gtech.helperclass;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.gtech.R;


public class CustomDialog extends Dialog {
//    ProgressBar pb;
public static boolean cus_dialog = false;
    public static CustomDialog dialog;
    public CustomDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  setContentView(R.layout.dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public static void customdialog(Context context) {
        dialog = new CustomDialog(context);
        dialog.setContentView(R.layout.dialog);
        cus_dialog = true;
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void custom_dialog_hide() {
        cus_dialog = false;
        dialog.dismiss();
    }



}
