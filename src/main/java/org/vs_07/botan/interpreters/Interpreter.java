package org.vs_07.botan.interpreters;

public interface Interpreter {

    String interpret(String message);
    String interpret(String message, String sender);

}
