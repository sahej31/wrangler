
package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {
    @Test
    public void testTimeConversions() {
        Assert.assertEquals(1000000, new TimeDuration("1ms").getNanos());
        Assert.assertEquals(2000000000, new TimeDuration("2s").getNanos());
        Assert.assertEquals(90000000000L, new TimeDuration("1.5min").getNanos());
    }
}
