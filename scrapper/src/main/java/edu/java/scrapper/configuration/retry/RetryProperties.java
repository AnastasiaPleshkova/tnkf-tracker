package edu.java.scrapper.configuration.retry;

import java.time.Duration;

public record RetryProperties(RetryType type, int[] statuses, int attempts, Duration delay) {
}
