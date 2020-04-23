package com.croot.gencode.common;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;

import javax.management.modelmbean.XMLParseException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlTest {
    @Test
    public void testRead() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dic/dict.xml")) {
            Document document = XMLHelper.read(inputStream);
            Element root = document.getRootElement();
            List<Element> list = root.elements();
            for (Element ele :
                    list) {
                for (Object object :
                        ele.attributes()) {
                    Attribute attribute = (Attribute) object;
                    attribute.getText();
                }
            }
            System.out.println(list.size());
        } catch (XMLParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
