import org.junit.Assert;
import org.junit.Test;

import ip3country.CountryLookup;

import java.util.concurrent.*;

public class TestRaceCondition {
    @Test
    public void testConcurrency() throws InterruptedException {
        int threadCount = 50;
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    CountryLookup.lookupIPString("1.2.3.4");
                    latch.countDown();
                } catch (Exception ignored) {
                    // noop
                }
            }).start();
        }

        if (!latch.await(1, TimeUnit.SECONDS)) {
            Assert.fail();
        }
    }
}
