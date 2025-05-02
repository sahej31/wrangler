/*
 * Copyright © 2025 Cask Data, Inc.
 * Licensed under the Apache License, Version 2.0
 */
package io.cdap.wrangler.plugin;
import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.parser.*;
import java.util.*;
public class AggregateStats implements Directive {
    private String sizeInputCol, timeInputCol, sizeOutputCol, timeOutputCol;
    private long totalBytes = 0, totalMillis = 0;
    private boolean isFinalized = false;
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
            .addRequiredArgument("size_column", TokenType.COLUMN_NAME)
            .addRequiredArgument("time_column", TokenType.COLUMN_NAME)
            .addRequiredArgument("output_size_column", TokenType.COLUMN_NAME)
            .addRequiredArgument("output_time_column", TokenType.COLUMN_NAME)
            .build();
    }
    public void initialize(DirectiveContext ctx, TokenGroup args) {
        this.sizeInputCol = args.asString("size_column");
        this.timeInputCol = args.asString("time_column");
        this.sizeOutputCol = args.asString("output_size_column");
        this.timeOutputCol = args.asString("output_time_column");
    }
    public void initialize(ExecutorContext ctx) {}
    public List<Row> execute(Row row, ExecutorContext ctx) {
        if (isFinalized) return Collections.emptyList();
        ByteSize bs = new ByteSize(row.getValue(sizeInputCol).toString());
        TimeDuration td = new TimeDuration(row.getValue(timeInputCol).toString());
        totalBytes += bs.getBytes();
        totalMillis += td.getMilliseconds();
        return Collections.emptyList();
    }
    public List<Row> finalize(ExecutorContext ctx) {
        isFinalized = true;
        Row result = new Row();
        result.add(sizeOutputCol, totalBytes / (1024.0 * 1024));
        result.add(timeOutputCol, totalMillis / 1000.0);
        return Collections.singletonList(result);
    }
}
