package com.zypher.laba5;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<String> parseData(String xmlData) {
        ArrayList<String> parsedData = new ArrayList<>();
        boolean insideItem = false;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(xmlData));

            int eventType = parser.getEventType();
            String currentTag = null;
            StringBuilder currentTitle = new StringBuilder();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    currentTag = parser.getName();

                    if ("item".equals(currentTag)) {
                        insideItem = true;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    String text = parser.getText();

                    if (insideItem && "title".equals(currentTag)) {
                        currentTitle.append(text).append(" ");
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if ("item".equals(currentTag)) {
                        insideItem = false;
                    } else if (insideItem && "title".equals(currentTag)) {
                        parsedData.add(currentTitle.toString().trim());
                        currentTitle.setLength(0);
                    }
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return parsedData;
    }
}


