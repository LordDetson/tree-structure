package by.babanin.example.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class Node<C, N extends Node<C, N>> {

    private C component;
    private N parent;
    private Set<N> children;

    public boolean isRoot() {
        return parent == null;
    }

    public N getParent() {
        return parent;
    }

    public void setParent(N parent) {
        this.parent = parent;
        parent.addChildWithoutAddingParent((N) this);
    }

    protected void addChildWithoutAddingParent(N child) {
        if(child != null) {
            if(children == null) {
                children = new LinkedHashSet<>();
            }
            children.add(child);
        }
    }

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    public Set<N> getChildren() {
        Set<N> result = Collections.emptySet();
        if(hasChildren()) {
            result = new LinkedHashSet<>(children);
        }
        return result;
    }

    public Set<N> getChildrenRecursively() {
        Set<N> result = new LinkedHashSet<>();
        collectChildrenRecursively(this, result);
        return result;
    }

    private void collectChildrenRecursively(Node<C, N> currentNode, Set<N> result) {
        if(currentNode.hasChildren()) {
            Set<N> childrenOfCurrentNode = currentNode.getChildren();
            result.addAll(childrenOfCurrentNode);
            for(N child : childrenOfCurrentNode) {
                collectChildrenRecursively(child, result);
            }
        }
    }

    public void setChildren(Set<N> children) {
        if(children != null && !children.isEmpty()) {
            clearChildren();
            this.children = new LinkedHashSet<>();
            for(N child : children) {
                child.setParentWithoutAddingChild((N) this);
            }
        }
    }

    protected void setParentWithoutAddingChild(N parent) {
        if(!this.parent.equals(parent)) {
            this.parent = parent;
        }
    }

    public void addChild(N child) {
        if(child != null) {
            if(children == null) {
                children = new LinkedHashSet<>();
            }
            children.add(child);
            child.setParentWithoutAddingChild((N) this);
        }
    }

    public void addChildren(Collection<? extends N> children) {
        if(children != null && !children.isEmpty()) {
            if(this.children == null) {
                this.children = new LinkedHashSet<>();
            }
            this.children.addAll(children);
            for(N child : children) {
                child.setParentWithoutAddingChild((N) this);
            }
        }
    }

    public void remove(N child) {
        if(child != null) {
            if(children == null) {
                children = new LinkedHashSet<>();
            }
            children.remove(child);
            child.setParentWithoutAddingChild(null);
        }
    }

    public void remove(Collection<? extends N> children) {
        if(children != null && !children.isEmpty()) {
            if(this.children == null) {
                this.children = new LinkedHashSet<>();
            }
            this.children.removeAll(children);
            for(N child : children) {
                child.setParentWithoutAddingChild(null);
            }
        }
    }

    private void clearChildren() {
        if(children != null) {
            for(N child : children) {
                child.setParentWithoutAddingChild(null);
            }
            children = null;
        }
    }

    public boolean hasComponent() {
        return component != null;
    }

    public C getComponent() {
        return component;
    }

    public void setComponent(C component) {
        this.component = component;
    }

    /**
     * Performing the operation (<code>reducer</code>) for each node while keeping the intermediate result
     *
     * @param reducer is operation for each node. Decryption of Generic type:
     *                &lt;N - current node, R - previous result of previous iteration, R - result of current iteration&gt;
     * @param initial is starting value
     * @param <R>     is result type of reducer
     * @return is final result
     */
    public <R> R reduce(BiFunction<N, R, R> reducer, R initial) {
        R result = reducer.apply((N) this, initial);
        if(hasChildren()) {
            for(N child : children) {
                result = child.reduce(reducer, result);
            }
        }
        return result;
    }
}
