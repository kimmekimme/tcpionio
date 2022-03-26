package iofile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static List<Integer> NoList;
    private static List<String> titleList;
    private static List<String> categoryList;
    private static List<String> langList;
    private static List<String> authorList;

    private static List<String> yearList;

    private static List<String> priceList;

    public static List<Integer> getNoList() {
        return NoList;
    }

    public static List<String> getTitleList() {
        return titleList;
    }

    public static List<String> getCategoryList() {
        return categoryList;
    }

    public static List<String> getLangList() {
        return langList;
    }

    public static List<String> getAuthorList() {
        return authorList;
    }

    public static List<String> getYearList() {
        return yearList;
    }

    public static List<String> getPriceList() {
        return priceList;
    }

    public static void parse() throws ParserConfigurationException, IOException, SAXException {

        // 파싱
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //static method, singleton
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 서버로부터 파일 받기
        File file = Server.getFile();

        Document doc = builder.parse(file);

        doc.getDocumentElement().normalize();

        System.out.println("[루트]: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("book");
        NoList = new ArrayList<>();
        titleList = new ArrayList<>();
        categoryList = new ArrayList<>();
        langList = new ArrayList<>();
        authorList = new ArrayList<>();
        yearList = new ArrayList<>();
        priceList = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Node lang = element.getFirstChild().getNextSibling();
                //list에 값 차례대로 넣기
                Element lang1 = (Element) lang;
                NoList.add(temp + 1);
                titleList.add(element.getElementsByTagName("title").item(0).getTextContent());
                categoryList.add(element.getAttribute("category"));
                langList.add(lang1.getAttribute("lang"));
                authorList.add(element.getElementsByTagName("author").item(0).getTextContent());
                yearList.add(element.getElementsByTagName("year").item(0).getTextContent());
                priceList.add(element.getElementsByTagName("price").item(0).getTextContent());

            }
    }
}

}
