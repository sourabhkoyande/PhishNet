package com.example.sourabhpc.phishnet;

public class GetData {
    int urlLength,port,cntDot,slashPos,isHyphenTrue,having_at;
    String scheme,url,domainName,id;
    boolean valid;

    public GetData()
    {

    }

    public int getUrlLength() {
        return urlLength;
    }

    public void setUrlLength(int urlLength) {
        this.urlLength = urlLength;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getCntDot() {
        return cntDot;
    }

    public void setCntDot(int cntDot) {
        this.cntDot = cntDot;
    }

    public int getSlashPos() {
        return slashPos;
    }

    public void setSlashPos(int slashPos) {
        this.slashPos = slashPos;
    }

    public int getIsHyphenTrue() {
        return isHyphenTrue;
    }

    public void setIsHyphenTrue(int isHyphenTrue) {
        this.isHyphenTrue = isHyphenTrue;
    }

    public int getHaving_at() {
        return having_at;
    }

    public void setHaving_at(int having_at) {
        this.having_at = having_at;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
