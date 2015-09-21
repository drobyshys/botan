package org.vs_07.botan.interpreters;

import org.apache.commons.lang3.StringUtils;
import org.vs_07.botan.util.ResourceFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mark.lacdao on 31/07/2015.
 */
public class CommandInterpreter implements Interpreter {

    private static final String USERS_FILE = "users.properties";

    @Override
    public String interpret(String message) {

        return null; // TODO
    }

    @Override
    public String interpret(String message, String sender) {
        List<String> admins = getAdmins();
        if(admins.contains(sender)){ // Only admins can execute commands
            return interpret(message);
        }
        return "Invalid command."; // TODO
    }

    @SuppressWarnings("deprecation")
    protected List<String> getAdmins(){
        List<String> admins = new ArrayList<String>();
        Properties properties = ResourceFileReader.getProperties(USERS_FILE);
        if(null == properties) return admins;
        admins.addAll(parseAdmins(properties.getProperty("admins")));
        return admins;
    }

    private List<String> parseAdmins(String propertiesValue){
        List<String> cleanedAdmins = new ArrayList<String>();
        String[] uncleanedAdmins = StringUtils.split(propertiesValue, ","); // LOL uncleaned haha
        for (String uncleanedAdmin : uncleanedAdmins) {
            cleanedAdmins.add(uncleanedAdmin.trim());
        }
        return  cleanedAdmins;
    }

}
