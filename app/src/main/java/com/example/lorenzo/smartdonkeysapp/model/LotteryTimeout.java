package com.example.lorenzo.smartdonkeysapp.model;

public class LotteryTimeout extends Message{
    long remaining_seconds;

    public LotteryTimeout(long remaining_seconds) {
        super(MESSAGE_TYPE.LOTTERY_TIMEOUT, "");
        this.remaining_seconds = remaining_seconds;
    }

    public long getRemaining_seconds() {
        return remaining_seconds;
    }
}
