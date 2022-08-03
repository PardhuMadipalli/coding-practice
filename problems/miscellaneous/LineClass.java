package problems.miscellaneous;

import java.util.Objects;

public class LineClass {
    Double m;
    double c;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineClass lineClass = (LineClass) o;
        return Double.compare(lineClass.m, m) == 0 && Double.compare(lineClass.c, c) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m, c);
    }
}
