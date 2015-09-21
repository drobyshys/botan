package org.vs_07.botan;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.vs_07.botan.interpreters.ChatInterpreter;
import org.vs_07.botan.interpreters.CurrencyInterpreter;
import org.vs_07.botan.interpreters.Interpreter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 */
public class App {

    private static RequestHandler handler = new RequestHandler(new ArrayList<Interpreter>() {
        {
            add(new CurrencyInterpreter());
            add(new ChatInterpreter());
        }
    });

    public static void main(String[] args) throws IOException, InterruptedException {
        final SlackSession session = SlackSessionFactory.
                createWebSocketSlackSession("xoxb-10423833012-Hnv53XFl1y4N6UjkJARY5Ywr");


        session.addMessagePostedListener(new SlackMessagePostedListener() {
            public void onEvent(SlackMessagePosted event, SlackSession session) {

                String message = event.getMessageContent();
                System.out.println("Received: " + message);
                session.sendMessage(event.getChannel(),
                        handler.handle(message, event.getSender().getUserName()), null);

                /*try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                //2 secs later, let's update the message (I can only update my own messages)
                session.updateMessage(handle.getSlackReply().getTimestamp(),event.getChannel(),
                        event.getMessageContent()+" UPDATED");
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                //2 secs later, let's now delete the message (I can only delete my own messages)
                session.deleteMessage(handle.getSlackReply().getTimestamp(),event.getChannel());*/
            }
        });
        session.connect();

        while (true) {
            Thread.sleep(1000);
        }
    }

}
