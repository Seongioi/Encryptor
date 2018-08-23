package com.example.allegro.encryptor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayEnigmaCipheredMessage extends AppCompatActivity {
    private static String rotor1key = "EKMFLGDQVZNTOWYHXUSPAIBRCJ"; //Enigma I, rotor I
    private static String rotor2key = "AJDKSIRUXBLHWTMCQGZNPYFVOE"; //Enigma I, rotor II
    private static String rotor3key = "BDFHJLCPRTXVZNYEIWGAKMUSQO"; //Enigma I, rotor III
    private static String rotor4key = "ESOVPZJAYQUIRHXLNFTGKDCMWB"; //M3 Army, rotor IV
    private static String rotor5key = "VZBRGITYUPSDNHLXAWMJQOFECK"; //M3 Army, rotor V

    private static String beta = "LEYJVCNIXWPBQMDRTAKZGFUHOS"; //M4 R2, rotor Beta
    private static String gamma = "FSOKANUERHMBTIYCWLQPZXVGJD"; //M4 R2, rotor Gamma


    private static ArrayList<Sets> reflectorBeta = new ArrayList<>();
    private static ArrayList<Sets> reflectorGamma = new ArrayList<>();

    private static ArrayList<Sets> plugBoard = new ArrayList<>();

    private static ArrayList<Sets> leftRotor = new ArrayList<>();
    private static ArrayList<Sets> middleRotor = new ArrayList<>();
    private static ArrayList<Sets> rightRotor = new ArrayList<>();
    private static ArrayList<Sets> reflector = new ArrayList<>();

    private static int smallWheel = 0;
    private static int midWheel = 0;
    private static int largeWheel = 0;
    private static boolean usePlug = false;

    public String encrypt(String in)
    {
        String encrypted = "";
        int test = 0;
        while(in.length()>0)
        {
            String temp = in.substring(0,1);
            String check = temp;
            if(usePlug){
                temp = matchPlug(temp);
            }
            temp = findKey("right",temp,"encryK1");
            temp = findKey("middle",temp,"encryK2");
            temp = findKey("left",temp,"encryK3");
            temp = reflector(temp , "encryR");
            temp = findOG("left",temp,"encryO1");
            temp = findOG("middle",temp,"encryO2");
            temp = findOG("right",temp,"encryO3");
            if(usePlug){
                temp = matchPlugR(temp);
            }
            encrypted += temp;
            if(!temp.equals(check))
                rotate();
            in = in.substring(1);
        }
        return encrypted;
    }

    public String decrypt(String in)
    {
        String decrypted = "";
        int test = 0;
        while(in.length()>0)
        {
            String temp = in.substring(0,1);
            String check = temp;
            if(usePlug){
                temp = matchPlug(temp);
            }
            temp = findKey("right",temp,"encryK1");
            temp = findKey("middle",temp,"encryK2");
            temp = findKey("left",temp,"encryK3");
            temp = reflectorR(temp , "encryR");
            temp = findOG("left",temp,"encryO1");
            temp = findOG("middle",temp,"encryO2");
            temp = findOG("right",temp,"encryO3");
            if(usePlug){
                temp = matchPlugR(temp);
            }
            decrypted += temp;
            if(!temp.equals(check))
                rotate();
            in = in.substring(1);
        }
        return decrypted;
    }

    //gets moving letter on moving part of the rotor
    public String findKey(String rotor, String op, String loc)
    {
        if(rotor.equals("right"))
        {
            for (int i = 0; i<rightRotor.size(); i++)
            {
                if(rightRotor.get(i).og.equals(op))
                    return rightRotor.get(i).key;
            }
        }
        if(rotor.equals("middle"))
        {
            for (int i = 0; i<middleRotor.size(); i++)
            {
                if(middleRotor.get(i).og.equals(op))
                    return middleRotor.get(i).key;
            }
        }
        if(rotor.equals("left"))
        {
            for (int i = 0; i<leftRotor.size(); i++)
            {
                if(leftRotor.get(i).og.equals(op))
                    return leftRotor.get(i).key;
            }
        }
        //System.out.println("ERROR " + op + " : " + loc);
        return op;
    }

    //gets the letter on constant side of rotor
    public String findOG(String rotor, String op, String loc)
    {
        if(rotor.equals("right"))
        {
            for (int i = 0; i<rightRotor.size(); i++)
            {
                if(rightRotor.get(i).key.equals(op))
                    return rightRotor.get(i).og;
            }
        }
        if(rotor.equals("middle"))
        {
            for (int i = 0; i<middleRotor.size(); i++)
            {
                if(middleRotor.get(i).key.equals(op))
                    return middleRotor.get(i).og;
            }
        }
        if(rotor.equals("left"))
        {
            for (int i = 0; i<leftRotor.size(); i++)
            {
                if(leftRotor.get(i).key.equals(op))
                    return leftRotor.get(i).og;
            }
        }
        //System.out.println("ERROR " + op + " : " + loc);
        return op;
    }

    //gets letter to reflector
    public String reflector(String op,String loc)
    {
        for (int i = 0; i<reflector.size(); i++)
        {
            if(reflector.get(i).og.equals(op))
                return reflector.get(i).key;
        }
        //System.out.println("ERROR " + op + " : " + loc);
        return op;
    }

    //gets the reverse reflector
    public String reflectorR(String op,String loc)
    {
        for (int i = 0; i<reflector.size(); i++)
        {
            if(reflector.get(i).key.equals(op))
                return reflector.get(i).og;
        }
        //System.out.println("ERROR " + op + " : " + loc);
        return op;
    }

    public String matchPlugR(String op)
    {
        for (int i = 0; i<plugBoard.size(); i++)
        {
            if(plugBoard.get(i).og.equals(op))
                return plugBoard.get(i).key;
        }
        System.out.println("ERROR " + op);
        return op;
    }

    public String matchPlug(String op)
    {
        for (int i = 0; i<plugBoard.size(); i++)
        {
            if(plugBoard.get(i).key.equals(op))
                return plugBoard.get(i).og;
        }
        System.out.println("ERROR " + op);
        return op;
    }

    //rotates the rotors after every character
    public void rotate()
    {
        String tempKEY = rightRotor.get(0).key;
        for (int i = 0; i<25; i++) {
            rightRotor.get(i).key = rightRotor.get(i+1).key;
        }
        rightRotor.get(25).key = tempKEY;
        smallWheel++;
        if(smallWheel>26)
        {
            tempKEY = middleRotor.get(0).key;
            for (int i = 0; i<25; i++) {
                middleRotor.get(i).key = middleRotor.get(i+1).key;
            }
            middleRotor.get(25).key = tempKEY;
            smallWheel %= 26;
            midWheel++;
        }
        if(midWheel>26)
        {
            tempKEY = leftRotor.get(0).key;
            for (int i = 0; i<25; i++) {
                leftRotor.get(i).key = leftRotor.get(i+1).key;
            }
            leftRotor.get(25).key = tempKEY;
            midWheel %= 26;
            largeWheel++;
        }
        if(largeWheel>26)
        {
            largeWheel %=26;
        }
    }

    //allows user to choose the rotor used to encrypt message, NOT SUPPORTED IN THIS VERSION
    public void setRotors(int left, int middle, int right) {
        int add = 0;
        if(left==1)
            for (int i = 65; i<91; i++) {
                leftRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor1key.charAt(add)) ) );
                add++;
            }
        if(left==2)
            for (int i = 65; i<91; i++) {
                leftRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor2key.charAt(add)) ) );
                add++;
            }
        if(left==3)
            for (int i = 65; i<91; i++) {
                leftRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor3key.charAt(add)) ) );
                add++;
            }
        if(left==4)
            for (int i = 65; i<91; i++) {
                leftRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor4key.charAt(add)) ) );
                add++;
            }
        if(left==5)
            for (int i = 65; i<91; i++) {
                leftRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor5key.charAt(add)) ) );
                add++;
            }

        add = 0;

        if(middle==1)
            for (int i = 65; i<91; i++) {
                middleRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor1key.charAt(add)) ) );
                add++;
            }
        if(middle==2)
            for (int i = 65; i<91; i++) {
                middleRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor2key.charAt(add)) ) );
                add++;
            }
        if(middle==3)
            for (int i = 65; i<91; i++) {
                middleRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor3key.charAt(add)) ) );
                add++;
            }
        if(middle==4)
            for (int i = 65; i<91; i++) {
                middleRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor4key.charAt(add)) ) );
                add++;
            }
        if(middle==5)
            for (int i = 65; i<91; i++) {
                middleRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor5key.charAt(add)) ) );
                add++;
            }

        add = 0;

        if(right==1)
            for (int i = 65; i<91; i++) {
                rightRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor1key.charAt(add)) ) );
                add++;
            }
        if(right==2)
            for (int i = 65; i<91; i++) {
                rightRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor2key.charAt(add)) ) );
                add++;
            }
        if(right==3)
            for (int i = 65; i<91; i++) {
                rightRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor3key.charAt(add)) ) );
                add++;
            }
        if(right==4)
            for (int i = 65; i<91; i++) {
                rightRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor4key.charAt(add)) ) );
                add++;
            }
        if(right==5)
            for (int i = 65; i<91; i++) {
                rightRotor.add( new Sets( String.valueOf((char)i), String.valueOf(rotor5key.charAt(add)) ) );
                add++;
            }
    }

    public void setReflectorBeta(){
        int add = 0;
        for (int i = 65; i<91; i++) {
            reflector.add( new Sets( String.valueOf((char)i), String.valueOf(beta.charAt(add)) ) );
            add++;
        }
    }

    public void setReflectorGamma(){
        int add = 0;
        for (int i = 65; i<91; i++) {
            reflector.add( new Sets( String.valueOf((char)i), String.valueOf(gamma.charAt(add)) ) );
            add++;
        }
    }

    public void setUp(){

        //Set plug board
        String plug = getIntent().getExtras().getString("plugboard");
        if(plug.length() > 0){
            plug = plug.toUpperCase();
            int add = 0;
            for (int i = 65; i<91; i++)
            {
                plugBoard.add( new Sets( String.valueOf((char)i) ,String.valueOf(plug.charAt(add)) ) );
                add++;
            }
            usePlug = true;
        }
        Log.i("DCM", "setUp: PLUGBOARD " + usePlug);

        //Set rotors
        setRotors(Integer.parseInt(getIntent().getExtras().getString("leftRotor"))+1,
                Integer.parseInt(getIntent().getExtras().getString("middleRotor"))+1,
                Integer.parseInt(getIntent().getExtras().getString("rightRotor"))+1);
        Log.i("DCM", "setUp: ROTORS " + (getIntent().getExtras().getString("leftRotor")) + " " +
                (getIntent().getExtras().getString("middleRotor")) + " " +
                Integer.parseInt(getIntent().getExtras().getString("rightRotor")));

        //set reflectors
        if(getIntent().getExtras().getString("reflector").equals("beta")) {
            setReflectorBeta();
            Log.i("DCM", "setUp: REFLECTOR BETA");
        }else {
            setReflectorGamma();
            Log.i("DCM", "setUp: REFLECTOR GAMMA");
        }

        //Set Rotor location
        int turns = Integer.parseInt(getIntent().getExtras().getString("rightRotorLoc")) +
                Integer.parseInt(getIntent().getExtras().getString("middleRotorLoc"))*26 +
                Integer.parseInt(getIntent().getExtras().getString("leftRotorLoc"))*26*26;
        for (int i = 0; i < turns; i++) {
            rotate();
        }
        Log.i("DCM", "setUp: starting loc " + turns);
    }

    public void clearSettings(){
        reflectorBeta = new ArrayList<>();
        reflectorGamma = new ArrayList<>();

        plugBoard = new ArrayList<>();

        leftRotor = new ArrayList<>();
        middleRotor = new ArrayList<>();
        rightRotor = new ArrayList<>();
        reflector = new ArrayList<>();

        smallWheel = 0;
        midWheel = 0;
        largeWheel = 0;
        usePlug = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_enigma_ciphered_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        clearSettings();
        Intent i = getIntent();
        Log.i("DCM", "onCreate: " + i.getStringExtra("plugboard") + " " + i.getStringExtra("message") );
        setUp();
        Log.i("DCM", "onCreate: FINISH SETUP");
        boolean isChecked = getIntent().getBooleanExtra("option", true);
        String newMessage = "";
        try{
            if(isChecked){
                newMessage = encrypt(getIntent().getExtras().getString("message").toUpperCase());
                Log.i("DCM", "onCreate: ENCRYPTING");
            }
            else{
                newMessage = decrypt(getIntent().getExtras().getString("message").toUpperCase());
                Log.i("DCM", "onCreate: DECRYPTING");
            }
        } catch (NullPointerException e) { }
        Log.i("DCM", "onCreate: FINISH ENCRY " + newMessage);
        TextView textView = (TextView) findViewById(R.id.outputMessage);
        textView.setText(newMessage);
    }

}
