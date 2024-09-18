package io.github.bluething.playground.handson.uniqueidgenerator.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SnowflakeIdGeneratorTest {
    private IdGenerator idGenerator;

    @BeforeEach
    public void setupEach() {
        idGenerator = new SnowflakeIdGenerator(1, 1);
    }
    @DisplayName("Given a nextId method, the method must return a value that fit 64 bit integer")
    @Test
    void nextIdReturnIdThatFit64BitInteger() {
        long uniqueId = Long.parseLong(idGenerator.nextId());
        Assertions.assertTrue(uniqueId >= Long.MIN_VALUE && uniqueId <= Long.MAX_VALUE);
    }
}
