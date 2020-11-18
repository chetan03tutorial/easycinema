package com.techverito.sales.entertaintment.helper;

import com.techverito.sales.entertaintment.bmm.exception.InputOutputException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class TestResourceReader {

    public static List<File> readFile(String path,String ext){
        List<File> files = new LinkedList<>();
        URI url = null;
        try {
            URL u = TestResourceReader.class.getClassLoader().getResource(path);
            url = TestResourceReader.class.getClassLoader().getResource(path).toURI();
            //url = ClassLoader.getSystemResource(path).toURI();
            Files.newDirectoryStream(Paths.get(url), p -> p.toString().endsWith(".".concat(ext)))
                    .forEach(p -> files.add(p.toFile()));
        } catch (URISyntaxException e) {
            throw new InputOutputException(e);
        } catch (IOException e) {
            throw new InputOutputException(e);
        }
        return files;
    }

}
