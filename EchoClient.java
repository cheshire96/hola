package socket;

import java.io.*;
import java.net.*;

public class EchoClient {
	
	public static final int PUERTO=10007; 		// numero de puerto donde se presta el servicio
	  
    public static void main(String[] args) throws IOException {

        String serverHostname = new String ("127.0.0.1");

        if (args.length > 0)
           serverHostname = args[0];
        System.out.println ("Esperando conectar con el host " +
		serverHostname + " en el puerto 10007.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, PUERTO);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("No se conoce el host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se puede obtener una coneccion con " + serverHostname);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	String userInput;
	System.out.println("Servidor: " + in.readLine());
        System.out.print ("Mandar: ");
	while ((userInput = stdIn.readLine()) != null) {
	    out.println(userInput);
	    System.out.println("Respuesta Servidor: " + in.readLine());
            System.out.print ("Mandar: ");
	}

	out.close();
	in.close();
	stdIn.close();
	echoSocket.close();
    }
}
