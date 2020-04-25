package com.croot.gencode.common;

import com.croot.gencode.pojo.message.*;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageXmlTest {
    private InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dic/message.xml");

    @Test
    public void testJAXBRead() {
        Interface anInterface = getInterface();
        Assert.assertNotNull("读取失败", anInterface);
    }

    private Interface getInterface() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Interface.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Interface anInterface = (Interface) unmarshaller.unmarshal(inputStream);
            return anInterface;
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void testParseInterface() {
        Interface anInterface = getInterface();
        Components components = anInterface.getComponents();
        Messages messages = anInterface.getMessages();
        Fields fields = anInterface.getFields();
        Map<String, Component> componentMap = parseComponents(components);
        Map<Long, Message> messageMap = parseMessages(messages);
        Map<String, MessageField> messageFieldMap = parseFields(fields);
        Long funcCode = 00017002L;
        //只取ANS尝试构建响应值
        if(oddOrEven(funcCode))
            funcCode++;


    }

    /**
     * true 奇数
     * false 偶数
     *
     * @param funcCode
     * @return
     */
    private boolean oddOrEven(Long funcCode) {
        return (funcCode & 1) == 1;
    }

    private Map<String, MessageField> parseFields(Fields fields) {
        List<MessageField> fieldList = fields.getFieldList();
        Map<String, MessageField> fieldMap = new HashMap<>(fieldList.size(), 1);
        for (MessageField field :
                fieldList) {
            Assert.assertFalse("存在重复Number", fieldMap.containsKey(field.getNumber()));
            fieldMap.put(field.getName(), field);
        }
        return fieldMap;
    }

    private Map<Long, Message> parseMessages(Messages messages) {
        List<Message> messageList = messages.getMessageList();
        Map<Long, Message> messageMap = new HashMap<>(messageList.size(), 1);
        for (Message message :
                messageList) {
            Long code = message.getCode();
            Assert.assertFalse("存在重复Code", messageMap.containsKey(code));
            messageMap.put(code, message);
        }
        return messageMap;


    }

    private Map<String, Component> parseComponents(Components components) {
        List<Component> componentsList = components.getComponents();
        Map<String, Component> componentMap = new HashMap<>(componentsList.size(), 1);
        for (Component component :
                componentsList) {
            String name = component.getName();
            Assert.assertFalse("存在重复Name", componentMap.containsKey(name));
            componentMap.put(name, component);
        }
        return componentMap;
    }
}
