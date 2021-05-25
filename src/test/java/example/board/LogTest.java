package example.board;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

    private static final Logger log = LoggerFactory.getLogger(LogTest.class);
    @Test
    public void logTest() throws Exception{
        log.trace("trace log");
        log.debug("debug log");
        log.info("info log");
        log.warn("warn log");
        log.error("error log");
    }
}
