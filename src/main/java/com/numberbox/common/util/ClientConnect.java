package com.numberbox.common.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.springframework.web.multipart.MultipartFile;

public class ClientConnect {
	
    private Socket socket = null;
    private FileOutputStream fos = null;
    private DataOutputStream  dos = null;
    private FileInputStream fin = null;
    private DataInputStream din = null;
    private PrintStream pout = null;
    private Scanner scan = null;

    public ClientConnect(String customSocketIp) throws IOException {
        socket=new Socket(customSocketIp, 5555);  
        scan = new Scanner(System.in);
        din = new DataInputStream(socket.getInputStream());
        pout = new PrintStream(socket.getOutputStream());
    }

    public void send(String msg) throws IOException {
    	//모드 작성(모드 바이트 크기는 4)
        pout.print("SEND");
        pout.flush();
    	
    	byte[] data = msg.getBytes();
    	//데이터 크기 작성(4바이트 할당)
    	ByteBuffer b = ByteBuffer.allocate(4);
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.putInt(data.length);
        pout.write(b.array(), 0, 4);
        
        //메시지 작성
        pout.print(msg);
        pout.flush();
    }
    
    public HashMap<String, Object> sendFile(MultipartFile multipartFile, String path) throws IOException {
    	//모드 작성(모드 바이트 크기는 4)
        pout.print("FILE");
        pout.flush();
    	
    	//MultipartFile to File
    	CommonUtil util = new CommonUtil();
    	File file = util.convertMultipartFileToFile(multipartFile, path+"hwpToHtml/");
		
    	//데이터 크기 작성(4바이트 할당)
		byte[] data = new byte[(int)file.length()];
		ByteBuffer bufferSize = ByteBuffer.allocate(4);
		bufferSize.order(ByteOrder.LITTLE_ENDIAN);
		bufferSize.putInt(data.length);
		dos = new DataOutputStream(socket.getOutputStream());
		dos.write(bufferSize.array(), 0, 4);
		
		//확장자 작성(확장자 바이트 크기는 4)
		//1: hwp, 2: hwpx, 3: hwt, 4: hwtx, 5: hml
		String fileName = file.getName();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		int extNum = 1;
		if(ext.equals("hwp")) {
			extNum = 1;
		}else if(ext.equals("hwpx")) {
			extNum = 2;
		}else if(ext.equals("hwt")) {
			extNum = 3;
		}else if(ext.equals("hwtx")) {
			extNum = 4;
		}else if(ext.equals("hml")) {
			extNum = 5;
		}
		ByteBuffer extBufferSize = ByteBuffer.allocate(4);
		extBufferSize.order(ByteOrder.LITTLE_ENDIAN);
		extBufferSize.putInt(extNum);
		dos = new DataOutputStream(socket.getOutputStream());
		dos.write(extBufferSize.array(), 0, 4);
		
		//파일 작성
		fin = new FileInputStream(file);
		int length;
		byte[] buffer = new byte[1024];
		while((length = fin.read(buffer))!=-1) {
			dos.write(buffer,0,length);
			dos.flush();
		}
		fin.close();
		file.delete();
		
		//파일 받기
		Random random1 = new Random();
    	long currentTime1 = System.currentTimeMillis();
		int randomValue1 = random1.nextInt(100);

		String newFileName = Long.toString(currentTime1) + "_"+randomValue1+"_hwpToHtml.zip";
		File zipfile = new File(path+"hwpToHtml/", newFileName);
        // Create new file if it does not exist
        // Then request the file from server
        if(!zipfile.exists()){
        	zipfile.createNewFile();
        }
        fos = new FileOutputStream(zipfile);
        
        String unzipPath="";
		try {// Get content in bytes and write to a file
            byte[] defaultBuffer = new byte[8192];
            for(int counter=0; (counter = din.read(defaultBuffer, 0, defaultBuffer.length)) >= 0;) {
                    fos.write(defaultBuffer, 0, counter);
            }
            fos.flush();
            fos.close();
            
            unzipPath = util.unZip(path+"hwpToHtml/", newFileName, path+"hwpToHtml/");
            zipfile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("unzipPath", unzipPath);
		return map;
    }

    public String recv() throws IOException {
        byte[] bytes = new byte[1024];
        din.read(bytes);
        String reply = new String(bytes, "UTF-8");
        return reply;
    }

    public void closeConnections() throws IOException {
        // Clean up when a connection is ended
        socket.close();
        din.close();
        pout.close();
        scan.close();
    }

    public void chat() throws IOException  {    
        String response = "s";
        while(!response.equals("QUIT")){
            String message = scan.nextLine();
            send(message);
            if(message.equals("QUIT"))
                break;
            response = recv();
        }
        closeConnections();
    }

    // Request a specific file from the server
    public String getFile(String path, String jsonStr) {
    	Random random1 = new Random();
    	long currentTime1 = System.currentTimeMillis();
		int randomValue1 = random1.nextInt(100);

		String newFileName = Long.toString(currentTime1) + "_"+randomValue1+"_나의제작문제.hwp";
        try {
            File file = new File(path, newFileName);
            // Create new file if it does not exist
            // Then request the file from server
            if(!file.exists()){
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            //보낼 데이터의 크기를 먼저 보낸다.
	       //데이터를 보낸다.
            send(jsonStr);

            // Get content in bytes and write to a file
            byte[] buffer = new byte[8192];
            for(int counter=0; (counter = din.read(buffer, 0, buffer.length)) >= 0;) {
                    fos.write(buffer, 0, counter);
            }
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFileName;
    }
    
    public void saveFile() throws IOException {
        InputStream in = socket.getInputStream();
        //바이트 단위로 데이터를 읽는다, 외부로 부터 읽어들이는 역할을 담당
        BufferedInputStream bis = new BufferedInputStream(in);
        //파일을 읽는 경우라면,BufferedReader보다 BufferedInputStream이 더 적절하다.
        FileOutputStream fos = new FileOutputStream("testfile2.hwp");
        //파일을 열어서 어떤식으로 저장할지 알려준다. FileOutputStream을 쓰면 들어오는 파일과 일치하게 파일을 작성해줄 수 있는 장점이 있다.
        int ch;
        while ((ch = bis.read()) != -1) {
            fos.write(ch);
            //열린 파일시스템에 BufferedInputStream으로 외부로 부터 읽어들여온 파일을 FileOutputStream에 바로 써준다.
        }
	    fos.close();
	    in.close();
    }
    
}