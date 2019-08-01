package model.interfaces;

import java.io.IOException;

public interface LoadAndSaveable {
    void load(String fileName) throws IOException;
    void save(String fileName) throws IOException;
}
