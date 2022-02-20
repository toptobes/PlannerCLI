package com.planner.utility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public enum Color {
    DEFAULT("\033[0m"),

    BLACK("\033[0;30m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    MAGENTA("\033[0;35m"),
    CYAN("\033[0;36m"),
    WHITE("\033[0;37m");

    private final String code;
    private static Color defaultColor;

    Color(String code) {
        this.code = code;
    }

    public static String color(String toColor, Color color) {
        return color + toColor.replace("\n", "\n" + color);
    }

    public static void setDefaultColor(Color color){
        defaultColor = Objects.requireNonNullElse(color, DEFAULT);
        System.out.print(defaultColor);
    }

    public static void setDefaultColor(String string){
        setDefaultColor(getColors().getOrDefault(string, null));
    }

    public static Color getDefaultColor(){
        return defaultColor;
    }

    public static Map<String, Color> getColors() {
        return new HashMap<>(Arrays.stream(Color.values())
                .collect(Collectors.toMap(
                        Enum::name,
                        color -> color
                )));
    }

    @Override
    public String toString() {
        return code;
    }
}
