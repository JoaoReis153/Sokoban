package gameelements;

import abstratas_e_interfaces.GameElement;
import utils.Point2D;

public class Vazio extends GameElement {

	public Vazio(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Vazio";
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		// deve ser impossivel qualquer objeto estar por cima do vazio
		return LAYERWALL;
	}

	@Override
	public int getLogicLayer() {
		// TODO Auto-generated method stub
		return LAYERWALL;
	}

}
