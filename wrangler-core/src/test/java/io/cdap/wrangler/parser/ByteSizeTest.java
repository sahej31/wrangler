
package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.ByteSize;
import org.junit.Assert;
import org.junit.Test;

public class ByteSizeTest {
    @Test
    public void testByteConversions() {
        Assert.assertEquals(1024, new ByteSize("1KB").getBytes());
        Assert.assertEquals(1048576, new ByteSize("1MB").getBytes());
        Assert.assertEquals(1, new ByteSize("1B").getBytes());
        Assert.assertEquals(1536000, new ByteSize("1.5MB").getBytes());
    }
}
