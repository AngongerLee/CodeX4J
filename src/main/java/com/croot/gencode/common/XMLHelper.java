package com.croot.gencode.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.modelmbean.XMLParseException;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class XMLHelper {


    private final static Logger LOGGER = LoggerFactory.getLogger(XMLHelper.class);

    public static Document read(InputStream is) throws XMLParseException {

        return readFromInputStream(is);
    }

    /**
     * This method ensures that the output String has only valid XML unicode characters as specified by the XML 1.0 standard. For reference,
     * please see <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the standard</a>. This method will return an empty String if
     * the input is null or empty.
     *
     * @param in The String whose non-valid characters we want to remove.
     * @return The in String, stripped of non-valid characters.
     */
    public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.
        if (in == null || ("".equals(in)))
            return ""; // vacancy test.
        int count = 0;
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught
            // here; it should not happen.
            if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF))) {

                out.append(current);

            } else if (out.toString().charAt((i - count) - 1) == '>' && in.charAt(i + 1) == '<') {
                out.append("非有效字符");
            } else {
                count++;
            }
        }
        return out.toString();
    }

    public static Reader stripNonValidXMLCharacters(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer xmlBuffer = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            xmlBuffer.append(line);
        }
        return new StringReader(stripNonValidXMLCharacters(xmlBuffer.toString()));
    }

    public static Document readFromInputStream(InputStream is) throws XMLParseException {
        Document doc;
        SAXReader reader = new SAXReader();

        try {
            doc = reader.read(stripNonValidXMLCharacters(is));
        } catch (DocumentException e) {
            throw new XMLParseException(e, "读取文档失败");
        } catch (IOException de) {
            throw new XMLParseException(de, "解析文档失败");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭流失败: " + e.getMessage());

            }
        }

        return doc;
    }
}
