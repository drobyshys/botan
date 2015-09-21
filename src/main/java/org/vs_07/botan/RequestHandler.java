package org.vs_07.botan;

import org.vs_07.botan.interpreters.Interpreter;

import java.util.List;

public class RequestHandler {

    private List<Interpreter> interpreters;

    public RequestHandler(List<Interpreter> interpreters) {
        this.interpreters = interpreters;
    }

    public String handle(String msg, String sender) {
        for (Interpreter interpreter : interpreters) {
            String response = interpreter.interpret(msg, sender);
            if (response != null) {
                return response;
            }
        }
        return null;
    }

    public String handle(String msg) {
        for (Interpreter interpreter : interpreters) {
            String response = interpreter.interpret(msg);
            if (response != null) {
                return response;
            }
        }
        return null;
    }

}
