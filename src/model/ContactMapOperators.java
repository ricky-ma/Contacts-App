package model;

public interface ContactMapOperators {
    int size();
    boolean isEmpty();
    boolean contains(Contact c);
    void add(Contact c);
    Contact get(String name);
}
