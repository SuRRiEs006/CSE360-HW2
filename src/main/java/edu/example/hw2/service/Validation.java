package edu.example.hw2.service;

import edu.example.hw2.exceptions.ValidationException;

public final class Validation {
    private Validation() {}
    public static void requireRange(String name, String s, int min, int max) {
        if (s == null || s.trim().length() < min) {
            throw new ValidationException(name + " cannot be empty");
        }
        if (s.trim().length() > max) {
            throw new ValidationException(name + " too long (max " + max + ")");
        }
    }
}
