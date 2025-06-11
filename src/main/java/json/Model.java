package json;

import java.text.DecimalFormat;

public record Model(
        String name,
        long size
) {

    @Override
    public String toString() {
        return "Model{" +
               "name='" + name + '\'' +
               ", size=" + formatSize(size) +
               '}';
    }

    private String formatSize(long size) {
        return round(1.0 * size / 1024 / 1024 / 1024) + " GB";
    }

    private String round(double d) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(d);
    }
}