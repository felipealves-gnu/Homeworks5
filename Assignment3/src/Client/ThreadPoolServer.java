package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.UUID;

public class ThreadPoolServer implements Runnable {
    public Socket socket;
    public ObjectInputStream sInput;
    String answer;
    CounterInter ci;
    UUID id;
    StoreReturnValues srv;

    public ThreadPoolServer(Socket socket) {
        this.socket = socket;
        System.out.println("\nThreadpool created and assigned taks to do e.g. Object Input/Output Streams");
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            srv = new StoreReturnValues();
        } catch (IOException e) {
            System.out.println("\nException creating new Input/Output Streams: " + e);
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for clients input to write into file and console");
                answer = sInput.readUTF();
                id = (UUID) sInput.readObject();
                System.out.println(id + answer);
                //                ci = (CounterInter) sInput.readObject();
            } catch (IOException e) {
                System.out.println("Exception reading Streams (IOE or ClassNF):  " + ci.getId() + " " + e.getMessage() + " " + e.toString());
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                ci.setUUIDandAnswer(id, answer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            try {
                srv.store(ci);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Exception write Streams to file:  " + ci.getId() + " " + e.getMessage() + " " + e.toString());
                break;
            }


            //works
            System.out.println("\n Client is on the server says " + ci.getId() + " voted as " + ci.getAnswer());
        }
    }

}
