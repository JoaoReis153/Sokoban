package gameelements;

import abstratas_e_interfaces.Consumable;
import abstratas_e_interfaces.GameElement;
import utils.Point2D;

public class Martelo extends GameElement implements Consumable {

	public Martelo(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Martelo";
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return LAYERINTERACTABLE;
	}

	@Override
	public void consume() {
		// TODO Auto-generated method stub
		Empilhadora empilhadora = Empilhadora.getInstance(null);

		empilhadora.addSuperPower();

		// remove a objeto da bateria
		engine.removeObject(this);

		// remove a imagem da bateria
		gui.removeImage(this);

		Empilhadora.getInstance(null).moveTo(getPosition());

	}

	@Override
	public int getLogicLayer() {
		// TODO Auto-generated method stub
		return LAYERINTERACTABLE;
	}

}
