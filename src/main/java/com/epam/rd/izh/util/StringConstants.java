package com.epam.rd.izh.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Пакет util содержит в себе классы со статическими методами и константами.
 * Пример статических методов - конвертер дат.
 *
 * Данный класс содержит текстовые константные выражения.
 */
public class StringConstants {

  public static final String RUS_GREETING = "Привет, ";
  public static final String ENG_GREETING = "Hello, ";

  public static Map<String, String> mapUrl;

  static {
    mapUrl = new HashMap<>();
    mapUrl.put("USER", "/user-dashboard");
    mapUrl.put("ADMIN", "/admin-dashboard");
  }
}