package by.babanin.example;

import by.babanin.example.tree.Node;

public class DataNode extends Node<Data, DataNode> {

    @Override
    public String toString() {
        if(hasComponent()) {
            return getComponent().toString();
        }
        return "null";
    }
}
