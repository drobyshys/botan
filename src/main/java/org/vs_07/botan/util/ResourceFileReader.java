package org.vs_07.botan.util;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceFileReader {

    private static final Logger LOG = Logger.getLogger(ResourceFileReader.class);

    public ResourceFileReader() {
    }

    protected static String getFileContents(String filename) throws IOException {
        InputStream is = getInputStream(filename);
        return IOUtils.toString(is);
    }

    protected static InputStream getInputStream(String filename){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
    }

    public static Properties getProperties(String filename) {
        InputStream inputStream = getInputStream(filename);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return properties;
    }
}
