package utils;

public class PathParser {
    private PathParser() {}

    public static Long getPathVariable(String path) {
        String firstLine = path.split("\n")[0].replaceAll("/", "");
        return Long.parseLong(firstLine);
    }
}
