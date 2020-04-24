package com.croot.gencode.common;

import com.croot.gencode.pojo.message.Interface;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class MessageXmlTest {
@Test
    public void testJAXBRead(){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Interface.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Interface anInterface = (Interface) unmarshaller.unmarshal(getClass().getClassLoader().getResourceAsStream("dic/message.xml"));
            System.out.println(anInterface.getComponents());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
