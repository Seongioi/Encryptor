package com.example.allegro.encryptor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class EnigmaIn extends AppCompatActivity {

    public String selectMessage, selectPlugBoard, selectReflector;
    public int selectLeftRotor, selectRightRotor, selectMiddleRotor , selectLeftRotorLoc, selectMiddleRotorLoc, selectRightRotorLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Enigma");
        setContentView(R.layout.activity_enigma_in);

        //Spinner spinnerRotor1 = (Spinner) findViewById(R.id.spinnerRotor1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<CharSequence> rotorAdapter1 = ArrayAdapter.createFromResource(this,R.array.rotorNames, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        //rotorAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //spinnerRotor1.setAdapter(rotorAdapter1);

        selectMessage = "";
        selectPlugBoard = "";
        selectReflector =  "beta";
        selectLeftRotor = 0;
        selectRightRotor = 0;
        selectMiddleRotor = 0;
        selectLeftRotorLoc = 0;
        selectMiddleRotorLoc = 0;
        selectRightRotorLoc = 0;

        addListenerOnSpinnersRotors();
        addListenerOnSpinnersReflectors();
        addListenerOnRotorInput();
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

    public void addListenerOnRotorInput(){
        EditText leftRotor = (EditText) findViewById(R.id.startingLocLeft);
        EditText middleRotor = (EditText) findViewById(R.id.startingLocMiddle);
        EditText rightRotor = (EditText) findViewById(R.id.startingLocRight);

        leftRotor.addTextChangedListener(numBoundWatcher);
        middleRotor.addTextChangedListener(numBoundWatcher);
        rightRotor.addTextChangedListener(numBoundWatcher);
    }
    public TextWatcher numBoundWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length()>0) {
                int num = Integer.parseInt(charSequence.toString());
                if (num>=0 && num <= 25){
                    Log.i("Rotor Loc", "onTextChanged: ROTOR: " + num);
                } else {
                    Toast.makeText(EnigmaIn.this, "Please enter a number between 0 and 25", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            try{
                int in = Integer.parseInt(editable.toString());
                if (in > 25)
                    editable.replace(0,editable.length(),"25");
                if (in < 0)
                    editable.replace(0,editable.length(), "0");
            } catch (NumberFormatException e) {  }
        }
    };

    public void addListenerOnSpinnersReflectors(){
        Spinner spinnerReflector = (Spinner) findViewById(R.id.spinnerReflector);

        spinnerReflector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Testing", "onItemSelected: REFLECTOR ITEM " + i);
                if(i == 0)
                    selectReflector = "beta";
                else
                    selectReflector = "gamma";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addListenerOnSpinnersRotors(){
        Spinner spinnerRotor1 = (Spinner) findViewById(R.id.spinnerRotor1);
        Spinner spinnerRotor2 = (Spinner) findViewById(R.id.spinnerRotor2);
        Spinner spinnerRotor3 = (Spinner) findViewById(R.id.spinnerRotor3);

        spinnerRotor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Testing", "onItemSelected: spinner ONE ITEM " + i);
                selectLeftRotor = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerRotor2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Testing", "onItemSelected: spinner TWO ITEM " + i);
                selectMiddleRotor = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerRotor3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Testing", "onItemSelected: spinner THREE ITEM " + i);
                selectRightRotor = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void cipherB(View view){
        Intent cipheredMessage = new Intent(this, DisplayEnigmaCipheredMessage.class);

        EditText editText = (EditText) findViewById(R.id.message);
        selectMessage = editText.getText().toString();
        Log.i("testing", "cipherB: " + selectMessage);
        if(selectMessage.length() == 0){
            Toast.makeText(EnigmaIn.this, "Enter a Message", Toast.LENGTH_SHORT).show();
            return;
        }
        cipheredMessage.putExtra("message", selectMessage);
        Log.i("testing", "cipherB: LEFT ROTOR " + selectLeftRotor);
        cipheredMessage.putExtra("leftRotor", selectLeftRotor + "");
        cipheredMessage.putExtra("middleRotor", selectMiddleRotor + "");
        cipheredMessage.putExtra("rightRotor", selectRightRotor + "");
        cipheredMessage.putExtra("reflector", selectReflector + "");
        cipheredMessage.putExtra("leftRotorLoc", selectLeftRotorLoc  + "");
        cipheredMessage.putExtra("middleRotorLoc", selectMiddleRotorLoc + "");
        cipheredMessage.putExtra("rightRotorLoc", selectRightRotorLoc  + "");
        // Log.i("plugboard length", "cipherB: " + selectPlugBoard.length());
        if (selectPlugBoard.length() != 26 && selectPlugBoard.length() != 0){
            Log.i("TEST", "cipherB: RETURNING");
            Toast.makeText(EnigmaIn.this, "Enter 26 letters into plug board or leave empty", Toast.LENGTH_SHORT).show();
            return;
        } else {
            cipheredMessage.putExtra("plugboard", selectPlugBoard);
        }
        Switch s = (Switch) findViewById(R.id.cypherOption);
        cipheredMessage.putExtra("option", s.isChecked());
        Log.i("TEST", "cipherB: START ACTIVITY");
        startActivity(cipheredMessage);
    }
}
