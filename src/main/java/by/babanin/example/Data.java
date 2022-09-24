package by.babanin.example;

import java.util.Objects;

public record Data(int id, String string, int parentId) {

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Data data = (Data) o;
        return id == data.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", string='" + string + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
