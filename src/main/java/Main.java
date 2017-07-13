import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

public class Main {
  public static void main(String... args) throws Exception {

        String botAuthToken = "your slack bot auth token";
        SlackSession slackSession = SlackSessionFactory.createWebSocketSlackSession(botAuthToken);

        slackSession.connect();
        // come back message
        slackSession.getChannels()
                .stream()
                .forEach(ch -> {
                  ch.getMembers()
                          .stream()
                          .forEach(m -> {
                            if(m.isBot()) {
                              slackSession.sendMessage(ch, "I'm back");
                            }
                          });
        });


        slackSession.addMessagePostedListener((event, session) -> {

          if(!event.getSender().isBot()) {
            session.sendMessage(event.getChannel(), event.getSender().getRealName() + " I'm running");
          }

        });


  }
}
