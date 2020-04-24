package com.croot.gencode.common;

import com.croot.gencode.pojo.dict.DictField;
import com.croot.gencode.pojo.dict.Dictionary;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Assert;
import org.junit.Test;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class DictXmlTest {

    @Test
    public void testRead() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dic/dict.xml")) {
            Document document = XMLHelper.read(inputStream);
            Element root = document.getRootElement();
            List<Element> dictFieldEles = root.elements();
            if (dictFieldEles == null) {
                return;
            }
            Set<String> filterFields = new HashSet<>();
            filterFields.add("typeEnum");
            Set<DictField> dictFieldSet = new HashSet<>(dictFieldEles.size());
            for (Element dictFieldEle :
                    dictFieldEles) {
                DictField dictField = new DictField();
                assembleDictField(dictFieldEle, dictField, filterFields);
                Assert.assertNotNull("Field属性不为空", dictField.getType());
                dictFieldSet.add(dictField);
            }
            Assert.assertNotNull("FieldList不为空", dictFieldSet.size() != 0);
        } catch (XMLParseException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJAXBReader() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Dictionary.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Dictionary dictionary = (Dictionary) unmarshaller.unmarshal(getClass().getClassLoader().getResourceAsStream("dic/dict.xml"));
            Assert.assertNotNull("dictList为空", dictionary.getDictFieldList());
            Assert.assertNotNull("dictField解析错误", dictionary.getDictFieldList().get(0).getType());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void assembleDictField(Element dictFieldEle, DictField dictField, Set<String> filterFields) throws Exception {
        Map<String, Field> fieldMap = new HashMap<>();
        Class clz = DictField.class;
        try {
            getClzFields(clz, fieldMap, filterFields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
            String name = entry.getKey();
            Field field = entry.getValue();
            field.setAccessible(true);
            String type = field.getGenericType().toString();
            if (type.endsWith("String")) {
                field.set(dictField, dictFieldEle.attributeValue(name));
            } else if (type.endsWith("int") || type.endsWith("Integer")) {
                field.set(dictField, Integer.parseInt(dictFieldEle.attributeValue(name)));
            } else if (type.endsWith("double") || type.endsWith("Double")) {
                field.set(dictField, Double.parseDouble(dictFieldEle.attributeValue(name)));
            } else if (type.endsWith("long") || type.endsWith("Long")) {
                field.set(dictField, Long.parseLong(dictFieldEle.attributeValue(name)));
            }

        }

    }

    /**
     * 获取类及其父类所有属性，如果存在父类子类同名属性，将忽略父类属性
     * TODO 应判断同名且同类型
     *
     * @param clz
     * @param fieldMap
     */
    private void getClzFields(Class clz, Map<String, Field> fieldMap, Set<String> filterFields) {
        for (Field field : clz.getDeclaredFields()) {
            String name = field.getName();
            if (filterFields.contains(name)) {
                continue;
            }
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            fieldMap.putIfAbsent(name, field);
        }
        if (clz.getSuperclass() != null && clz.getSuperclass() != Object.class) {
            clz = clz.getSuperclass();
            getClzFields(clz, fieldMap, filterFields);
        }
    }


}
