package gameelements;

import java.awt.event.KeyEvent;

import abstratas_e_interfaces.GameElement;
import abstratas_e_interfaces.Movable;
import sokobanstarter.GameEngine;
import utils.Point2D;

public class Empilhadora extends Movable {

	private int energy;
	private final int INITIAL_ENERGY = 100;
	private int moves;
	private String imageName = "Empilhadora_D";
	private static Empilhadora INSTANCE;

	private Empilhadora(Point2D position) {
		super(position);
		energy = INITIAL_ENERGY;
		moves = 0;
	}

	// se ja houver empilhadora e o position nao for null -> muda a empilhadora de
	// sitio e devolve-a
	// se ja houver empilhadora e o position for null -> devolve so a empilhadora
	// se nao houver empilhadora -> cria uma com a posicao dada e devolve-a
	public static Empilhadora getInstance(Point2D position) {
		if (INSTANCE == null)
			INSTANCE = new Empilhadora(position);
		return INSTANCE;
	}

	public static void reset() {
		INSTANCE = null;
	}

	@Override
	public boolean compare(Movable movable, GameElement objectInNewPosition) {
		return movable.getLogicLayer() >= objectInNewPosition.getLogicLayer();
	};

	public void changeImage(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_DOWN:
			imageName = "Empilhadora_D";
			break;
		case KeyEvent.VK_UP:
			imageName = "Empilhadora_U";
			break;
		case KeyEvent.VK_LEFT:
			imageName = "Empilhadora_L";
			break;
		case KeyEvent.VK_RIGHT:
			imageName = "Empilhadora_R";
			break;
		}
	}

	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public int getLayer() {
		return LAYEREMPILHADORA;
	}

	@Override
	public boolean box() {
		return false;
	}

	public void addEnergy(int n) {
		energy = Math.max(0, energy + n);
	}

	public void resetEnergy() {
		energy = INITIAL_ENERGY;
	}

	public int getEnergy() {
		return energy;
	}

	public int getInitialEnergy() {
		return INITIAL_ENERGY;
	}

	public void addSuperPower() {
		GameEngine engine = GameEngine.getInstance();
		for (GameElement e : engine.getGameElements()) {
			if (e instanceof ParedeRachada) {
				((ParedeRachada) e).setLayer(LAYERINTERACTABLE);
			}
		}
		;

	}

	public void resetMoves() {
		moves = 0;
	}

	public int getMoves() {
		return moves;
	}

	public void addMove() {
		moves++;
	}
}