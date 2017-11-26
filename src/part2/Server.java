package part2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import java.net.ServerSocket;
import java.io.File;
import javax.imageio.ImageIO;
import  java.awt.image.BufferedImage;


public class Server implements Runnable {

    private String name;
    private String port;

    private String routerIP;
    private String routerPort;

    private String imagePath; //path to jpg image file to send

    @Override
    public void run() {
        register();

        acceptClientCommunication();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRouterIP() {
        return routerIP;
    }

    public void setRouterIP(String routerIP) {
        this.routerIP = routerIP;
    }

    public String getRouterPort() {
        return routerPort;
    }

    public void setRouterPort(String routerPort) {
        this.routerPort = routerPort;
    }

    private void register() {
        try {
            Socket socket = new Socket(routerIP, Integer.parseInt(routerPort));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("register");
            printWriter.println(name);
            printWriter.println(InetAddress.getLocalHost().getHostAddress());
            printWriter.println(port);
            printWriter.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about router: " + routerIP + ":" + routerPort);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + routerIP + ":" + routerPort);
            System.exit(1);
        }
    }

    private void acceptClientCommunication() {
        //TODO Logic for the server actually accepting client connections and doing stuff goes here

        switch(getName()){
            case "imageServer"://send image
            {

                    //code not working
               /* BufferedImage image = ImageIO.read(new File("test.jpg"));
                try (ServerSocket serv = new ServerSocket(getPort())) {
                    System.out.println("waiting...");
                    try (Socket socket = serv.accept()) {
                        System.out.println("client connected");
                        ImageIO.write(image, "jpg", socket.getOutputStream());
                        System.out.println("sent");

                    }
                }*/
            }
                break;
            case "textServer"://send text
            {

            }
                break;
            case "audioServer"://send audio
            {

            }
                break;

        }

               // socket.close;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setName("Test Server");
        server.setPort("5655");
        server.setRouterIP("127.0.0.1");
        server.setRouterPort("5555");

        Server serverImage = new Server();
        server.setName("imageServer");
        server.setPort("5656");
        server.setRouterIP("127.0.0.1");
        server.setRouterPort("5555");

        Server serverText = new Server();
        server.setName("textServer");
        server.setPort("5657");
        server.setRouterIP("127.0.0.1");
        server.setRouterPort("5555");

        Server serverAudio = new Server();
        server.setName("audioServer");
        server.setPort("5658");
        server.setRouterIP("127.0.0.1");
        server.setRouterPort("5555");

        new Thread(server).start();
        new Thread(serverImage).start();
        new Thread(serverText).start();
        new Thread(serverAudio).start();
    }
}
