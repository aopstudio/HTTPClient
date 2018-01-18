package top.neusoftware.HTTPClinet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket s;
	PrintStream writer;
	InputStream in;
	InputStreamReader reader;
	
	public void Client() throws UnknownHostException, IOException {
		s=new Socket("127.0.0.1",8888);
		writer=new PrintStream(s.getOutputStream());
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
	
	public void receiveResponse() throws IOException {
		char ch;
		StringBuilder res=new StringBuilder();
		String firstLineOfResponse;
		String secondLineOfResponse;
		String threeLineOfResponse;
		String fourLineOfResponse;

		while((ch=(char) reader.read())!='\n') {
			res.append(ch);
		}
		res.append(ch);
	}
}
