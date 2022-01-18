package com.satyajitghosh.mediclock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

public class InputValidationHandler {

    public static boolean inputValidation(Context context, String medicineName, ArrayList<TIME.AlarmBundle> time) {
        //Toast.makeText(getApplicationContext(), "Required details are empty", Toast.LENGTH_LONG).show();
        return !medicineName.isEmpty() && !time.isEmpty();
    }
    public static void showDialog(Context context){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        // Set the message show for the Alert time
        builder.setMessage("Required fields are empty.");
        // Set Alert Title
        builder.setTitle("Alert !");

        builder
                .setPositiveButton(
                        "OK",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // When the user click yes button
                                // then app will close
                                //    finish();
                                dialog.cancel();
                            }
                        });

//                   // Set the Negative button with No name
//                   // OnClickListener method is use
//                   // of DialogInterface interface.
//                   builder
//                           .setNegativeButton(
//                                   "No",
//                                   new DialogInterface
//                                           .OnClickListener() {
//
//                                       @Override
//                                       public void onClick(DialogInterface dialog,
//                                                           int which)
//                                       {
//
//                                           // If user click no
//                                           // then dialog box is canceled.
//                                           dialog.cancel();
//                                       }
//                                   });


        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }
}
