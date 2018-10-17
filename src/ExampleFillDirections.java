public class ExampleFillDirections {

    private A2Direction[] array;

    public static void main(String[] args) {

	ExampleFillDirections example = new ExampleFillDirections();
	example.fillSix();

    }

    private void fillSix() {
	array = new A2Direction[6];

	array[0] = A2Direction.LEFT;
	array[1] = A2Direction.DOWN;
	array[2] = A2Direction.DOWN;
	array[3] = A2Direction.RIGHT;
	array[4] = A2Direction.UP;
	array[5] = A2Direction.LEFT;
    }

}
