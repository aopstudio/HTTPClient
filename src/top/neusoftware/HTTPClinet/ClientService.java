package top.neusoftware.HTTPClinet;

import java.io.IOException;

public class ClientService {
	public static void main(String[] args) throws IOException {
		Client cli=new Client();
		String fileName="cookie.txt";
		cli.sendRequest(fileName);
		cli.receiveResponse(fileName);
	}
}
