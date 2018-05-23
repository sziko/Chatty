package com.example.nagys.chatty.Classes;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by nagys on 2/27/2018.
 */

public class Functionality {

    /* class used for creating customisable progress hud and error dialogs*/

    public static KProgressHUD createProgressHud(Context context, String label, String detailsLabel) {

       KProgressHUD hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(label)
                .setDetailsLabel(detailsLabel)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(.5f);

        return hud;
    }

    public static void showErrorDialog(String title, String message, Context context) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
