package showtime_corp.profile_vitaile.service.service_ai;

public final class AiResponseParser {

    private AiResponseParser() {}

    public static String extract(String text, String section) {
        String start = "===" + section + "===";
        int startIndex = text.indexOf(start);

        if (startIndex == -1) {
            return "";
        }

        int contentStart = startIndex + start.length();
        int nextSection = text.indexOf("===", contentStart);

        return (nextSection == -1)
                ? text.substring(contentStart).trim()
                : text.substring(contentStart, nextSection).trim();
    }
}
