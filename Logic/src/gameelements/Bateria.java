package gameelements;

import abstratas_e_interfaces.Consumable;
import abstratas_e_interfaces.GameElement;
import gui.ImageMatrixGUI;
import sokobanstarter.GameEngine;
import utils.Point2D;

public class Bateria extends GameElement implements Consumable {

	private static final int ENERGYBONUS = 50;

	public Bateria(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bateria";
	}

	public static int getBonusEnergy() {
		return ENERGYBONUS;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return LAYERINTERACTABLE;
	}

	@Override
	public void consume() {
		Empilhadora empilhadora = Empilhadora.getInstance(null);
		empilhadora.addEnergy(ENERGYBONUS);

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
