package model.interfaces;


import model.Contact;

public interface ContactMapOperators {
    int size();
    boolean isEmpty();
    boolean contains(Contact c);
    void add(Contact c);
    Contact get(String name);
}
