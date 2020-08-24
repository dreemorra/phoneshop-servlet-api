package com.es.phoneshop.security;

import java.util.Date;

public class DosUser {
    private boolean isBanned;
    private Date date;
    private long count;

    public DosUser(Date date, long count) {
        this.date = date;
        this.count = count;
        this.isBanned = false;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
