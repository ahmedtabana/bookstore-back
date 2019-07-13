package com.ahmedtabana.bookstore.util;

import java.util.Random;

public class IsbnGenerator implements NumberGenerator {
    @Override
    public String generateNumber() {
        return "13-5667-" + Math.abs(new Random().nextInt());
    }
}
