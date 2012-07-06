package me.rich.gss.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import me.rich.gss.Game;
import me.rich.gss.entity.Player;
import me.rich.gss.entity.mob.Drone;
import me.rich.gss.entity.mob.Fighter;
import me.rich.gss.entity.mob.Mob;
import me.rich.gss.entity.mob.MobType;
import me.rich.gss.entity.projectile.Bullet;
import me.rich.gss.entity.projectile.Projectile;
import me.rich.gss.graphic.Art;
import me.rich.gss.particle.EmitterUtil;
import me.rich.gss.particle.Star;
import me.rich.gss.sound.SoundManager;
import me.rich.gss.stage.Stage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState implements MouseListener {
	private int stateID;

	private Random r;
	private long time;
	private Font font;

	private ArrayList<Projectile> projectiles;
	private float pjWidth = 32;
	private float pjHeight = 32;
	private int currentProjectiles;
	private long lastFireTime;
	private long fireInterval = 250L;
	private long lastSpecialFireTime;
	private long specialFireInterval = 2000L;
	private static Player player;

	private ArrayList<Star> stars;
	private int maxStarNum = 32;
	private int currentStarNum;

	private ArrayList<Mob> mobs;
	private int currentMobNum;
	private int totalSpawned;

	private int amountToSpawn;
	private int amountSpawned;

	private long lastMobSpawnTime;
	private long mobSpawnInterval = 750L;

	private ParticleSystem explosion;

	private ArrayList<Stage> stages;
	private int currentStage;
	private int finalStage = 1;
	private boolean stageComplete = false;
	private boolean mousePos = false;

	public GameState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		stages = new ArrayList<Stage>();
		loadStages();
		r = new Random();
		font = Art.getFont(72);
		player = new Player(Game.width / 2, Game.height - 200);
		projectiles = new ArrayList<Projectile>();
		stars = new ArrayList<Star>();
		mobs = new ArrayList<Mob>();

		explosion = new ParticleSystem(EmitterUtil.getEmitterInfo("explode").getImage());
		explosion.setPosition(0, 0);
		gc.getInput().addMouseListener(this);
		started = true;

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		time = gc.getTime();
		updateProjectiles(delta);
		updateStars(delta);
		updateMobs(sbg, delta);

		if (!gc.isMouseGrabbed()) {
			gc.setMouseGrabbed(true);
		}

		if (!mousePos) {
			Mouse.setCursorPosition(Game.width / 2, 200);
			mousePos = true;
		}

		if (player.isAlive()) {
			player.update(delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			sbg.enterState(State.MAIN_MENU_STATE.getStateID());
		}

		if (!mobs.isEmpty() && !projectiles.isEmpty()) {
			checkCollisions();
		}

		if (!mobs.isEmpty() && player.isAlive()) {
			checkPlayerCollisions();
		}

		explosion.update(delta);

		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (player.isAlive()) {
				if (currentProjectiles < player.getBullets()) {
					if (time > lastFireTime + fireInterval) {
						lastFireTime = time;
						projectiles.add(new Bullet(Mouse.getX(), Game.height - Mouse.getY(), true));
						currentProjectiles++;
						SoundManager.playShot(2.0F);
					}
				}

			}
		}

		if (Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
			if (time > lastSpecialFireTime + specialFireInterval) {
				lastSpecialFireTime = time;
				specialAttack();
				SoundManager.playDeath();
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("CurrentState: " + State.GAME_STATE.getName(), 20, Game.height - 20);

		g.drawString("TotalMobs: " + totalSpawned + " CurrentMobs: " + mobs.size() + " Stage: " + stages.get(currentStage).getID() + " Queue: "
				+ stages.get(currentStage).getCurrentSpawnQueue().getId() + " Alive: " + player.isAlive(), Game.width / 2, Game.height - 20);
		
		renderStars(g);
		renderProjectiles();
		renderMobs(g);

		if (player.isAlive()) {
			player.render(g);
		}

		explosion.render();

		if (ended) {
			g.setFont(font);
			g.drawString("End of Game", Game.width / 5, Game.height / 2);
		}

		if (stageComplete) {
			g.setFont(font);
			g.drawString("Stage Complete", Game.width / 3, Game.height / 2);
		}

		if (!player.isAlive()) {
			g.setFont(font);
			g.drawString("Game Over", Game.width / 3, Game.height / 2);
		}

		//g.drawRect(player.getX() - (player.getWidth() / 2), player.getY() - (player.getHeight() / 2), player.getWidth(), player.getHeight());

	}

	@Override
	public int getID() {
		return stateID;
	}

	private void updateProjectiles(int delta) {
		if (!projectiles.isEmpty()) {
			for (int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).update(delta);
				if (!projectiles.get(i).isAlive()) {
					if (projectiles.get(i).isPlayerFired()) {
						currentProjectiles--;
					}
					projectiles.remove(i);
				}
			}
		}
	}

	private void renderProjectiles() {
		if (!projectiles.isEmpty()) {
			for (int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).render();
			}
		}
	}

	private void updateStars(int delta) {
		if (!stars.isEmpty()) {
			for (int i = 0; i < stars.size(); i++) {
				stars.get(i).update(delta);
				if (!stars.get(i).isAlive()) {
					stars.remove(i);
					currentStarNum--;
				}
			}
		}
		if (currentStarNum < maxStarNum) {
			stars.add(new Star(r.nextFloat() * Game.width, -100.0F, r.nextFloat()));
			currentStarNum++;
		}
	}

	private void renderStars(Graphics g) {
		if (!stars.isEmpty()) {
			for (int i = 0; i < stars.size(); i++) {
				stars.get(i).render(g);
			}
		}
	}

	public void updateMobs(StateBasedGame sbg, int delta) {
		if (!mobs.isEmpty()) {
			for (int i = 0; i < mobs.size(); i++) {
				mobs.get(i).update(delta);
				if (mobs.get(i).getFireChance() > r.nextInt(100)) {
					mobs.get(i).fire();
				}
				if (!mobs.get(i).isAlive()) {
					mobs.remove(i);
					currentMobNum--;
				}
			}
		}

		if (started) {
			if (amountSpawned < stages.get(currentStage).getCurrentSpawnQueue().getAmount()) {
				if (time > (lastMobSpawnTime + (mobSpawnInterval + r.nextInt(500)))) {
					lastMobSpawnTime = time;
					if (stages.get(currentStage).getCurrentSpawnQueue().getMobType() == MobType.Drone) {
						mobs.add(new Drone(r.nextFloat() * (Game.width - 64), -100.0F, false));
					} else if (stages.get(currentStage).getCurrentSpawnQueue().getMobType() == MobType.Fighter) {
						mobs.add(new Fighter(r.nextFloat() * (Game.width - 64), -100.0F, true));
					}
					currentMobNum++;
					amountSpawned++;
					totalSpawned++;
				}
			} else {
				stages.get(currentStage).incrementCurrentQueue();
				if (stages.get(currentStage).isStageOver()) {
					if (mobs.isEmpty()) {
						if (currentStage == finalStage) {
							ended = true;
						} else {
							if (!stageComplete) {
								stageComplete();
							}
							if (System.currentTimeMillis() > timer + 5000L) {
								started = false;
								stageComplete = false;
								Game.changeState(State.UPGRADE_STATE);
								sbg.enterState(State.UPGRADE_STATE.getStateID(), new FadeOutTransition(Color.black), new FadeInTransition(Color.black, 1000));
							}
						}
					}
				} else {
					amountSpawned = 0;
				}
			}
		}
	}

	long timer;

	public void stageComplete() {
		stageComplete = true;
		timer = System.currentTimeMillis();
	}

	private void renderMobs(Graphics g) {
		if (!mobs.isEmpty()) {
			for (int i = 0; i < mobs.size(); i++) {
				mobs.get(i).render(g);
			}
		}
	}

	Rectangle prj, mob;

	public void checkCollisions() {
		for (int j = 0; j < projectiles.size(); j++) {
			if (projectiles.get(j).isPlayerFired()) {
				float pjX = projectiles.get(j).getX();
				float pjY = projectiles.get(j).getY();

				for (int i = 0; i < mobs.size(); i++) {
					float mobX = mobs.get(i).getX();
					float mobY = mobs.get(i).getY();
					float mobWidth = mobs.get(i).getImage().getWidth();
					float mobHeight = mobs.get(i).getImage().getHeight();

					prj = new Rectangle(pjX, pjY, pjWidth, pjHeight);
					mob = new Rectangle(mobX + 8, mobY + 8, 32, 32);

					if (prj.intersects(mob)) {
						mobs.get(i).takeDamage(time, player.getDamage());
						if (!mobs.get(i).isAlive()) {
							player.addMoney(mobs.get(i).getMoney());
							mobs.remove(i);
							projectiles.remove(j);
							currentProjectiles--;
							currentMobNum--;
							explode(mobX + (mobWidth / 2), mobY + (mobHeight / 2));
							fall(mobX + (mobWidth / 2), mobY + (mobHeight / 2));
							SoundManager.playDeath();

						} else {
							// small effect
							explode(mobX + (mobWidth / 2), mobY + (mobHeight));
							projectiles.remove(j);
							currentProjectiles--;
						}
						break;
					}

				}
			} else {
				// notPlayerFired
				float pjX = projectiles.get(j).getX();
				float pjY = projectiles.get(j).getY();
				prj = new Rectangle(pjX, pjY, 32, 32);
				mob = new Rectangle(player.getX(), player.getY(), 128, 128);
				if (prj.intersects(mob)) {
					player.takeDamage();
					if (!player.isAlive()) {
						// dead
					}
				}
			}
		}
	}

	public void checkPlayerCollisions() {
		Rectangle p = new Rectangle(player.getX() - (player.getWidth() / 2), player.getY() - (player.getHeight() / 2), player.getWidth(), player.getHeight());
		for (int x = 0; x < mobs.size(); x++) {
			Rectangle m = new Rectangle(mobs.get(x).getX(), mobs.get(x).getY(), mobs.get(x).getImage().getWidth(), mobs.get(x).getImage().getHeight());
			if (p.intersects(m)) {
				explode(m.getX() + (m.getWidth() / 2), m.getY() + (m.getHeight() / 2));
				fall(m.getX() + (m.getWidth() / 2), m.getY() + (m.getHeight() / 2));
				SoundManager.playDeath();
				mobs.remove(x);
				player.takeDamage();
			}
		}

	}

	private void explode(float x, float y) {
		ConfigurableEmitter emitter = null;
		try {
			emitter = ParticleIO.loadEmitter(EmitterUtil.getEmitterInfo("explode").getXML());
		} catch (IOException e) {
			e.printStackTrace();
		}
		emitter.setPosition(x, y);
		explosion.addEmitter(emitter);
	}

	private void fall(float x, float y) {
		ConfigurableEmitter fallEmitter = null;
		try {
			fallEmitter = ParticleIO.loadEmitter(EmitterUtil.getEmitterInfo("fall").getXML());
		} catch (IOException e) {
			e.printStackTrace();
		}
		fallEmitter.setPosition(x, y);
		explosion.addEmitter(fallEmitter);
	}
	
	private void specialAttack() {
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).getX() > 0 && mobs.get(i).getX() < Game.width && mobs.get(i).getY() > 0 && mobs.get(i).getY() < Game.height) {

				explode(mobs.get(i).getX() + (mobs.get(i).getImage().getWidth() / 2), mobs.get(i).getY() + (mobs.get(i).getImage().getHeight() / 2));
				mobs.remove(i);
				currentMobNum--;
			}
		}
	}

	public void nextQueue(int amountToSpawn) {
		this.amountToSpawn = amountToSpawn;
	}

	private boolean started = false;

	public void stageStart(int amountToSpawn) {
		this.amountToSpawn = amountToSpawn;
		started = true;
	}

	private boolean ended = false;

	public void endGame() {
		amountToSpawn = 0;
		amountSpawned = 0;
		ended = true;
	}

	private void loadStages() {
		stages.add(new Stage(1));
		stages.add(new Stage(2));
		currentStage = 0;
		amountToSpawn = stages.get(currentStage).getCurrentSpawnQueue().getAmount();
	}

	public void next() {
		amountToSpawn = 0;
		amountSpawned = 0;
		mobs.clear();
		projectiles.clear();
		currentStage++;
		
		if (currentStage > stages.size()) {
			ended = true;
		}
		
		started = true;
		mousePos = false;
		player.x = Game.width / 2;
		player.y = Game.height - 200;
		SoundManager.loopMusic(SoundManager.beautyfi);
	}
	
	public static Player getPlayer() {
		return player;
	}
}
