package com.github.itsheroph.hewoutil.configuration;

import com.github.itsheroph.hewoutil.messages.logging.HewoLogger;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class HewoResource {

    public static @Nullable InputStream getResource(String resource, HewoLogger logger) {

        URL input = HewoResource.class.getClassLoader().getResource(resource);

        try {

            if(input == null) {

                return null;

            } else {

                URLConnection connection = input.openConnection();

                connection.setUseCaches(false);
                return connection.getInputStream();

            }

        } catch (IOException e) {

            logger.error(e.getMessage());
            return null;

        }

    }

    public static void saveResource(String resource, String path, HewoLogger logger) {

        InputStream input = getResource(resource, logger);

        if(input == null) {

            logger.error("The embeded resource " + resource + " cannot found in jar file.");

        } else {

            File outFile = new File(path);
            File outDir = new File(outFile.getParent());

            if(!outDir.exists()) {

                outDir.mkdirs();

            }

            try {

                if(outFile.exists()) {

                    logger.warning("Could not save the " + outFile.getName() + " to " + outFile.toString() + " because it is already exists");

                } else {

                    OutputStream output = Files.newOutputStream(outFile.toPath());
                    byte[] buffer = new byte[1024];

                    int len;

                    while((len = input.read(buffer)) > 0) {

                        output.write(buffer, 0, len);

                    }

                    output.close();
                    input.close();

                }

            } catch (IOException e) {

                logger.error("Could not save the " + outFile.getName() + " to " + outFile.toString());

            }
        }
    }
}
