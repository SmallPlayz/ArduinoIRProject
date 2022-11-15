import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.*;

public class Client implements Runnable {

    private static Socket clientSocket = null;
    public static PrintStream os = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    private static String username="";

    public static void main(String []args) {

        int portNumber = 10334; //port
        String host = "localhost";

        System.out.println("Now using host = " + host + ", portNumber = " + portNumber);
        Scanner input=new Scanner (System.in);
        Graphics graphics = new Graphics();
        String message="";
        String full="";
        Boolean exit=false;

        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
            os.println(username);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (clientSocket != null && os != null && is != null) {
            try {
                new Thread(new Client()).start();
                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }

    }
    public void run() {
        String responseLine;
        final int mouseMove = 10;
        try {
            Robot robot = new Robot();
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if(responseLine.equalsIgnoreCase("mouseUp"))
                    robot.mouseMove((((int) MouseInfo.getPointerInfo().getLocation().getX())), (((int) MouseInfo.getPointerInfo().getLocation().getY())-mouseMove));
                else if(responseLine.equalsIgnoreCase("mouseDown"))
                    robot.mouseMove((((int) MouseInfo.getPointerInfo().getLocation().getX())), (((int) MouseInfo.getPointerInfo().getLocation().getY())+mouseMove));
                else if(responseLine.equalsIgnoreCase("mouseLeft"))
                    robot.mouseMove((((int) MouseInfo.getPointerInfo().getLocation().getX())-mouseMove), (((int) MouseInfo.getPointerInfo().getLocation().getY())));
                else if(responseLine.equalsIgnoreCase("mouseRight"))
                    robot.mouseMove((((int) MouseInfo.getPointerInfo().getLocation().getX())+mouseMove), (((int) MouseInfo.getPointerInfo().getLocation().getY())));
                else if(responseLine.equalsIgnoreCase("mouseClick")) {
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }
                else if(responseLine.equalsIgnoreCase("mouseSpace")) {
                    robot.keyPress(32);
                    try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
                    robot.keyRelease(32);
                }
                else if(responseLine.equalsIgnoreCase("mouseEnter")) {
                    robot.keyPress(13);
                    try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
                    robot.keyRelease(13);
                }
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
class Graphics {
    Graphics(){
        JFrame frame = new JFrame("ChatApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);
        frame.setVisible(true);

    }
}