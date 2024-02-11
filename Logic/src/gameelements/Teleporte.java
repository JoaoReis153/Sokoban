package gameelements;

import abstratas_e_interfaces.GameElement;
import abstratas_e_interfaces.Interactable;
import abstratas_e_interfaces.Movable;
import sokobanstarter.GameEngine;
import utils.Point2D;

public class Teleporte extends GameElement implements Interactable {
	
	private Teleporte(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	public static GameElement criar(Point2D position) {
		Teleporte t = new Teleporte(position);

		GameEngine.getInstance().addTeleporte(t);

		return t;
	}

	@Override
	public String getName() {
		return "Teleporte";
	}

	@Override
	public int getLayer() {
		return LAYERFLOOR;
	}
	
	public Teleporte getPair() {
		return (Teleporte) engine.getOtherTeleporte(this);
	}

	public Point2D getPairPosition() {
		return engine.getOtherTeleporte(this).getPosition();
	}

	@Override
	public int getLogicLayer() {
		return LAYERFLOOR;
	}
	
	@Override
	public void interact(GameElement g) {
		GameElement objectInPairPosition = engine.getObject(getPairPosition());
		if((objectInPairPosition instanceof Teleporte) ) {
			((Movable) g).moveTo(getPairPosition());
		}
	}

}
