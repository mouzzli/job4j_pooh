package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] lines = content.split(System.lineSeparator());
        String[] data = lines[0].split(" ");
        String httpRequestType = data[0];
        String[] poohModeAndSourceName = data[1].split("/");
        String param = "";
        String poohMode = poohModeAndSourceName[1];
        String sourceName = poohModeAndSourceName[2];
        if (poohModeAndSourceName.length > 3 && httpRequestType.equals("GET")) {
            param = poohModeAndSourceName[3];
        } else if (httpRequestType.equals("POST")) {
            param = lines[lines.length - 1];
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}