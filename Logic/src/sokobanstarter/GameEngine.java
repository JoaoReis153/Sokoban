package sokobanstarter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import abstratas_e_interfaces.GameElement;
import abstratas_e_interfaces.Movable;
import gameelements.Alvo;
import gameelements.Bateria;
import gameelements.Caixote;
import gameelements.Empilhadora;
import gameelements.Teleporte;
import gui.ImageMatrixGUI;
import gui.ImageTile;
import observer.Observed;
import observer.Observer;
import utils.Point2D;
import filereader.MapReader;

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	// Constantes
	public static final int NUMBEROFWINNERS = 3;

	private List<GameElement> gameElements;
	private List<Teleporte> teleportes;
	private List<Movable> boxes;

	private String player;
	private int level; // nivel inicial

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private Estatistica stats;
	private Empilhadora bobcat; // Referencia para a empilhadora

	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		level = 0;
		gameElements = new ArrayList<>();
		teleportes = new ArrayList<Teleporte>();
		stats = Estatistica.getInstance();
	}

	public int numeroDeTeleportes() {
		return teleportes.size();
	}

	public void addGameElement(GameElement g) {
		gameElements.add(g);
	}

	public void addTeleporte(Teleporte t) {
		if (teleportes.size() < 2) teleportes.add(t);
	}

	public Teleporte getOtherTeleporte(Teleporte t1) {
		for (Teleporte k : teleportes) {
			if (!k.equals(t1))
				return k;
		}
		return t1;
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	private void getUser() {
		player = gui.askUser("Introduza o seu nome:");
		if (player == null || player.equals("")) {
			gui.setMessage("Para jogar tem de inserir um nome :)");
			getUser();
		}
	}

	private void getLevel() {
		level = level % ( getNumberOfLevels() - 1 );
	}

	private void getStatusMessage() {
		gui.setStatusMessage("Player: " + player + "| Level - " + level + "| Energia - " + bobcat.getEnergy()
				+ "| Moves - " + bobcat.getMoves());
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega
	// numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado
	// (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada

		if (bobcat == null) return;

		if (key == KeyEvent.VK_ENTER) newGame();

		bobcat.move(key);

		getStatusMessage();
		gui.update(); // redesenha a lista de ImageTiles na GUI,
						// tendo em conta as novas posicoes dos objetos

	}

	// Criacao da planta do armazem - so' chao neste exemplo
	private void createWarehouse() {

		for (int y = 0; y < GRID_HEIGHT; y++)
			for (int x = 0; x < GRID_HEIGHT; x++)
				gameElements.add(GameElement.criar("Chao", new Point2D(x, y)));
	}

	// load file
	private void loadFile() {
		MapReader obj = new MapReader(getFile(getLevelName()));

		try {
			// Code that may throw FileNotFoundException
			gameElements = obj.read();

			if (!checkIfThereAreEnoughBoxes() || !checkNumberOfTeleportes() || !hasEmpilhadora()) {
				gui.setMessage("Ficheiro mal formatado");
				System.exit(0);
			}
			// ...
		} catch (FileNotFoundException e) {
			// Handle the exception (e.g., print an error message)
			e.printStackTrace();
		}

		boxes = createBoxesList();
		this.bobcat = Empilhadora.getInstance(null);
	}

	// get file
	private File getFile(String sourceFile) {
		File file = new File(sourceFile);
		return file;
	}

	private List<ImageTile> convertToImageTileList(List<GameElement> gameElementList) {
		List<ImageTile> imageTileList = new ArrayList<ImageTile>();

		for (GameElement g : gameElementList) 
			imageTileList.add((ImageTile) g);

		return imageTileList;
	}

	public List<GameElement> getGameElements() {
		return gameElements;
	}

	public List<Movable> getBoxes() {
		return boxes;
	}

	private ArrayList<Movable> createBoxesList() {
		ArrayList<Movable> movables = new ArrayList<Movable>();
		for (GameElement p : gameElements) {
			if (p instanceof Movable) {
				Movable m = (Movable) p;
				if (m.box())
					movables.add((Movable) p);

			}
		}

		return movables;
	}

	private String getLevelName() {
		getLevel();
		String def = "level" + level + ".txt";
		String base = "levels/";

		File[] files = new File("levels/").listFiles();
		// caso nao encontro a pasta, devolve null

		if (level < files.length)
			return base + def;
		return base + files[0].getName();
	}

	public GameElement getObject(Point2D point) {
		GameElement g = null;
		int maxLayer = GameElement.LAYERGROUND; // Objeto com a layer mais alta encontrada
		for (GameElement element : getGameElements()) {
			if (maxLayer <= element.getLayer() && element.getPosition().equals(point)) {
				maxLayer = element.getLayer();
				g = element;
			}
		}

		return g;

	}

	public static int getNumberOfLevels() {
		return new File("levels/").list().length;
	}

	public Alvo getAlvo(Point2D point) {
		for (GameElement a : gameElements)
			if (a.getPosition().equals(point) && a instanceof Alvo)
				return (Alvo) a;

		return null;
	}

	public Teleporte getTeleporte(Point2D point) {
		for (Teleporte a : teleportes)
			if (a.getPosition().equals(point) && a instanceof Teleporte)
				return (Teleporte) a;

		return null;
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		List<ImageTile> tileList = convertToImageTileList(gameElements);
		gui.addImages(tileList);
	}

	public void removeObject(GameElement e) {
		gameElements.remove(e);
		boxes.remove(e);
	}

	private void loadGame() {
		// Criar o cenario de jogo
		loadFile();
		createWarehouse(); // criar o armazem
		sendImagesToGUI(); // enviar as imagens para a GUI
		gui.update();
	}

	private void loadGui() {
		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI
	}

	// Inicio
	public void start() {
		// Load ImageMatrixGUI
		loadGui();
		// Pedir info do jogo
		getUser();
		// Criar o cenario de jogo
		loadGame();
		// Escrever uma mensagem na StatusBar
		getStatusMessage();
	}

	private int nBaterias() {
		int n = 0;
		for (GameElement g : gameElements) if (g instanceof Bateria) n++;
		return n;
	}

	private int getPoints() {
		return bobcat.getInitialEnergy() + (nBaterias() * Bateria.getBonusEnergy()) - bobcat.getMoves();
	}

	private void newGame() {
		reset();
		loadGame();
		gui.update();
	}

	private void reset() {
		gui.clearImages();
		gameElements.clear();
		boxes.clear();
		Empilhadora.reset();

	}

	public boolean hasEmpilhadora() {
		for (GameElement g : gameElements) if (g instanceof Empilhadora) return true;
		return false;
	}

	public boolean checkNumberOfTeleportes() {
		int n = 0;
		for (GameElement g : gameElements) if (g instanceof Teleporte) n++;
		return n == 2 || n == 0;
	}

	public boolean checkIfThereAreEnoughBoxes() {
		int nAlvos = 0;
		int nCaixotes = 0;

		for (GameElement g : gameElements) {
			if (g instanceof Alvo) nAlvos++;
			if (g instanceof Caixote) nCaixotes++;
		}
		
		if (nAlvos > nCaixotes) return false;
		else return true;
	}

	public void lost() {
		gui.setMessage("You lost. Try again :)");
		newGame();
	}

	public void win() {

		for (GameElement e : gameElements) {
			if (e instanceof Alvo) {
				if (((Alvo) e).hasBox() == false)
					return;
			}
		}
		
		gui.setMessage("Congratulations! You won with " + bobcat.getMoves() + " moves");
		stats.loadGame(player, getPoints(), level);
		level++;
		newGame();

	}
}