package gameelements;

import abstratas_e_interfaces.GameElement;
import utils.Point2D;

public class Parede extends GameElement {

	public Parede(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Parede";
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		/*
		 * Logica: Os gameElements apenas podem passar por cima de objetos com um layer
		 * menor Como nada pode passar por cima das paredes O seu layer vai ser mais
		 * alto do que qualquer outro elemento Podia ser 10 ou outro numero maior que
		 * todos os outros
		 */
		return LAYERWALL;
	}

	@Override
	public int getLogicLayer() {
		// TODO Auto-generated method stub
		return getLayer();
	}

}
