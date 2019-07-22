package Net05;

public class StudentSide {
    public static void main(String[] args) {
        new Thread(new TalkSender(8888, "localhost", 9999)).start();

        new Thread(new TalkReceiver(6666, "老师")).start();
    }
}
