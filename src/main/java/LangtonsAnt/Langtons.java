package LangtonsAnt;

public class Langtons {

    public static void main(String[] args) throws Exception {
        byte[][] b = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 0, 0 } };
        var g = new Grid(b);
        var h = g.Height();
        System.out.printf("Grid is W=%2$d by H=%1$d", h, g.Width());
        System.out.printf("%s", g.Draw());
    	var l = new Langtons();
    	System.out.printf("test: %s", l.IAmADummy());
    }

    public String IAmADummy() {
    	return "Dummy";
    }
}
