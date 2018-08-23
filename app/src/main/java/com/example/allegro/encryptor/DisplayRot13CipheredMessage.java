package com.example.allegro.encryptor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayRot13CipheredMessage extends AppCompatActivity {

    private static String alphaU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Rot 13");
        setContentView(R.layout.activity_display_rot13_ciphered_message);

        boolean isChecked = getIntent().getBooleanExtra("option", true);
        String newMessage = "";
        try {
            if (isChecked) {
                newMessage = encrypt(getIntent().getExtras().getString("message").toUpperCase());
                Log.i("DCM", "onCreate: ENCRYPTING");
            } else {
                newMessage = decrypt(getIntent().getExtras().getString("message").toUpperCase());
                Log.i("DCM", "onCreate: DECRYPTING");
            }
        } catch (NullPointerException e) { }

        TextView textViewMsg = (TextView) findViewById(R.id.outputMessage);
        textViewMsg.setText(newMessage);
    }

    private String encrypt(String text){
        String sb = "";

        for(int i = 0; i < text.length(); i++){
            char get = text.charAt(i);
            if(Character.isUpperCase(get)){
                int index = alphaU.indexOf(get);
                int total = (index + 13) % 26;
                total = (total<0)? total + 26 : total;

                sb = sb + alphaU.charAt(total);
            }
            else{
                sb = sb + get;
            }
        }

        return sb;
    }

    private String decrypt(String text){
        String sb = "";

        for(int i = 0; i < text.length(); i++){
            char get = text.charAt(i);
            if(Character.isUpperCase(get)){
                int index = alphaU.indexOf(get);
                int total = (index - 13) % 26;

                total = (total<0)? total + 26 : total;

                sb = sb + alphaU.charAt(total);
            }
            else{
                sb = sb + get;
            }
        }

        return sb;
    }
}
