package mechine_learning;

public class LearnData {
    private String path0;
    private String path1;
    private boolean similar;

    public LearnData(String path0, String path1, boolean similar) {
        this.path0 = path0;
        this.path1 = path1;
        this.similar = similar;
    }

    public String getPath0() {
        return path0;
    }

    public String getPath1() {
        return path1;
    }

    public boolean isSimilar() {
        return similar;
    }
}
