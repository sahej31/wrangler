/*
 * Copyright © 2025 Cask Data, Inc.
 * Licensed under the Apache License, Version 2.0
 */
package io.cdap.wrangler.api.parser;
public class TimeDuration extends Token {
    private final long millis;
    public TimeDuration(String value) {
        super(value);
        this.millis = parseMillis(value.trim().toLowerCase());
    }
    private long parseMillis(String value) {
        if (value.endsWith("ms")) return (long)(Double.parseDouble(value.replace("ms", "")));
        if (value.endsWith("s")) return (long)(Double.parseDouble(value.replace("s", "")) * 1000);
        if (value.endsWith("min")) return (long)(Double.parseDouble(value.replace("min", "")) * 60000);
        if (value.endsWith("h")) return (long)(Double.parseDouble(value.replace("h", "")) * 3600000);
        throw new IllegalArgumentException("Invalid time duration: " + value);
    }
    public long getMilliseconds() { return millis; }
}
