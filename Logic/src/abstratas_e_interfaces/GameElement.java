package abstratas_e_interfaces;

import gameelements.Alvo;
import gameelements.Bateria;
import gameelements.Buraco;
import gameelements.Caixote;
import gameelements.Chao;
import gameelements.Empilhadora;
import gameelements.Martelo;
import gameelements.Palete;
import gameelements.Parede;
import gameelements.ParedeRachada;
import gameelements.Teleporte;
import gameelements.Vazio;
import gui.ImageMatrixGUI;
import gui.ImageTile;
import sokobanstarter.GameEngine;
import utils.Point2D;

public abstract class GameElement implements ImageTile {

	private Point2D position;

	public static final int LAYERGROUND = 0;
	public static final int LAYERFLOOR = 1;
	public static final int LAYERINTERACTABLE = 2;
	public static final int LAYERMOVABLE = 3;
	public static final int LAYEREMPILHADORA = 4;
	public static final int LAYERWALL = 5;

	public GameEngine engine = GameEngine.getInstance();
	public ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	
	public GameElement(Point2D position) {
		this.position = position;
	}

	public static GameElement criar(String tipo, Point2D position) {
		switch (tipo) {
		case "Chao":
		case " ":
			return new Chao(position);
		case "Vazio":
		case "=":
			return new Vazio(position);
		case "Empilhadora":
		case "E":
			return Empilhadora.getInstance(position);
		case "Parede":
		case "#":
			return new Parede(position);
		case "Caixote":
		case "C":
			return new Caixote(position);
		case "Bateria":
		case "B":
			return new Bateria(position);
		case "Alvo":
		case "X":
			return new Alvo(position);
		case "Buraco":
		case "O":
			return new Buraco(position);
		case "Palete":
		case "P":
			return new Palete(position);
		case "Martelo":
		case "M":
			return new Martelo(position);
		case "Parede Rachada":
		case "%":
			return new ParedeRachada(position);
		case "Teleporte":
		case "T":
			return Teleporte.criar(position);

		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public abstract String getName();

	@Override
	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D newposition) {
		position = newposition;
	}

	@Override
	public abstract int getLayer();

	public abstract int getLogicLayer();

}
