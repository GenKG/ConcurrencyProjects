package org.safonov.rateLimiter;

public interface MyRateLimiter {

    boolean isAllowed(String resource);

}
