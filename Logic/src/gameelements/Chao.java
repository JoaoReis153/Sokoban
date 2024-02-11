package gameelements;

import abstratas_e_interfaces.GameElement;
import utils.Point2D;

public class Chao extends GameElement {

	public Chao(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Chao";
	}

	@Override
	public int getLayer() {
		return LAYERGROUND;
	}

	@Override
	public int getLogicLayer() {
		// TODO Auto-generated method stub
		return getLayer();
	}

}
