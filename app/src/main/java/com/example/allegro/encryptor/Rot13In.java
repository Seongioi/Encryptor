package com.example.allegro.encryptor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Rot13In extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Rot 13");
        setContentView(R.layout.activity_rot13_in);
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
        Intent cipheredMessage = new Intent(this, DisplayRot13CipheredMessage.class);

        EditText editText = (EditText) findViewById(R.id.message);
        String selectMessage = editText.getText().toString();
        Log.i("testing", "cipherB: " + selectMessage);
        if(selectMessage.length() == 0){
            Toast.makeText(Rot13In.this, "Enter a Message", Toast.LENGTH_SHORT).show();
            return;
        }
        cipheredMessage.putExtra("message", selectMessage);

        startActivity(cipheredMessage);
    }
}
