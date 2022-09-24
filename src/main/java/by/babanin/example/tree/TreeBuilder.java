package by.babanin.example.tree;

import java.util.List;

public class TreeBuilder<C, N extends Node<C, N>> {

    private final NodeBuildStrategy<C, N> nodeBuildStrategy;

    public TreeBuilder(NodeBuildStrategy<C, N> nodeBuildStrategy) {
        this.nodeBuildStrategy = nodeBuildStrategy;
    }

    public N build(List<C> components) {
        N fakeRoot = nodeBuildStrategy.createFakeRoot();
        List<C> rootComponents = components.stream()
                .filter(nodeBuildStrategy::isRoot)
                .toList();
        for(C rootComponent : rootComponents) {
            fakeRoot.addChild(buildRecursive(fakeRoot, rootComponent, components));
        }
        return fakeRoot;
    }

    private N buildRecursive(N parent, C component, List<C> components) {
        N node = nodeBuildStrategy.createNode(parent, component);
        List<C> directChildComponents = components.stream()
                .filter(c -> nodeBuildStrategy.isChild(component, c))
                .toList();
        for(C childComponent : directChildComponents) {
            node.addChild(buildRecursive(node, childComponent, components));
        }
        return node;
    }
}
