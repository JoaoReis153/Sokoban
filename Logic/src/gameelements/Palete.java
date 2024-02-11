package gameelements;

import abstratas_e_interfaces.Movable;
import utils.Point2D;

public class Palete extends Movable {

	int l = LAYERINTERACTABLE;

	public Palete(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Palete";
	}

	public void setLayer(int n) {
		l = n;
	}

	@Override
	public int getLayer() {
		return l;
	}

	@Override
	public int getLogicLayer() {
		return getLayer();
	}

	@Override
	public boolean box() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void changeImage(int keyCode) {
	}

}
