package gameelements;

import abstratas_e_interfaces.GameElement;
import abstratas_e_interfaces.Interactable;
import abstratas_e_interfaces.Movable;
import utils.Point2D;

public class Buraco extends GameElement implements Interactable {

	int layer;

	public Buraco(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Buraco";
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return LAYERFLOOR;
	}

	@Override
	public void interact(GameElement g) {
		

		if (g instanceof Palete) {
			Palete p = (Palete) g;
			p.setPosition(getPosition());

			// TODO Auto-generated method stub
			engine.removeObject(p);
			p.setLayer(LAYERFLOOR);

			engine.removeObject(this);
			engine.removeObject(g);
			// remove a imagem da bateria
			gui.removeImage(this);
			gui.removeImage(g);

		} else {

			// TODO Auto-generated method stub
			engine.removeObject(g);

			// remove a imagem da bateria
			gui.removeImage(g);
      
			if(g instanceof Empilhadora || (g instanceof Caixote && !engine.checkIfThereAreEnoughBoxes())) 
				engine.lost();
			

		}
		((Movable) g).moveTo(getPosition());
	}

	@Override
	public int getLogicLayer() {
		// TODO Auto-generated method stub
		return LAYERFLOOR;
	}

}
