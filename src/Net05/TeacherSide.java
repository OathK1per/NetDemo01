package Net05;

public class TeacherSide {
    public static void main(String[] args) {
        new Thread(new TalkReceiver(9999,"学生")).start();

        new Thread(new TalkSender(7777, "localhost", 6666)).start();
    }
}
