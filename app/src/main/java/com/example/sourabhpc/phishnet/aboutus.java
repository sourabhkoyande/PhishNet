package com.example.sourabhpc.phishnet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class aboutus extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.applogo)
                .setDescription("Phishing is the scourge of the Internet. One in four people are victims. " +
                        "Regrettably phishing is here to stay and while all the major players, large corporations, banks, " +
                        "Internet companies and more are doing their best to counter this problem phishing still seems the " +
                        "best way for the cyber-criminal to make money, lots of money, mostly based on the assumption that" +
                        " people are stupid and will do stupid things. " +
                        "PhishNet Puts you ahead of the curve and guards you against the threats of Phishing")
                .addItem(new Element().setTitle("Version: 1.0.0"))
                .addGroup("Connect with us")
                .addWebsite("http://www.spit.ac.in/")
                .addFacebook("the.medy")
                .addTwitter("SourabhKoyande")
                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("medyo")
                .addInstagram("sourabh_23")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format("Copyrights © %1$d",Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(aboutus.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}
