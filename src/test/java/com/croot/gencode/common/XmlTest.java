package com.croot.gencode.common;

import com.croot.gencode.enums.TypeEnum;
import com.croot.gencode.pojo.DictField;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;

import javax.management.modelmbean.XMLParseException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class XmlTest {
    @Test
    public void testRead() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dic/dict.xml")) {
            Document document = XMLHelper.read(inputStream);
            Element root = document.getRootElement();
            List<Element> dictFieldEles = root.elements();
            if(dictFieldEles == null){
                return;
            }
            List<DictField> dictFieldList = new ArrayList<>(dictFieldEles.size());
            for (Element dictFieldEle :
                    dictFieldEles) {
                DictField dictField = new DictField();
                assembleDictField(dictFieldEle,dictField);
                dictFieldList.add(dictField);
                System.out.println(dictField.getName());
                }
        } catch (XMLParseException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void assembleDictField(Element dictFieldEle,DictField dictField) throws Exception {
        Set<String> fieldNames = new HashSet<>();
            Class clz = DictField.class;
        try {
            getClzFields(clz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String name:
             fieldNames) {
           Field field = clz.getDeclaredField(name.toLowerCase());
           field.setAccessible(true);
           field.set(dictField,dictFieldEle.attributeValue(name));
        }

    }
    private void  getClzFields(Class clz) {
        for (Field field : clz.getDeclaredFields()) {
            if (!field.getType().equals(TypeEnum.class)) {
                String name = field.getName();
                name = Character.toUpperCase(name.charAt(0))+name.substring(1);
            }
        }
        if(clz.getSuperclass()!=null && clz.getSuperclass()!=Object.class){
            clz = clz.getSuperclass();
            getClzFields(clz);
        }
    }
}
