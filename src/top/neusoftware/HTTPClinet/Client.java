package top.neusoftware.HTTPClinet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket s;
	private PrintWriter writer;
	private InputStream in;
	private InputStreamReader reader;
	String saveLocation="D:/FTCache";
	
	public Client() throws UnknownHostException, IOException {
		s=new Socket("127.0.0.1",8888);
		writer=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		in=s.getInputStream();
		reader=new InputStreamReader(in);
	}
	
	public void sendRequest(String fileName) {
		writer.println("GET /"+fileName+" HTTP/1.1");
		writer.println("Host:localhost");
		writer.println("connection:keep-alive");
		writer.println();
		writer.flush();
	}
	
	public void receiveResponse(String fileName) throws IOException {
		char ch='\0';
		BufferedReader stringReader = new BufferedReader(new InputStreamReader(in));
		StringBuilder res=new StringBuilder();
		String statusCode;
		String firstLineOfResponse;
		while((ch=(char) reader.read())!='\n') {
			res.append(ch);
		}
		res.append(ch);		
		firstLineOfResponse=res.toString();
		statusCode=firstLineOfResponse.split(" ")[1];//第一行格式是HTTP/1.1 200 OK或者HTTP/1.1 404 Not Found，
		//statusCode就是中间那个数字
		if(statusCode.equals("200")) {
			while(true) {
				if(ch=='\n'&(ch=(char)reader.read())=='\r')
					break;//读到前一个\n后面紧接着\r，代表是空行，跳出循环去读后面的具体文件内容
			}
			byte[] b = new byte[1024];
			OutputStream out = new FileOutputStream(saveLocation+"/"+fileName);
			int len;//读后面的数据
			while((len=in.read(b))!=-1)
			{		    	   
				out.write(b, 0, len);
				out.flush();
			}
			in.close();
			out.close();
			
		}
		else if(statusCode.equals("404")) {
			StringBuffer result = new StringBuffer();
			String line;
			while ((line = stringReader.readLine()) != null) {
					result.append(line);
			}
			reader.close();
			System.out.println(result);
		}
		
	}
}
