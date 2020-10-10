import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static HashMap<String, List<String>> emailArchive = new HashMap<>();

    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println("Arguments from command line!");
            System.out.println(emailHandler(args[0], args[1]));
        } else {
            TestEmails testEmails = new TestEmails();
            for (String sender: testEmails.getTestMails().keySet()) {
                String text = testEmails.getTestMails().get(sender);
                System.out.println(emailHandler(sender, text));
            }
        }
    }

    public static String emailHandler(String emailSender, String emailText) {
        String senderFromArchive = getEmailFromArchive(emailSender);
        List<String> previousMessages = emailArchive.get(senderFromArchive);
        if(previousMessages != null) previousMessages.add(emailText);
        else previousMessages = new ArrayList<>();

        TextHandler handler = new TextHandler();
        String newText = handler.handleText(emailText);

        emailArchive.put(senderFromArchive, previousMessages);

        return newEmail(emailSender, newText);
    }

    private static String getEmailFromText(String emailText) {
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(emailText);
        return m.find() ? "" : m.group();
    }


    public static String newEmail(String emailTo, String emailText) {
        String finalEmail = "\nHello! \n\n" + emailText + "\n\nSincerely \n" + "Paul\n";
        return "TO: " + emailTo + "\n" + "TEXT: " + finalEmail;
    }

    public static String getEmailFromArchive(String emailSender) {
        for (String senders: emailArchive.keySet()) {
            if (senders.contains(emailSender)) return senders;
        }
        return "";
    }
}
