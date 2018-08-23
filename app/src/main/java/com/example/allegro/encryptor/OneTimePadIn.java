package com.example.allegro.encryptor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class OneTimePadIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("One Time Pad");
        setContentView(R.layout.activity_one_time_pad_in);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.enigma:
                Intent x = new Intent(this, EnigmaIn.class);
                startActivity(x);
                break;
            case R.id.onetimepad:
                Intent n = new Intent(this, OneTimePadIn.class);
                startActivity(n);
                break;
            case R.id.rot13:
                Intent m = new Intent(this, Rot13In.class);
                startActivity(m);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cipherB(View view){
        Log.i("DCM", "cipherB: button clicked");
        Intent cipheredMessage = new Intent(this, DisplayOTPCipheredMessage.class);

        EditText editText = (EditText) findViewById(R.id.message);
        String selectMessage = editText.getText().toString();
        Log.i("testing", "cipherB: " + selectMessage);
        if(selectMessage.length() == 0){
            Toast.makeText(OneTimePadIn.this, "Enter a Message", Toast.LENGTH_SHORT).show();
            return;
        }
        cipheredMessage.putExtra("message", selectMessage);
        Log.i("DCM", "cipherB: received message");

        EditText editTextKey = (EditText) findViewById(R.id.key);
        Log.i("DCM", "cipherB: Key length " + editTextKey.getText().toString().length());
        if(editTextKey.getText().toString().length() < selectMessage.length() && editTextKey.getText().toString().length() != 0){
            Toast.makeText(OneTimePadIn.this, "Key must be same length as message", Toast.LENGTH_SHORT).show();
            return;
        } else {
            cipheredMessage.putExtra("key", editTextKey.getText().toString());
        }
        Log.i("DCM", "cipherB: received key");

        Switch s = (Switch) findViewById(R.id.cypherOption);
        cipheredMessage.putExtra("option", s.isChecked());

        Log.i("DCM", "onCreate: FINISH CLASS ");

        startActivity(cipheredMessage);
    }
}
