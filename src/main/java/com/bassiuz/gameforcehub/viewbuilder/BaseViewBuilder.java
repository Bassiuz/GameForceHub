package com.bassiuz.gameforcehub.viewbuilder;

public class BaseViewBuilder {

    /**
     * This function wraps the page in a body.
     * @param page
     * @return
     */
    public static String wrapPage(String page)
    {
        return wrapPageTop + page + wrapPageBottom;
    }
    public static String wrapPageTop = "<head></head><body>";
    public static String wrapPageBottom = "</body>";

    public static String addHeader(String page)
    {
        return headerTop + page + headerBottom;
    }
    public static String headerTop = "";
    public static String headerBottom = "";
}
