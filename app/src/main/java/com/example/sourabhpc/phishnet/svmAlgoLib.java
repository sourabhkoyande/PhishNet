package com.example.sourabhpc.phishnet;

import android.net.Uri;
import android.webkit.URLUtil;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class svmAlgoLib {
    public boolean CheckURLValid(String source)
    {

        boolean result = URLUtil.isValidUrl(source)&&(URLUtil.isHttpUrl(source)||URLUtil.isHttpsUrl(source));
        return result;
    }

    public int hyphenSeperation(String domainUrl)
    {
        int cnt = 0;
        if(domainUrl.contains("-"))
        {
            cnt = 1;
        }
        return cnt;
    }
    //Get m_link
    public String getmlink(String domainUrl)
    {
        URL url = null;
        try {
            url = new URL(domainUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url.getHost();
    }

    //IP Address
    public String GetIPAddress(String domainUrl){
        InetAddress address = null;
        try {
            address = InetAddress.getByName(new URL(domainUrl).getHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String ip = address.getHostAddress();
        return ip;
    }


    //URL_Length
    public int GetURLLength(String domainUrl)
    {
        return domainUrl.length();
    }

    //HTTPS_token
    public String GetHTTPS_Token(String domainUrl)
    {
        URL url = null;
        try {
            url = new URL(domainUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url.getProtocol().toString();
    }

    //Port
    public int GetDomainPort(String domainUrl)
    {
        URL url = null;
        try {
            url = new URL(domainUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url.getPort();
    }

    //subdomain and multi domain check
    public int GetDotOccurance(String domainUrl)
    {
        int cnt = 0;
        for (char c : domainUrl.toCharArray())
        {
            if (c == '.')
                cnt++;
        }
        return cnt;
    }

    //Having_At_Symbol
    public int Having_At_Symbol(String domainUrl)
    {
        int cnt=0;
        if (domainUrl.contains("@"))
        {
            cnt = 1;
        }
        return cnt;
    }

    //Double_slash_redirecting
    public int DoubleSlashRedirect(String domainUrl)
    {
        int cnt = 0;
        String Scheme = GetHTTPS_Token(domainUrl);
        //var sch = String.Compare(Scheme, "https");
        if (Scheme.length() == 5)
        {
            if (domainUrl.lastIndexOf("//") != 6)
            {
                cnt = 1;
            }
        }
        if (Scheme.length() == 6)
        {
            if (domainUrl.lastIndexOf("//") != 7)
            {
                cnt = 1;
            }
        }
        return cnt;
    }
}
