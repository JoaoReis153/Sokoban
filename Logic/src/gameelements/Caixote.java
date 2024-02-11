package gameelements;

import abstratas_e_interfaces.Movable;
import utils.Point2D;

public class Caixote extends Movable {

	public Caixote(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Caixote";
	}

	@Override
	public void changeImage(int keyCode) {
	}

	@Override
	public boolean box() {
		// TODO Auto-generated method stub
		return true;
	}
}
