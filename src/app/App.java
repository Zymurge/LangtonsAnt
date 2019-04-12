package app;

class Langtons {
    public static void main(String[] args) throws Exception {
        byte[][] b = { { 0, 1 }, { 0, 0 }, { 1, 1 } };
        var g = new Grid(b);
        System.out.printf("H=%0d, W=%-d", g.Height(), g.Width());
    }
}
