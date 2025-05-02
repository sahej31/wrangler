
package io.cdap.wrangler.plugin;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.test.RecipePipelineExecutor;
import io.cdap.wrangler.test.RecipePipelineExecutor.TestingRig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {
    @Test
    public void testAggregation() throws Exception {
        List<Row> rows = Arrays.asList(
            new Row("data_transfer_size", "1MB").add("response_time", "1s"),
            new Row("data_transfer_size", "512KB").add("response_time", "500ms")
        );

        String[] recipe = {
            "aggregate-stats :data_transfer_size :response_time :total_size_mb :total_time_sec"
        };

        List<Row> result = TestingRig.execute(recipe, rows);
        Assert.assertEquals(1, result.size());
        Row aggregated = result.get(0);

        double expectedSizeMB = (1024 * 1024 + 512 * 1024) / (1024.0 * 1024);
        double expectedTimeSec = (1 + 0.5);

        Assert.assertEquals(expectedSizeMB, (double) aggregated.getValue("total_size_mb"), 0.001);
        Assert.assertEquals(expectedTimeSec, (double) aggregated.getValue("total_time_sec"), 0.001);
    }
}
