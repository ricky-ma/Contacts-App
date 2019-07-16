package model;

import java.io.IOException;

public interface LoadAndSaveable {
    void load() throws IOException;
    void save() throws IOException;
}
