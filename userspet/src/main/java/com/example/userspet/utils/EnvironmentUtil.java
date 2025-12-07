package com.example.userspet.utils;

public class EnvironmentUtil {
    public static boolean isRender() {
        // Render automatically sets this environment variable
        String render = System.getenv("RENDER");
        return render != null && render.equals("true");
    }
}
