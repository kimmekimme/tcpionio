package iofile;

import org.apache.poi.ss.usermodel.Workbook;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyVetoException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private static ExcelWriter excelWriter; //엑셀 라이터
    private static Parser parser; //파서
    private static ServerSocket serverSocket; //서버소켓
    private static Socket socket; //소켓
    private static DataInputStream dataInputStream;
    private static BufferedInputStream bufferedInputStream;
    private static BufferedOutputStream bufferedOutputStream;
    private static DataOutputStream dataOutputStream;
    private static String pathName; //파일명
    private static File saveDirectory; //저장 경로
    private static File file; //받은 파일
    private static String fileName; //받은 파일명
    private static OutputStream outputStream;

    public static File getFile() {
        return file;
    }

    public static void setPathName(String pathName) {
        Server.pathName = pathName;
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        setPathName("c:/test/upload/");

        saveDirectory = new File(pathName);

        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }

        while (true) {
            try {
                //서버소켓 생성 -> 어셉트 -> 소켓 생성 -> 파일 받기
                serverSocket = new ServerSocket(7777);
                System.out.println("[서버 대기 상태]");
                socket = serverSocket.accept();
                System.out.println("[파일 저장 시작]");

                dataInputStream = new DataInputStream(socket.getInputStream());
                bufferedInputStream = new BufferedInputStream(dataInputStream);

                fileName = dataInputStream.readUTF();
                outputStream = socket.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF("SUCCESS");
                System.out.println();
                file = new File(saveDirectory, fileName);

                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));

                byte[] bytes = new byte[1024];
                int length = 0;

                while ((length = bufferedInputStream.read(bytes)) != -1) {
                    bufferedOutputStream.write(bytes, 0, length);
                }

                bufferedOutputStream.flush();

                System.out.println("[받은 파일 저장 완료]: " + pathName + fileName);



                //파싱
                Parser.parse();
                System.out.println("[파싱 완료]");

                //엑셀 라이터
                excelWriter = new ExcelWriter();
                excelWriter.execute();
                excelWriter.write();
                System.out.println("[엑셀 저장 완료]");
                System.out.println("--------------------------------------");
                System.out.println();


            } catch (FileNotFoundException e) {
                dataOutputStream.writeUTF("FAIL");
                e.printStackTrace();
            } catch (IOException e) {
                dataOutputStream.writeUTF("FAIL");
                e.printStackTrace();
            } finally {
                bufferedOutputStream.close();
                dataOutputStream.close();
                bufferedInputStream.close();
                serverSocket.close();
                socket.close();
            }


        }

    }
}
