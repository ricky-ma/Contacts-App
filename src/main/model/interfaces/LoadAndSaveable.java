package model.interfaces;

import model.exceptions.ContactAlreadyExistsException;

import java.io.IOException;

public interface LoadAndSaveable {

    void load(String fileName) throws IOException, ContactAlreadyExistsException;

    void save(String fileName) throws IOException;
}
