package test;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FileServerTest {
    public static void main(String[] args) throws IOException {
        //    String pathName = "d:/d_other/upload/";
        String pathName = "c:/test/upload/";
        File saveDirectory = new File(pathName);

        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }

        DataOutputStream dataOutputStream = null;
        ServerSocket serverSocket2 = null;
        Socket socket2 = null;
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            System.out.println("[서버 대기 상태]");
            Socket socket = serverSocket.accept();
            System.out.println("[파일 저장 시작]");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(dataInputStream);

            String fileName = dataInputStream.readUTF();
            File file = new File(saveDirectory, fileName);

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));

            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, length);
            }

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            System.out.println("[파일 저장 완료]: " + pathName + fileName);

            // File excelFile = new File(pathName + "\\" + fileName);
            List<String> attributeNames = List.of("NO", "TITLE", "CATEGORY", "LANG", "AUTHOR", "YEAR", "PRICE");
            //Create a 엑셀파일
            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a 엑셀시트
            XSSFSheet sheet = workbook.createSheet("sheet1");

            //엑셀 시트에 행 지정
            XSSFRow row; // 첫 행

            //11행 7열(컬럼명 맨위 1행)
            for (int j = 0; j < 11; j++) {
                row = sheet.createRow(j);
                for (int m = 0; m < attributeNames.size(); m++) {
                    row.createCell(m);
                }
            }

            //컬럼명 추가
            for (int i = 0; i < attributeNames.size(); i++)
                sheet.getRow(0).getCell(i).setCellValue(attributeNames.get(i));

            // 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //static method, singleton
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            System.out.println("[루트]: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("book");
            List<Integer> NoList = new ArrayList<>();
            List<String> titleList = new ArrayList<>();
            List<String> categoryList = new ArrayList<>();
            List<String> langList = new ArrayList<>();
            List<String> authorList = new ArrayList<>();
            List<String> yearList = new ArrayList<>();
            List<String> priceList = new ArrayList<>();

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

            int j = 0;
            for (int i = 1; i <= 10; i++) {
                sheet.getRow(i).getCell(0).setCellValue(NoList.get(j));
                sheet.getRow(i).getCell(1).setCellValue(titleList.get(j));
                sheet.getRow(i).getCell(2).setCellValue(categoryList.get(j));
                sheet.getRow(i).getCell(3).setCellValue(langList.get(j));
                sheet.getRow(i).getCell(4).setCellValue(authorList.get(j));
                sheet.getRow(i).getCell(5).setCellValue(yearList.get(j));
                sheet.getRow(i).getCell(6).setCellValue(priceList.get(j));
                if (j <= NoList.size()) {
                    j++;
                }
            }
            //System.out.println("한 행 삽입 완료");

            bufferedInputStream.close();
            socket.close();
            serverSocket.close();


            //    workbook.write(new FileOutputStream("D:\\d_other\\upload\\socket_excel.xlsx"));
            workbook.write(new FileOutputStream("C:\\test\\upload\\socket_excel.xlsx"));
            System.out.println("[엑셀 쓰기 완료]");
            System.out.println("----------------------");

            serverSocket2 = new ServerSocket(8888);
            socket2 = serverSocket2.accept();
            dataOutputStream = new DataOutputStream(socket2.getOutputStream());
            dataOutputStream.writeUTF("SUCCESS");


        } catch (IOException e) {
            e.printStackTrace();
            dataOutputStream.writeUTF("FAIL");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            dataOutputStream.writeUTF("FAIL");
        } catch (SAXException e) {
            e.printStackTrace();
            dataOutputStream.writeUTF("FAIL");
        } finally {
            dataOutputStream.flush();
            dataOutputStream.close();
            serverSocket2.close();
            socket2.close();
        }


    }


}