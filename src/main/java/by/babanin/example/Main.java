package by.babanin.example;

import java.util.List;

import by.babanin.example.tree.TreeBuilder;

public class Main {

    public static void main(String[] args) {
        List<Data> list = List.of(
                new Data(1, "Соки", 0),
                new Data(2, "Манго", 1),
                new Data(3, "Виноградный", 1),
                new Data(4, "Яблочный", 1),
                new Data(5, "Газировка", 0),
                new Data(6, "Кола", 5),
                new Data(7, "Кола 0.5", 6),
                new Data(8, "Кола 1.5", 6),
                new Data(9, "Минералка", 5),
                new Data(10, "Лимонад", 5)
        );

        TreeBuilder<Data, DataNode> builder = new TreeBuilder<>(new DataNodeBuildStrategy());
        DataNode root = builder.build(list);
        String result = root.reduce((dataNode, names) -> {
            if(!dataNode.hasChildren()) {
                String string = dataNode.getComponent().string();
                if(names.length() > 0) {
                    return names + ", " + string;
                }
                return string;
            }
            return names;
        }, "");
        System.out.println(result);
    }
}