package com.itheima.learn_01;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientDemoFile {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket(InetAddress.getByName("DESKTOP-BOVMBAC"),10000);
        BufferedReader br = new BufferedReader(new FileReader("user.txt"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        String line;
        while ((line= br.readLine())!=null){
            bw.write(line);
            bw.newLine();  // 为什么要这个
            bw.flush();
        }
        s.shutdownOutput();
        BufferedReader brClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String data = brClient.readLine();
        System.out.println("服务器的反馈，"+data);
        br.close();
        s.close();
    }
}
