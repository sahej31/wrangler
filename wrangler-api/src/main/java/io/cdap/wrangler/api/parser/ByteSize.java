/*
 * Copyright © 2025 Cask Data, Inc.
 * Licensed under the Apache License, Version 2.0
 */
package io.cdap.wrangler.api.parser;
public class ByteSize extends Token {
    private final long bytes;
    public ByteSize(String value) {
        super(value);
        this.bytes = parseBytes(value.trim().toUpperCase());
    }
    private long parseBytes(String value) {
        if (value.endsWith("KB")) return (long)(Double.parseDouble(value.replace("KB", "")) * 1024);
        if (value.endsWith("MB")) return (long)(Double.parseDouble(value.replace("MB", "")) * 1024 * 1024);
        if (value.endsWith("GB")) return (long)(Double.parseDouble(value.replace("GB", "")) * 1024 * 1024 * 1024);
        if (value.endsWith("B")) return Long.parseLong(value.replace("B", ""));
        throw new IllegalArgumentException("Invalid byte size: " + value);
    }
    public long getBytes() { return bytes; }
}
