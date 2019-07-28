package model;

public interface ContactListOperators {
    int size();
    boolean isEmpty();
    boolean contains(Contact c);
    void add(Contact c);
    Contact get(int i);
}
