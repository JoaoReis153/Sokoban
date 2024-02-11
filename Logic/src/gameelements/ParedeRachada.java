package gameelements;

import abstratas_e_interfaces.GameElement;
import abstratas_e_interfaces.Interactable;
import abstratas_e_interfaces.Movable;
import utils.Point2D;

public class ParedeRachada extends GameElement implements Interactable {

	private int layer;

	public ParedeRachada(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
		layer = LAYERWALL;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ParedeRachada";
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return layer;
	}

	public void setLayer(int newLayer) {
		layer = newLayer;
	}

	@Override
	public int getLogicLayer() {
		// TODO Auto-generated method stub
		return getLayer();
	}

	@Override
	public void interact(GameElement g) {

		if (((Movable) g).canGoTo(g)) {
			// remove a objeto da bateria
			engine.removeObject(this);

			// remove a imagem da bateria
			gui.removeImage(this);
			((Movable) g).moveTo(getPosition());
		}
	}

}
