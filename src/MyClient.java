import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClient implements Runnable {

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

        int mouseUpX = 0, mouseUpY = 0;
        int mouseDownX = 0, mouseDownY = 0;
        int mouseLeftX = 0, mouseLeftY = 0;
        int mouseRightX = 0, mouseRightY = 0;
        int mouseClickX = 0, mouseClickY = 0;
        int mouseSpaceX = 0, mouseSpaceY = 0;
        int mouseEnterX = 0, mouseEnterY = 0;

        int textRed = 0, textGreen = 0, textBlue = 0;

        if (clientSocket != null && os != null && is != null) {
            try {
                Robot robot = new Robot();
                new Thread(new Client()).start();
                while (!closed) {
                    os.println(inputLine.readLine().trim());
                    Color mouseUp = robot.getPixelColor(mouseUpX, mouseUpY);
                    Color mouseDown = robot.getPixelColor(mouseDownX, mouseDownY);
                    Color mouseLeft = robot.getPixelColor(mouseLeftX, mouseLeftY);
                    Color mouseRight = robot.getPixelColor(mouseRightX, mouseRightY);
                    Color mouseClick = robot.getPixelColor(mouseClickX, mouseClickY);
                    Color mouseSpace = robot.getPixelColor(mouseSpaceX, mouseSpaceY);
                    Color mouseEnter = robot.getPixelColor(mouseEnterX, mouseEnterY);

                    if ((mouseUp.getRed() == textRed && mouseUp.getGreen() == textGreen && mouseUp.getBlue() == textBlue))
                        os.println("mouseUp");
                    else if ((mouseDown.getRed() == textRed && mouseDown.getGreen() == textGreen && mouseDown.getBlue() == textBlue))
                        os.println("mouseDown");
                    else if ((mouseLeft.getRed() == textRed && mouseLeft.getGreen() == textGreen && mouseLeft.getBlue() == textBlue))
                        os.println("mouseLeft");
                    else if ((mouseRight.getRed() == textRed && mouseRight.getGreen() == textGreen && mouseRight.getBlue() == textBlue))
                        os.println("mouseRight");
                    else if ((mouseClick.getRed() == textRed && mouseClick.getGreen() == textGreen && mouseClick.getBlue() == textBlue))
                        os.println("mouseClick");
                    else if ((mouseSpace.getRed() == textRed && mouseSpace.getGreen() == textGreen && mouseSpace.getBlue() == textBlue))
                        os.println("mouseSpace");
                    else if ((mouseEnter.getRed() == textRed && mouseEnter.getGreen() == textGreen && mouseEnter.getBlue() == textBlue))
                        os.println("mouseEnter");

                }
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            } catch (AWTException e) {
                throw new RuntimeException(e);
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
                /*
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
                */
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}