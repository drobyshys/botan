package org.vs_07.botan.interpreters;

import org.vs_07.botan.util.ResponseReader;

import java.util.List;
import java.util.Random;

/**
 * Created by mark.lacdao on 31/07/2015.
 */
public class ChatInterpreter implements Interpreter {

    private Random random = new Random();

    @Override
    public String interpret(String message){
        List<String> responses = ResponseReader.getByKeys(message);
        return getRandomResponse(responses);
    }

    @Override
    public String interpret(String message, String sender) {
        String reply = interpret(message);
        if(reply.contains("%s")) reply = String.format(reply, sender);
        return reply;
    }

    private String getRandomResponse(List<String> responses){
        int index = random.nextInt(responses.size());
        return responses.get(index);
    }

}
