package io.github.bluething.playground.handson.uniqueidgenerator.domain;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Log4j2
class SnowflakeIdGenerator implements IdGenerator {

    private static final int SIGN_BITS = 1;
    private static final int TIMESTAMP_BITS = 41;
    private static final int DATACENTER_BITS = 5;
    private static final int MACHINE_BITS = 5;
    private static final int SEQUENCE_BITS = 12;

    private static final long MAX_DATACENTER = -1L ^ (-1L << DATACENTER_BITS);
    private static final long MAX_MACHINE = -1L ^ (-1L << MACHINE_BITS);

    // custom epoch (23 November 2023)
    private static final long DEFAULT_EPOCH = 1700672400000L;

    private final long datacenterId;
    private final long machineId;
    private final AtomicLong sequence = new AtomicLong(0L);
    private final AtomicLong lastTimestamp = new AtomicLong(-1L);

    private final ConcurrentHashMap<Long, AtomicLong> threadWaitCount = new ConcurrentHashMap<>();

    SnowflakeIdGenerator(@Value("${datacenterId}") long datacenterId,
                         @Value("${machineId}") long machineId) {
        if (datacenterId > MAX_DATACENTER || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID can't be greater than " + MAX_DATACENTER + " or less than 0");
        }
        if (machineId > MAX_MACHINE || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + MAX_MACHINE + " or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    @Override
    public String nextId() {
        long currTimestamp = timestamp();
        long prevTimeStamp = lastTimestamp.get();
        if (currTimestamp < prevTimeStamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        if (currTimestamp == prevTimeStamp) {
            long seq = sequence.incrementAndGet() & SEQUENCE_BITS;
            if (seq == 0L) {
                currTimestamp = waitForNextMillis(prevTimeStamp);
            }
        } else {
            sequence.set(0L);
        }

        lastTimestamp.set(currTimestamp);

        return String.valueOf(0L << (TIMESTAMP_BITS + DATACENTER_BITS + MACHINE_BITS + SEQUENCE_BITS)
                | currTimestamp << (DATACENTER_BITS + MACHINE_BITS + SEQUENCE_BITS)
                | datacenterId << (MACHINE_BITS + SEQUENCE_BITS)
                | machineId << (SEQUENCE_BITS)
                | sequence.get());
    }

    private long timestamp() {
        return Instant.now().toEpochMilli() - DEFAULT_EPOCH;
    }

    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        long waitCount = threadWaitCount.computeIfAbsent(lastTimestamp, k -> new AtomicLong(0)).incrementAndGet();
        while (timestamp <= lastTimestamp) {
            try {
                Thread.sleep(waitCount); // Wait for existing thread wait count + 1 millisecond
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                throw new RuntimeException("Interrupted while waiting for next millisecond", e);
            }
            timestamp = System.currentTimeMillis();
        }
        threadWaitCount.remove(lastTimestamp); // Clean up the wait count for the past timestamp
        return timestamp;
    }
}
