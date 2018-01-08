package cn.itcast.autotest.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class XmlTest {

    @Test
    public void read() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("data/student-list.xml"));

        Element rootElement = document.getRootElement();
        String rootElementName = rootElement.getName();
        System.out.println(rootElementName);

        List<Element> elementList = rootElement.elements();
        for (Element studentElement : elementList) {
            String id = studentElement.attributeValue("id");
            String name = studentElement.element("name").getText();
            String age = studentElement.element("age").getText();
            System.out.println(id);
            System.out.println(name);
            System.out.println(age);
            System.out.println();
        }
    }

    @Test
    public void write() throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("data/student-list.xml"));

        Element rootElement = document.getRootElement();
        String rootElementName = rootElement.getName();
        System.out.println(rootElementName);

        Element studentElement = rootElement.addElement("student");
        studentElement.addAttribute("id", "3");
        Element nameElement = studentElement.addElement("name");
        nameElement.setText("宋小宝 ");
        studentElement.addElement("age").setText("18");

        OutputFormat format = new OutputFormat("\t", true, "UTF-8");
        format.setTrimText(true);

        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(new File("data/student-list.xml")), format);
        xmlWriter.write(document);
        xmlWriter.close();
    }

}
