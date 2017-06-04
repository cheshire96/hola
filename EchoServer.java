package socket;

import java.io.*;
import java.net.*;


//-------------------
//|		SOCKET		|
//-------------------
class MyStreamSocket extends Socket {
	private Socket  socket; //socket de atencion al cliente
	private BufferedReader recibe;//lo que el cliente manda/recibe el servidor
	private PrintWriter respuesta;//lo que el servidor va a responder al cliente

	
	//constructor del socket que tiene como parametro otro socket
	MyStreamSocket(Socket socket)  throws IOException {
		this.socket = socket;
		setStreams( );//setea lo que reciba el socket y la respuesta que se envia
	}

  
  	//setea lo que se manda y recibe
	private void setStreams( ) throws IOException{
	   
	    InputStream inStream = socket.getInputStream();
	    recibe = new BufferedReader(new InputStreamReader(inStream));//se lee lo que recibe y lo guarda en un buffer
	   
	    OutputStream outStream = socket.getOutputStream();
	    respuesta = new PrintWriter(new OutputStreamWriter(outStream));//escribe la respuesta
	}

	
	public void sendMessage(String message) throws IOException {	
		respuesta.println(message);   //imprime la respuesta por la salida, es decir se lo manda al cliente
		respuesta.flush();               
	} //Finaliza el envio del mensaje al cliente

	
	public String receiveMessage( ) throws IOException {	
		//lee una linea de la informacion recibida desde el cliente(lee de lo que manda el cliente)
		String message = recibe.readLine( );  
		return message;
  	} //Finaliza la recepcion del mensaje que envio el cliente al servidor
} //Termina clase socket de atencion





//--------------------------
//|		SERVIDOR ECHO		|
//--------------------------


public class EchoServer {
  
	public static final String CERRAR = "."; 	//indica con que caracter se termina la conexion
	public static final int PUERTO=10007; 		// numero de puerto donde se presta el servicio
  
	
	//inicia el main del servidor
	public static void main(String[] args) {
	        
	    try {
	    	//se instancia un socket para aceptar conexiones
	    	ServerSocket s = new ServerSocket(PUERTO); 
	    	System.out.println("Se inicio el servidor.");  
	    	while (true) {  //bucle infinito
	    		//se espera una conexion 
	    		System.out.println("Se espera una conexion.");
	    		//conexion aceptada
	    		MyStreamSocket ns = new MyStreamSocket (s.accept( ));//nuevo socket de atencion
	    		System.out.println("Conexion Aceptada");
		    		
	    //---------------------------
	    //|		Servicio echo		|
	    //---------------------------
	    		Echo(ns);
	    		
	    	} //Termina bucle infinito
	    } // end try
	    catch (Exception ex) {
	       ex.printStackTrace( );
	    }
	} //Termina Main Server
	
	
	
	
    //---------------------------
    //|		SERVICIO ECHO		|
    //---------------------------

	
	public static void Echo(MyStreamSocket ns) throws IOException{
		
		boolean finalizar = false;//no finalizo la conexion
		 String mensaje;//mensaje 
		 
		//se le informa al cliente como debe cerrar la conexion
		ns.sendMessage("Para cerra la conexion enviar . "+CERRAR);
		
		//hacer mientras la conexion no finalice
		while (!finalizar) {
			//recibe el mensaje
			mensaje = ns.receiveMessage( );
			//System.out.println("message received: "+ message);
			
			//si el mensaje no es un solo punto entonces se cierra la conexion
			if ((mensaje.trim()).equals (CERRAR)){
				
				System.out.println("Conexion Cerrada");
				
				ns.close( );//se cierra conexion
				finalizar = true;//finalizar ahora es verdadero para poder salir del bucle
			} 
			else {//si no se manda el punto entonces
				//se contesta al cliente con el echo
				ns.sendMessage(mensaje);
			} 
		} //Termina bucle while(!finalizar) por que finalizar es verdadero
		//se deja de atender al cliente
		
	}//Termina metodo Echo 
	
	
	
} // termina clase Server