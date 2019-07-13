package com.ahmedtabana.bookstore.util;

public class TextUtil {

    public String sanitize(String textToSantitize){
        return textToSantitize.replaceAll("\\s+"," ");
    }
}
