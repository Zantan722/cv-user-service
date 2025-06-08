package com.neutec.blog.constant;

public class UrlConstant {

    public static final String COMMON_PATH = "/common/";
    public static final String ADMIN_PATH = "/admin/";
    public static final String USER_PATH = "/user/";

    public static final String[] PUBLIC_PATTERN_PATHS = new String[] {
        COMMON_PATH,
        "/swagger-ui/",
        "v3/api-docs/"
    };

    public static final String[] ADMIN_PATTERN_PATH = new String[] {
        ADMIN_PATH,
    };

    public static final String[] PUBLIC_PATHS = PUBLIC_PATHS();

    public static final String[] ADMIN_PATHS = ADMIN_PATHS();



    private static String[] PUBLIC_PATHS() {
        String[] paths = new String[PUBLIC_PATTERN_PATHS.length];
        for (int i = 0; i < PUBLIC_PATTERN_PATHS.length; i++) {
            paths[i] = PUBLIC_PATTERN_PATHS[i] + "**";
        }
        return paths;
    }

    private static String[] ADMIN_PATHS() {
        String[] paths = new String[ADMIN_PATTERN_PATH.length];
        for (int i = 0; i < ADMIN_PATTERN_PATH.length; i++) {
            paths[i] = ADMIN_PATTERN_PATH[i] + "**";
        }
        return paths;
    }
}
