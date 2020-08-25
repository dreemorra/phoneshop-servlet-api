package com.es.phoneshop.security;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DefaultDosProtectionService implements DosProtectionService {
    private final long maxCount = 20L;
    private final long maxTime = 60L;
    private final long unbanTime = 180L;

    private Map<String, DosUser> countMap = new ConcurrentHashMap<>();

    private static DefaultDosProtectionService instance;

    public static synchronized DefaultDosProtectionService getInstance() {
        if (instance == null) {
            instance = new DefaultDosProtectionService();
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        DosUser user = countMap.get(ip);
        if (user == null) {
            user = new DosUser(new Date(), 1L);
        } else if(user.isBanned()){
            long diff = calculateTime(user);
            //unban
            if (diff >= unbanTime) {
                unbanUser(user);
                return true;
            }

            user.setLastAccessDate(new Date());
            return false;

        } else {
            long diff = calculateTime(user);
            if (diff < maxTime) {
                //ban
                if (user.getAccessCount() > maxCount) {
                    return false;
                }
            } else {
                unbanUser(user);
            }

            user.setAccessCount(user.getAccessCount()+1);
        }
        countMap.put(ip, user);
        return true;
    }

    private long calculateTime(DosUser user) {
        long milliseconds = new Date().getTime() - user.getLastAccessDate().getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        return seconds;
    }

    private void unbanUser(DosUser user) {
        user.setAccessCount(1L);
        user.setLastAccessDate(new Date());
    }
}
