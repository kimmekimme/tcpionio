package iofile;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    DataOutputStream dataOutputStream;
    BufferedOutputStream bufferedOutputStream;
    BufferedInputStream bufferedInputStream;
    DataInputStream dataInputStream;
    InputStream inputStream;
    Socket socket;
    BufferedWriter bufferedWriter;

    protected void clientStart() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("전송할 파일의 경로를 입력하세요: ");
        String filePath = scanner.nextLine();
        System.out.print("전송할 파일의 이름을 입력하세요: ");
        String fileName = scanner.nextLine();
        File file = new File(filePath + "\\" + fileName);

        if (file == null) {
            System.out.println("전송할 파일을 선택 하세요. 작업을 중단합니다.");
            return;
        }

        System.out.println("전송한 파일명: " + file.getName());

        if (!file.exists()) {
            System.out.println(fileName + "파일이 없습니다. 작업을 중단합니다.");
        }

        try {
            socket = new Socket("localhost", 7777);
            System.out.println("[파일 전송 시작]");


            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedOutputStream = new BufferedOutputStream(dataOutputStream);

            dataOutputStream.writeUTF(fileName);

            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, length);
            }

            System.out.println("[파일 전송 완료]");
            System.out.println("---------------------------------");

            //결과 리턴 받기
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            String message = dataInputStream.readUTF();
            System.out.println("[서버로부터 온 메세지] " + message);
            bufferedWriter = new BufferedWriter(new FileWriter("C:\\test\\result.txt"));
            bufferedWriter.write(message);
            bufferedWriter.flush();


        } catch (IOException e) {
            System.out.println("[파일 전송 실패] " + e.getMessage());
        } finally {
            bufferedOutputStream.flush();
            bufferedInputStream.close();
            bufferedOutputStream.close();
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
            bufferedWriter.close();

        }

    }

    public static void main(String[] args) throws IOException {
        while (true) {
            new Client().clientStart();
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.print("계속 보내려면 엔터, 종료하려면 exit 라고 입력하세요: ");
            String line = scanner.nextLine();
            System.out.println();

            if (line.equals("exit")) {
                break;
            }

        }
    }
}