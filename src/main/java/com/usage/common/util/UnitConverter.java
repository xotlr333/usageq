package com.usage.common.util;

public class UnitConverter {
    public static long convertToSeconds(long value, String unit) {
        return switch (unit.toUpperCase()) {
            case "MINUTES" -> value * 60;
            case "HOURS" -> value * 3600;
            default -> value;
        };
    }

    public static long convertToPackets(long value, String unit) {
        return switch (unit.toUpperCase()) {
            case "KB" -> value * 1024;
            case "MB" -> value * 1024 * 1024;
            default -> value;
        };
    }
}

# Usage Query Module
