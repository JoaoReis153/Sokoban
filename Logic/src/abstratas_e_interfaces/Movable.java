package abstratas_e_interfaces;

import gameelements.Alvo;
import gameelements.Caixote;
import gameelements.Chao;
import gameelements.Empilhadora;
import gameelements.Teleporte;
import gui.ImageMatrixGUI;
import sokobanstarter.GameEngine;
import utils.Direction;
import utils.Point2D;

public abstract class Movable extends GameElement {

	GameEngine engine = GameEngine.getInstance();
	ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

	public Movable(Point2D position) {
		super(position);
	}

	@Override
	public int getLayer() {
		return LAYERMOVABLE;
	}

	public int getLogicLayer() {
		return LAYERINTERACTABLE;
	}

	private boolean insideBounds(Point2D position) {
		if (position.getX() >= 0 && position.getX() < GameEngine.GRID_WIDTH && position.getY() >= 0
				&& position.getY() < GameEngine.GRID_HEIGHT)
			return true;
		return false;
	}

	private Point2D getNewPosition(Direction direction) {
		return getPosition().plus(direction.asVector());
	}

	public boolean canGoTo(GameElement g) {
		GameElement objectInNewPosition = g;

		return compare(this, objectInNewPosition);
	}

	private boolean canGoTo(Direction direction) {
		Point2D newPosition = getNewPosition(direction);

		if (!insideBounds(newPosition))
			return false;

		GameElement objectInNewPosition = engine.getObject(newPosition);

		return compare(this, objectInNewPosition);
	}

	// da compare a duas layers
	// caso queiramos que o objeto da layer:
	// ande em objetos da mesma altura no compare fazemos layer >= otherObjectLayer
	// caso contrario fazemos apenas layer > otherObjectLayer
	public boolean compare(Movable movable, GameElement objectInNewPosition) {
		if (objectInNewPosition instanceof Alvo)
			return true;
		
		if(objectInNewPosition instanceof Teleporte) {
			if(!(GameEngine.getInstance().getObject( ((Teleporte) objectInNewPosition).getPairPosition() ) instanceof Teleporte)) {
				return false;
			}
		}
		
		return movable.getLogicLayer() > objectInNewPosition.getLogicLayer();
	};

	public void moveTo(Point2D position) {

		Empilhadora empilhadora = Empilhadora.getInstance(null);
		setPosition(position);
		empilhadora.addEnergy(-1);
		if (this instanceof Empilhadora)
			empilhadora.addMove();
	}

	public void move(int keyCode) {
		Empilhadora empilhadora = Empilhadora.getInstance(null);
		// se nao tem energina nao anda

		// se nao tem energina nao anda
		if (empilhadora.getEnergy() <= 0) {
			engine.lost();
			return;
		}

		Direction direction = Direction.isDirection(keyCode) ? Direction.directionFor(keyCode) : null;
		changeImage(keyCode);
		if (direction == null || !canGoTo(direction))
			return;

		// cria o ponto correspondente à nova posicao
		Point2D newPosition = getNewPosition(direction);

		GameElement objectInNewPosition = engine.getObject(newPosition);
		Alvo alvoInMyPosition = engine.getAlvo(this.getPosition());
		// caso nao tenha nada no novo ponto, move-se
		if (alvoInMyPosition != null && this instanceof Caixote) {				
			((Alvo) engine.getAlvo(getPosition())).setHasBox(false);
		}
		
		if (objectInNewPosition instanceof Chao) {
			moveTo(newPosition);
		}

		else if (objectInNewPosition instanceof Consumable) {
			((Consumable) objectInNewPosition).consume();
		}

		// caso tenha um consumable no novo ponto, consome-o e move-se
		else if (objectInNewPosition instanceof Interactable) {
			((Interactable) objectInNewPosition).interact(this);
		}

		// caso tenha um objecto que se possa mover
		else if (objectInNewPosition instanceof Movable) {
			Movable box = (Movable) objectInNewPosition;
			if (box.canGoTo(direction)) {
				box.move(keyCode);
				moveTo(newPosition);
			}

		}
	}

	public abstract boolean box();

	public abstract void changeImage(int keyCode);

}
