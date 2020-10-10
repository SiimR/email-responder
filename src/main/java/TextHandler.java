import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class TextHandler {
    public HashMap<String, List<String>> topic = new HashMap<>();
    public HashMap<String, String> response = new HashMap<>();

    public TextHandler() {
        topic.put("financial service", Arrays.asList("credit", "loan", "lend", "trust", "grant", "donate", "investment", "invest", "mortgage", "payment"));
        topic.put("bad health", Arrays.asList("illness", "cancer", "sickness", "disease", "illness", "disorder", "infection", "heart", "heart disease", "stroke", "corona", "ebola", "tuberculosis", "alzheimer"));
        topic.put("personal information", Arrays.asList("bank account number", "bank account", "name", "address", "age", "phone number", "telephone number", "mobile Number", "country", "country of residence", "residence address"));

        response.put("bad health", "I am sorry to hear about your health. I want to help you! But please tell me how.");
        response.put("personal information", "I am a german who lives in Estonia. Which details should I provide? The ones related with Estonia or Germany? Which ones to you care about?");
        response.put("financial service", "Before we start with the contract. Can I get more information on the terms? What time periods are we talking about?");
        response.put("short", "Please write a bit longer. No much to say from that letter.");
        response.put("default", "Sorry, I am kinda in a hurry now. But can you explain the situation a bit more? I quickly read it and the situation felt rather odd. \nPS! I get back do you in the evening or the morning after that. I hope to see your letter then!");
    }

    public String handleText(String text) {
        // Count the occurrences of words related with personal information to verify that scammer is asking for personal data
        int personalInfoCount = 0;
        for (String aTopic : topic.keySet()) {
            for (String specificTopic : topic.get(aTopic)) {
                String pattern = "\\b"+specificTopic+"\\b";
                Pattern p = Pattern.compile(pattern);

                if (p.matcher(text).find()) {
                    if (!aTopic.equals("personal information") || personalInfoCount > 2) return response.get(aTopic);
                    else personalInfoCount++;
                }

            }
        }
        if (text.length() < 150) return response.get("short");
        return response.get("default");
    }
}
