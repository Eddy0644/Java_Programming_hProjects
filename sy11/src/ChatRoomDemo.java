import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoomDemo {
    public static void main(String[] args) {
        new ChatRoomServer("SRV - 0", 23001);
        new ChatRoomClient("CLI - 1", 23001, 1);
    }
}

class ChatRoomServer extends JFrame {
    private final ArrayList<ResponseThread> clientList = new ArrayList<>();
    String hostname;
    JTextArea textArea;     // For dual-direction messages
    JTextField textField;   // Message waiting to be sent
    ServerSocket server;
    PrintWriter pw;

    public ChatRoomServer(String name, int port) {
        super("ChatServer " + name);
        this.hostname = name;
        paintComponent();
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new ServerThread()).start();
        textArea.append("Server is online.\n");

        this.setSize(380, 470);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void sendAll(String msg) {
        if (!"".equals(msg)) for (ResponseThread rt : clientList) {
            rt.send(msg);
        }
    }

    private void paintComponent() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new FlowLayout());

        textArea = new JTextArea(18, 36);
        Font testfont = new Font("Consolas", Font.PLAIN, 16);
        textArea.setFont(testfont);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(scrollPane);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(350, 30));
        contentPane.add(textField);

        JButton sendBtn = new JButton("Send");
        sendBtn.addActionListener(e -> {
            String msg = textField.getText();
            textArea.append(hostname + ": " + msg + "\n");
            sendAll(hostname + ": " + msg);
            textField.setText("");
        });
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> textArea.setText(""));

        contentPane.add(sendBtn);
        contentPane.add(clearBtn);
    }

    class ServerThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                Socket client = null;
                try {
                    client = server.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("A client connected.");
                ResponseThread c = new ResponseThread(client);
                clientList.add(c);
                new Thread(c).start();
            }
        }
    }

    class ResponseThread implements Runnable {
        Socket client;
        BufferedReader br;

        public ResponseThread(Socket client) {
            this.client = client;
            try {
                br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void send(String msg) {
            try {
                pw = new PrintWriter(this.client.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pw.println(msg);
            pw.flush();
        }

        @Override
        public void run() {
            while (true) {
                String msg = null;
                try {
                    msg = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (msg != null && !"".equals(msg.trim())) {
                    textArea.append(msg + "\n");
                    sendAll(msg);
                }
            }
        }
    }
}

class ChatRoomClient extends JFrame {
    String hostname;
    JTextArea textArea;     // For dual-direction messages
    JTextField textField;   // Message waiting to be sent
    PrintWriter pw;
    Socket socket;

    public ChatRoomClient(String name, int port, int pos) {
        super("ChatClient " + name);
        this.hostname = name;
        paintComponent();

        connectWithServer(port);
        new Thread(new ReceiveThread(socket)).start();


        this.setBounds(380 * pos, 0, 380, 470);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void connectWithServer(int port) {
        try {
            socket = new Socket("127.0.0.1", port);
            textArea.append(this.hostname + " Connected With Server!\n");
            pw = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void paintComponent() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new FlowLayout());

        textArea = new JTextArea(18, 36);
        Font testfont = new Font("Consolas", Font.PLAIN, 16);
        textArea.setFont(testfont);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(scrollPane);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(350, 30));
        contentPane.add(textField);

        JButton sendBtn = new JButton("Send");
        sendBtn.addActionListener(e -> {
            String msg = textField.getText();
            // As our message would also appear in server broadcast, comment this to avoid duplicate.
//            textArea.append(hostname + ": " + msg + "\n");
            textField.setText("");
            pw.println(hostname + ": " + msg);
            pw.flush();
        });
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> textArea.setText(""));

        contentPane.add(sendBtn);
        contentPane.add(clearBtn);
    }

    class ReceiveThread implements Runnable {
        BufferedReader br;  //负责读出数据
        private Socket client;

        public ReceiveThread(Socket client) {
            this.client = client;
            try {
                InputStreamReader isr = new InputStreamReader(this.client.getInputStream());
                br = new BufferedReader(isr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                //接收数据
                try {
                    String msg = br.readLine();
                    if (!"".equals(msg.trim())) {
                        textArea.append(msg + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}