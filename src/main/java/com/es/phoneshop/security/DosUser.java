package com.es.phoneshop.security;

import java.util.Date;

public class DosUser {
    private boolean isBanned;
    private Date lastAccessDate;
    private long accessCount;

    public DosUser(Date lastAccessDate, long accessCount) {
        this.lastAccessDate = lastAccessDate;
        this.accessCount = accessCount;
        this.isBanned = false;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public long getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }
}
