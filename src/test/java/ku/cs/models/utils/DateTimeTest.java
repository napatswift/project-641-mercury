package ku.cs.models.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeTest {

    @Test
    void testNotThisYear(){
        LocalDateTime localDateTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        assertEquals("01:01 - 1 Jan 2020", DateTime.toReadableDateTime(localDateTime));
    }

    @Test
    void testOnThisWeek(){
        LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 5, 1, 1);
        assertEquals("Tuesday 01:01", DateTime.toReadableDateTime(localDateTime));
    }

    @Test
    void testInThisYear(){
        LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 5, 1, 1);
        assertEquals("01:01 - 5 Jan", DateTime.toReadableDateTime(localDateTime));
    }
}