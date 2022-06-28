package com.shaxhxnk.rnspark.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.shaxhxnk.rnspark.R;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Instructions")
                .setMessage("1.You cannot chat if you don't upload your profile picture \n" +
                        "2.Swipe right if you like the profile and left if you don't\n" +
                        "3.If you left swipe a person you cannot view their profile again\n" +
                        "4.You cannot chat with a person until both of you right swipe each other\n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent =new Intent(InstructionsActivity.this, TinderActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
}