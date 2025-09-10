package org.safonov.rateLimiter;

public interface MyWatch {

    MyWatch SYSTEM_NANO_TIME = System::nanoTime;

    long currentTimeNanos();

}
