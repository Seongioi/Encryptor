package com.example.allegro.encryptor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

public class DisplayOTPCipheredMessage extends AppCompatActivity {

    private static Random r = new Random();
    private static String alphaU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_otpciphered_message);

        boolean isChecked = getIntent().getBooleanExtra("option", true);
        String newMessage = "";
        String key = "";

        if(getIntent().getExtras().getString("key").length() == 0){
            key = RandomAlpha(getIntent().getExtras().getString("message").length());
            try {
                if (isChecked) {
                    newMessage = encrypt(getIntent().getExtras().getString("message").toUpperCase(),
                            key);
                    Log.i("DCM", "onCreate: ENCRYPTING");
                } else {
                    newMessage = decrypt(getIntent().getExtras().getString("message").toUpperCase(),
                            key);
                    Log.i("DCM", "onCreate: DECRYPTING");
                }
            } catch (NullPointerException e) { }
        } else {
            key = getIntent().getExtras().getString("key").toUpperCase();
            try{
                if (isChecked) {
                    newMessage = encrypt(getIntent().getExtras().getString("message").toUpperCase(),
                            key);
                    Log.i("DCM", "onCreate: ENCRYPTING");
                } else {
                    newMessage = decrypt(getIntent().getExtras().getString("message").toUpperCase(),
                            key);
                    Log.i("DCM", "onCreate: DECRYPTING");
                }
            } catch (NullPointerException e) { }
        }

        TextView textViewMsg = (TextView) findViewById(R.id.outputMessage);
        textViewMsg.setText(newMessage);

        TextView textViewKey = (TextView) findViewById(R.id.outputKey);
        textViewKey.setText(key);
    }

    public static String RandomAlpha(int len){
        String key = "";
        for(int x=0;x<len;x++)
            key = key + (char) (r.nextInt(26) + 'A');
        return key;
    }

    public static String encrypt(String text,String key){
        int len = text.length();

        String sb = "";
        for(int x=0;x<len;x++){
            char get = text.charAt(x);
            char keyget = key.charAt(x);
            if(Character.isUpperCase(get)){
                int index = alphaU.indexOf(get);
                int keydex = alphaU.indexOf(Character.toUpperCase(keyget));

                int total = (index + keydex) % 26;

                sb = sb + alphaU.charAt(total);
            }
            else{
                sb = sb + get;
            }
        }

        return sb;
    }
    public static String decrypt(String text,String key){
        int len = text.length();

        String sb = "";
        for(int x=0;x<len;x++){
            char get = text.charAt(x);
            char keyget = key.charAt(x);
            if(Character.isUpperCase(get)){
                int index = alphaU.indexOf(get);
                int keydex = alphaU.indexOf(Character.toUpperCase(keyget));

                int total = (index - keydex) % 26;
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
