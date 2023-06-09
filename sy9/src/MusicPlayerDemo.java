import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayerDemo extends JFrame {
    public PlayerCore player;
    public int curPicIndex = 0;
    public JLabel imageLabel;

    public MusicPlayerDemo() {
        super("Music Player Example");
        setSize(800, 900);
        setVisible(true);
        setLocation(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        paintComponents();

        player = new PlayerCore("./resource/test.wav");
        player.start(true);

    }

    public static void main(String[] args) {
        new MusicPlayerDemo();
    }

    void paintComponents() {
        //顶部轮播图
        imageLabel = new JLabel();
//        imageLabel.setIcon(new ImageIcon("./resource/b" + curPicIndex + ".png"));
        imageLabel.setBounds(50, 10, 700, 300);
        this.add(imageLabel);
        Timer timer = new Timer(2000, (x) -> {
            curPicIndex %= 5;
            curPicIndex++;
            imageLabel.setIcon(new ImageIcon("./resource/b" + curPicIndex + ".png"));
        });
        timer.start();
//        timer.schedule(new ChangeImageTask(this), 0, 1500);
    }

}

class PlayerCore {
    private final String musicPath; // 音频文件
    private volatile boolean run = true; // 记录音频是否播放
    private Thread mainThread; // 播放音频的任务线程

    private AudioInputStream audioStream;
    private SourceDataLine sourceDataLine;

    public PlayerCore(String musicPath) {
        this.musicPath = musicPath;
        prefetch();
    }

    // 数据准备
    private void prefetch() {
        try {
            // 获取音频输入流
            audioStream = AudioSystem.getAudioInputStream(new File(musicPath));
            // 获取音频的编码对象
            AudioFormat audioFormat = audioStream.getFormat();
            // 包装音频信息
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat,
                    AudioSystem.NOT_SPECIFIED);
            // 使用包装音频信息后的Info类创建源数据行，充当混频器的源
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }

    }

    // 析构函数:关闭音频读取流和数据行
    protected void destroy() throws Throwable {
        sourceDataLine.drain();
        sourceDataLine.close();
        audioStream.close();
    }

    // 播放音频:通过loop参数设置是否循环播放
    private void playMusic(boolean loop) throws InterruptedException {

        try {
            if (loop) {
                while (true) {
                    playMusic();
                }
            } else {
                playMusic();
                // 清空数据行并关闭
                sourceDataLine.drain();
                sourceDataLine.close();
                audioStream.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void playMusic() {
        try {
            synchronized (this) {
                run = true;
            }
            // 通过数据行读取音频数据流，发送到混音器;
            // 数据流传输过程：AudioInputStream -> SourceDataLine;
            audioStream = AudioSystem.getAudioInputStream(new File(musicPath));
            int count;
            byte[] tempBuff = new byte[1024];

            while ((count = audioStream.read(tempBuff, 0, tempBuff.length)) != -1) {
                synchronized (this) {
                    while (!run)
                        wait();
                }
                sourceDataLine.write(tempBuff, 0, count);
            }

        } catch (UnsupportedAudioFileException | IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // 暂停播放音频
    private void stopMusic() {
        synchronized (this) {
            run = false;
            notifyAll();
        }
    }

    // 继续播放音乐
    private void continueMusic() {
        synchronized (this) {
            run = true;
            notifyAll();
        }
    }

    // 外部调用控制方法:生成音频主线程；
    public void start(boolean loop) {
        mainThread = new Thread(() -> {
            try {
                playMusic(loop);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mainThread.start();
    }

    // 外部调用控制方法：暂停音频线程
    public void stop() {
        new Thread(this::stopMusic).start();
    }

    // 外部调用控制方法：继续音频线程
    public void continues() {
        new Thread(this::continueMusic).start();
    }

    // 播放器的状态
    public boolean isPlaying() {
        return run;
    }

    // 设置音频音量
    // https://zhidao.baidu.com/question/269020584.html
    public void setVol(float value) {
        // 必须open之后
        if (value != 7) {
            FloatControl control = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(value);
        }
    }

}