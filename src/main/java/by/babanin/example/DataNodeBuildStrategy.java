package by.babanin.example;

import by.babanin.example.tree.NodeBuildStrategy;

public class DataNodeBuildStrategy implements NodeBuildStrategy<Data, DataNode> {

    private static final int FAKE_ROOT_ID = 0;
    private static final String FAKE_ROOT = "FakeRoot";

    @Override
    public DataNode createFakeRoot() {
        DataNode dataNode = new DataNode();
        dataNode.setComponent(new Data(FAKE_ROOT_ID, FAKE_ROOT, FAKE_ROOT_ID));
        return dataNode;
    }

    @Override
    public DataNode createNode(DataNode parent, Data component) {
        DataNode dataNode = new DataNode();
        dataNode.setParent(parent);
        dataNode.setComponent(component);
        return dataNode;
    }

    @Override
    public boolean isChild(Data parentComponent, Data childComponent) {
        return parentComponent.id() == childComponent.parentId();
    }

    @Override
    public boolean isRoot(Data component) {
        return component.parentId() == FAKE_ROOT_ID;
    }
}
