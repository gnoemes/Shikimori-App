package com.gnoemes.shikimoriapp.utils.net.parser;

import android.support.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupHtmlHelper {

    private Document doc;
    private String attr;
    private String content;

    private JsoupHtmlHelper(Document doc) {
        this.doc = doc;
    }

    public static JsoupHtmlHelper withHtml(@NonNull String html) {
        return new JsoupHtmlHelper(Jsoup.parse(html));
    }

    public static JsoupHtmlHelper withDocument(@NonNull Document doc) {
        return new JsoupHtmlHelper(doc);
    }

    public JsoupHtmlHelper withAttr(String attr) {
        this.attr = attr;
        return this;
    }

    public JsoupHtmlHelper findSingleValueByAttr() {
        content = doc.getElementsByAttribute(attr).attr(attr);
        return this;
    }

    public String getContent() {
        return content;
    }

}
