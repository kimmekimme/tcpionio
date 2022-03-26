package ioecho;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5001);
        System.out.println(socket + ": 연결됨");

        OutputStream outputStream = socket.getOutputStream();
        //DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        InputStream inputStream = socket.getInputStream();
        //DataInputStream dataInputStream = new DataInputStream(inputStream);

        byte[] buf = new byte[1024];
        int count;

        while ((count = System.in.read(buf)) != -1) {
            outputStream.write(buf, 0, count);
            count = inputStream.read(buf);
            System.out.write(buf, 0, count);
        }

        outputStream.close();
        System.out.println(socket + ": 연결 종료");
        socket.close();

    }
}