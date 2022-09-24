package by.babanin.example.tree;

public interface NodeBuildStrategy<C, N extends Node<C, N>> {

    N createFakeRoot();

    N createNode(N parent, C component);

    boolean isChild(C parentComponent, C childComponent);

    boolean isRoot(C component);
}
