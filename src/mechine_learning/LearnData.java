package mechine_learning;

class LearnData {
    private String path0;
    private String path1;
    private boolean similar;

    LearnData(String path0, String path1, boolean similar) {
        this.path0 = path0;
        this.path1 = path1;
        this.similar = similar;
    }

    String getPath0() {
        return path0;
    }

    String getPath1() {
        return path1;
    }

    boolean isSimilar() {
        return similar;
    }
}
