package com.example.sourabhpc.phishnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;

import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private String v_link = "";  //Visual Dns Name
    private String m_link = "www.example.com";  /* Actual Dns Name */
    private EditText e;
    private  int flag=0;
    private svmAlgoLib _svmalgolib;

    DatabaseReference databaseReference;
    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _svmalgolib = new svmAlgoLib();
        e=(EditText) findViewById(R.id.checklink);
        e.requestFocus();

        Button button = findViewById(R.id.go);
        Button contact = findViewById(R.id.contactus);
        Button about=findViewById(R.id.abt);

        databaseReference = FirebaseDatabase.getInstance().getReference("Blacklist");

        mref = FirebaseDatabase.getInstance().getReference();


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Contactus.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),aboutus.class);
                startActivity(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (e.length() == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please Enter a URL to Check").setNegativeButton("OK",null);
                    AlertDialog alert= builder.create();
                    alert.show();
                    e.requestFocus();
                }
                else if (!_svmalgolib.CheckURLValid(e.getText().toString()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please Enter a Valid Wesbite URL").setNegativeButton("OK",null);
                    AlertDialog alert= builder.create();
                    alert.show();
                    e.requestFocus();
                }
                else
                {
                    m_link=_svmalgolib.getmlink(e.getText().toString());
                    try {
                        svm(m_link,e.getText().toString());
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (UnknownHostException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }



    private void svm(String DomainName, String url) throws MalformedURLException, UnknownHostException {
        _svmalgolib = new svmAlgoLib();

        int URLLength = _svmalgolib.GetURLLength(url);
        int Port = _svmalgolib.GetDomainPort(url);
        String Scheme = _svmalgolib.GetHTTPS_Token(url);

        boolean valid = _svmalgolib.CheckURLValid(url);
        int cntDot = _svmalgolib.GetDotOccurance(url);
        int slashPos = _svmalgolib.DoubleSlashRedirect(url);

        int having_at=_svmalgolib.Having_At_Symbol(url);
        int isHyphenTrue = _svmalgolib.hyphenSeperation(url);

        int exists=checkIfUrlExists(url);
        if (exists==1||URLLength >= 54 || (Port != 80 || Port != 443) && Scheme.equals("http") || cntDot >= 4 || slashPos == 1||having_at==1 )
        {
            //testlabel.Text = pagerank.ToString();
            if(exists==1)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Phishy Site!!").setNegativeButton("OK", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
            else {
                AddToBlackList(DomainName, url);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Phishy Site!!").setNegativeButton("OK", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Not a Phishy Site!!").setNegativeButton("OK",null);
            AlertDialog alert= builder.create();
            alert.show();
        }
    }

    public int checkIfUrlExists(final String url)
    {

        mref.child("Blacklist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String Url= (String) dataSnapshot.child("url").getValue();
                    if(Url==url)
                    {
                        flag=1;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return flag;
    }


    protected  void AddToBlackList(String domainName,String url)
    {
        _svmalgolib = new svmAlgoLib();

        //String ip = _svmalgolib.GetIPAddress(url);
        int urlLength = _svmalgolib.GetURLLength(url);
        int Port = _svmalgolib.GetDomainPort(url);
        String Scheme = _svmalgolib.GetHTTPS_Token(url);

        boolean valid = _svmalgolib.CheckURLValid(url);
        int cntDot = _svmalgolib.GetDotOccurance(url);
        int slashPos = _svmalgolib.DoubleSlashRedirect(url);

        int having_at=_svmalgolib.Having_At_Symbol(url);
        int isHyphenTrue = _svmalgolib.hyphenSeperation(url);

        String id=databaseReference.push().getKey();
        InsertData insertData = new InsertData(id,url,domainName,urlLength, Port, cntDot, slashPos, isHyphenTrue, having_at, Scheme, valid);

        databaseReference.child(id).child("id").setValue(id.toString());
        databaseReference.child(id).setValue(insertData);
    }


    public boolean isOnline(String link) {
        try
        {
            _svmalgolib = new svmAlgoLib();
            String ip = _svmalgolib.GetIPAddress(link);
            int port = _svmalgolib.GetDomainPort(link);
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress(ip, port);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        }
        catch (IOException e) { return false; }
        }
    }

