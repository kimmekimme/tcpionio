package ioecho;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5001);
        System.out.println(serverSocket + ": 서버 소켓 생성");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket + ": 연결됨");

            InputStream inputStream = socket.getInputStream();
            //DataInputStream dataInputStream = new DataInputStream(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            //DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            byte[] buf = new byte[1024];
            int count;

            while ((count = inputStream.read(buf)) != -1) {
                outputStream.write(buf,0,count);
                System.out.write(buf,0,count);
            }

            outputStream.close();
            System.out.println(socket + ": 연결 종료");

            socket.close();

        }
    }



}