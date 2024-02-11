package gameelements;

import abstratas_e_interfaces.GameElement;
import abstratas_e_interfaces.Interactable;
import abstratas_e_interfaces.Movable;
import utils.Point2D;

public class Alvo extends GameElement implements Interactable {

	boolean hasBox = false;

	public Alvo(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	public boolean hasBox() {
		return hasBox;
	}

	public void setHasBox(boolean t) {
		hasBox = t;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Alvo";
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return LAYERFLOOR;
	}

	@Override
	public int getLogicLayer() {
		// TODO Auto-generated method stub
		return LAYERINTERACTABLE;
	}

	@Override
	public void interact(GameElement g) {
		// TODO Auto-generated method stub
		
		// caso o objeto que se esta a mover seja um caixote
		if (g instanceof Caixote) {

			this.setHasBox(true);
			engine.win();
		}
		
		if(g instanceof Movable) ((Movable) g).moveTo(getPosition());
		
	}
	

}
