package com.lukelavin.orbit;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.TypeComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.lukelavin.orbit.collision.*;
import com.lukelavin.orbit.component.HPComponent;
import com.lukelavin.orbit.component.ShieldingComponent;
import com.lukelavin.orbit.control.PlayerControl;
import com.lukelavin.orbit.type.EntityType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lukelavin.orbit.Config.*;
import static com.lukelavin.orbit.RenderLayers.*;

/**
 * Created using the framework of the Java FXGL public game library.
 * All game logic, though, was written by me.
 */
public class OrbitApp extends GameApplication
{
    @Override
    protected void initSettings(GameSettings gameSettings)
    {
        gameSettings.setTitle("orbit");
        gameSettings.setVersion("0.5");

        gameSettings.setWidth(1440);
        gameSettings.setHeight(900);

        gameSettings.setIntroEnabled(false);
        gameSettings.setCloseConfirmation(false);
        gameSettings.setMenuEnabled(false);
        gameSettings.setProfilingEnabled(false);

        gameSettings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    public GameEntity player()
    {
        return (GameEntity) getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
    }
    public PlayerControl playerControl()
    {
        return player().getControlUnsafe(PlayerControl.class);
    }
    public GameEntity enemy(){
        return (GameEntity) getGameWorld().getEntitiesByType(EntityType.ENEMY).get(0);
    }
    public HPComponent enemyHPComponent(){
        return enemy().getComponentUnsafe(HPComponent.class);
    }
    public List<GameEntity> enemies(){
        return getGameWorld().getEntitiesByType(EntityType.ENEMY).stream()
                .map(e -> (GameEntity) e).collect(Collectors.toList());
    }

    private int bossNum;
    private boolean gameOver;

    @Override
    protected void initInput()
    {
        getInput().addAction(new UserAction("UP")
        {
            @Override
            protected void onAction()
            {
                playerControl().up();
            }
        }, UP_KEY);
        getInput().addAction(new UserAction("DOWN")
        {
            @Override
            protected void onAction()
            {
                playerControl().down();
            }
        }, DOWN_KEY);

        getInput().addAction(new UserAction("LEFT")
        {
            @Override
            protected void onAction()
            {
                playerControl().left();
            }
        }, LEFT_KEY);

        getInput().addAction(new UserAction("RIGHT")
        {
            @Override
            protected void onAction()
            {
                playerControl().right();
            }
        }, RIGHT_KEY);

        getInput().addAction(new UserAction("NEXTBOSS")
        {
            @Override
            protected void onActionBegin()
            {   if(!gameOver && noBoss())
                {
                    if(bossNum == 0)
                        getGameWorld().addEntity(EntityFactory.newTutorialBoss());
                    else if (bossNum == 1)
                        getGameWorld().addEntity(EntityFactory.newBoss1());
                    else if(bossNum == 2)
                        getGameWorld().addEntity(EntityFactory.newBoss2());
                    else if(bossNum == 3)
                        getGameWorld().addEntity(EntityFactory.newBoss3());
                    else if(bossNum == 4)
                        getGameWorld().addEntity(EntityFactory.newBoss4());
                    else if(bossNum == 5)
                        getGameWorld().addEntity(EntityFactory.newBoss5());

                    getGameScene().removeUINode(nextBossTip);
                    getGameScene().removeUINode(itemText);
                }
            }
        }, KeyCode.SPACE);

        /*
        These following actions are commands I used to test my program.
        They were useful to be able to test specific things quickly.
         */


//        getInput().addAction(new UserAction("ADDORBITAL")
//        {
//            @Override
//            protected void onActionBegin()
//            {
//                playerControl().addOrbital();
//            }
//        }, KeyCode.NUMPAD1);
//
//        getInput().addAction(new UserAction("REMOVEORBITAL")
//        {
//            @Override
//            protected void onActionBegin()
//            {
//                playerControl().removeOrbital();
//            }
//        }, KeyCode.NUMPAD0);
//
//        getInput().addAction(new UserAction("ADDRANGE")
//        {
//            @Override
//            protected void onAction()
//            {
//                playerControl().setRange(playerControl().getRange() + 1);
//            }
//        }, KeyCode.NUMPAD3);
//        getInput().addAction(new UserAction("REMOVERANGE")
//        {
//            @Override
//            protected void onAction()
//            {
//                playerControl().setRange(playerControl().getRange() - 1);
//            }
//        }, KeyCode.NUMPAD2);
//        getInput().addAction(new UserAction("ADDDAMAGE")
//        {
//            @Override
//            protected void onAction()
//            {
//                playerControl().setDamage(playerControl().getDamage() + 1);
//            }
//        }, KeyCode.NUMPAD5);
//        getInput().addAction(new UserAction("REMOVEDAMAGE")
//        {
//            @Override
//            protected void onAction()
//            {
//                playerControl().setDamage(playerControl().getDamage() - 1);
//            }
//        }, KeyCode.NUMPAD4);
//        getInput().addAction(new UserAction("ADDSPEED")
//        {
//            @Override
//            protected void onActionBegin()
//            {
//                playerControl().setSpeed(playerControl().getSpeed() + 1);
//            }
//        }, KeyCode.NUMPAD7);
//        getInput().addAction(new UserAction("REMOVESPEED")
//        {
//            @Override
//            protected void onAction()
//            {
//                playerControl().setSpeed(playerControl().getSpeed() - 1);
//            }
//        }, KeyCode.NUMPAD6);
//        getInput().addAction(new UserAction("REVERSESPEED") {
//            @Override
//            protected void onActionBegin() {
//                playerControl().setSpeed(playerControl().getSpeed() * -1);
//            }
//        }, KeyCode.MINUS);
//        getInput().addAction(new UserAction("NEWTESTBOSS") {
//            @Override
//            protected void onActionBegin() {
//                getGameWorld().addEntity(EntityFactory.newTutorialBoss());
//            }
//        }, KeyCode.EQUALS);
//        getInput().addAction(new UserAction("LOSEHP") {
//            @Override
//            protected void onActionBegin() {
//                loseHP();
//            }
//        }, KeyCode.PERIOD);
//        getInput().addAction(new UserAction("SKIPBOSS")
//        {
//            @Override
//            protected void onActionBegin()
//            {
//                bossDeath();
//                getGameWorld().getEntitiesByType(EntityType.ENEMY).forEach(entity -> entity.removeFromWorld());
//                getGameWorld().getEntitiesByType(EntityType.CHASER).forEach(entity -> entity.removeFromWorld());
//                getGameWorld().addEntity(EntityFactory.newPopUp("Boss skipped", Duration.millis(500)));
//            }
//        },KeyCode.C);
    }

    @Override
    protected void initAssets()
    {
        getAssetLoader().cache();
    }

    @Override
    protected void initGame()
    {
        bossNum = 0; //initializes the variable to track the current boss

        //puts text on the screen to guide the player
        nextBossTip = new Label("Press Space to start the game.");
        nextBossTip.setTextFill(Color.WHITE);
        nextBossTip.setTranslateX(200);
        nextBossTip.setTranslateY(200);
        getGameScene().addUINode(nextBossTip);

        //background music from Artofescapism on freemusicarchive.org
        Music bgm = getAssetLoader().loadMusic("bgm.mp3");
        bgm.setCycleCount(Integer.MAX_VALUE);
        getAudioPlayer().playMusic("bgm.mp3");
        getAudioPlayer().setGlobalMusicVolume(0.1);

        //makes the background black
        getGameWorld().addEntity(EntityFactory.newBackground());

        //spawns the player in the middle of the of the screen, slightly left
        getGameWorld().addEntity(EntityFactory.newPlayer((getWidth() / 4)  - TILE_SIZE * 2, getHeight() / 2));
    }

    @Override
    protected void initPhysics()
    {
        //adds all the collision handlers which trigger when entities of specific types meet
        getPhysicsWorld().addCollisionHandler(new OrbitalEnemyHandler(EntityType.ORBITAL, EntityType.ENEMY));
        getPhysicsWorld().addCollisionHandler(new PlayerProjectileHandler(EntityType.PLAYER, EntityType.PROJECTILE));
        getPhysicsWorld().addCollisionHandler(new OrbitalProjectileHandler(EntityType.ORBITAL, EntityType.PROJECTILE));
        getPhysicsWorld().addCollisionHandler(new PlayerPickupHandler(EntityType.PLAYER, EntityType.PICKUP));
        getPhysicsWorld().addCollisionHandler(new PlayerEnemyHandler(EntityType.PLAYER, EntityType.ENEMY));
        getPhysicsWorld().addCollisionHandler(new PlayerChaserHandler(EntityType.PLAYER, EntityType.CHASER));
    }

    //the list that stores the players' hearts
    List<ImageView> hearts = new ArrayList<ImageView>();
    @Override
    protected void initUI()
    {
        //start the game with 5 hearts
        for(int i = 0; i < 5; i++){
            gainHP();
        }
    }

    @Override
    protected void onUpdate(double v)
    {
        if(!noBoss()) //if there's a boss on the screen, update its corresponding health bars
            updateBossHealth();
    }

    public void loseHP()
    {
        playerControl().setHP(playerControl().getHP() - 1); //remove 1 hp from the player in PlayerControl

        if(hearts.size() > 0) // if the game doesn't end, remove the last heart from the list and from the gameScene
            getGameScene().removeUINode(hearts.remove(hearts.size() - 1));

        if(hearts.size() <= 0) // if the game would end, trigger a game over sequence
        {
            gameOver();
        }
    }

    public void gainHP()
    {
        if(hearts.size() < 50)
        {
            //make a new heart and set its position
            ImageView heart = new ImageView(new Image("assets/textures/" + "heart.png"));
            heart.setTranslateX(5 + (hearts.size() % 10 * 35));
            heart.setTranslateY(5 + (hearts.size() / 10 * 30));

            //add the heart to the array for future reference
            hearts.add(heart);
            //add the heart to the gameScene to display it
            getGameScene().addUINode(heart);
        }
        else //if the player has reached the health cap, tell them. (this is impossible right now)
            getGameWorld().addEntity(EntityFactory.newPopUp("Max health!", Duration.seconds(1)));
    }

    double initialBossHP;
    public void initBossHealth(double max)
    {
        bossHealthMax = new EntityView(new Rectangle(getWidth(), 50, Color.DIMGREY));
        bossHealthMax.setRenderLayer(bottom);
        bossHealthMax.setTranslateY(getHeight() - 50);

        initialBossHP = max;

        System.out.println(initialBossHP);

        bossHealthCurrent = new EntityView(new Rectangle(getWidth(), 50, Color.CRIMSON));
        bossHealthCurrent.setRenderLayer(middle);
        bossHealthCurrent.setTranslateY(getHeight() - 50);

        getGameScene().addUINodes(bossHealthMax, bossHealthCurrent);
    }

    EntityView bossHealthMax;
    EntityView bossHealthCurrent;
    public void updateBossHealth()
    {
        getGameScene().removeUINode(bossHealthCurrent);
        if(enemies().size() == 1)
            bossHealthCurrent = new EntityView(new Rectangle(getWidth() * (enemyHPComponent().getValue() / initialBossHP), 50, Color.CRIMSON));
        else
            bossHealthCurrent = new EntityView(new Rectangle(getWidth() * (enemyHPSum() / initialBossHP), 50, Color.CRIMSON));
        bossHealthCurrent.setRenderLayer(middle);
        bossHealthCurrent.setTranslateY(getHeight() - 50);
        getGameScene().addUINode(bossHealthCurrent);
    }

    private double enemyHPSum()
    {
        double sum = 0;
        for(GameEntity enemy : enemies())
            sum += enemy.getComponentUnsafe(HPComponent.class).getValue();
        return sum;
    }


    Label nextBossTip;
    Label itemText;
    public void bossDeath()
    {
        //clear projectiles
        List<Entity> ents = getGameWorld().getEntities();
        for(Entity e : ents){
            if(e.getComponentUnsafe(TypeComponent.class).getValue() == (EntityType.PROJECTILE))
                e.removeFromWorld();
        }

        //clears tips/boss health bars
        List<Node> nodes = getGameScene().getUINodes();
        for(int i = nodes.size() - 1; i >= 0; i--)
        {
            if(nodes.get(i) instanceof Label)
                getGameScene().removeUINode(nodes.get(i));
        }

        bossHealthCurrent.removeFromScene();
        bossHealthMax.removeFromScene();

        if(bossNum == 0)
        {
            itemText = new Label("Here's your first item. You'll get "  + "\n" +
                                      "an item and HP after each boss.");
            itemText.setTextFill(Color.WHITE);
            itemText.setTranslateX(getWidth() / 2 - (itemText.getText().length() / 2 * itemText.getFont().getSize() / 2));
            itemText.setTranslateY(itemText.getFont().getSize());
            getGameScene().addUINode(itemText);
        }

        if(bossNum == 5){
            nextBossTip = new Label("Congratulations! You beat all 5 bosses!");
            nextBossTip.setTextFill(Color.WHITE);
            nextBossTip.setTranslateX(200);
            nextBossTip.setTranslateY(200);
        }
        else
        {
            nextBossTip = new Label("Press space when ready to spawn the next boss.");
            nextBossTip.setTextFill(Color.WHITE);
            nextBossTip.setTranslateX(200);
            nextBossTip.setTranslateY(200);
            generateItem();
            gainHP();
        }
        getGameScene().addUINode(nextBossTip);


        bossNum++;
    }

    public void generateItem()
    {
        int specialItemRand = (int) (Math.random() * 10);

        if(specialItemRand == 0)
            spawnSpecialItem();
        else
            spawnNormalItem();
    }

    private void spawnSpecialItem()
    {
        /*
        Special items are much more powerful than the normal items.
        They can include extra orbitals, shielding orbitals, and similar
        game-changing items.
         */
        boolean successful = false;

        while(!successful)
        {
            int item = (int) (Math.random() * 3);

            if (item == 0 && player().getComponentUnsafe(ShieldingComponent.class) == null)
            {
                getGameWorld().addEntity(PickupFactory.newShieldingPickup(getWidth() / 2 - 37.5, 75));
                successful = true;
            }
            else if(item == 1)
            {
                getGameWorld().addEntity(PickupFactory.newExtraOrbitalPickup(getWidth() / 2 - 37.5, 75));
                successful = true;
            }
            else if(item == 2)
            {
                getGameWorld().addEntity(PickupFactory.newTwoExtraOrbitalPickup(getWidth() / 2 - 37.5, 75));
                successful = true;
            }
        }
    }

    private final double maxRange = 300;
    private final double maxDamage = 50;
    private final double maxSpeed = 10;
    private final double maxOrbitalSpeed = 15;

    private void spawnNormalItem()
    {
        boolean successful = false;

        //will always terminate, because there aren't enough bosses to hit max everything
        while(!successful)
        {
            int item = (int) (Math.random() * 4);

            double range = playerControl().getRange();
            double damage = playerControl().getDamage();
            double speed = playerControl().getSpeed();
            double orbitalSpeed = playerControl().getOrbitalSpeed();


            if (item == 0 && range < maxRange)
            {
                getGameWorld().addEntity(PickupFactory.newRangePickup(getWidth() / 2 - 37.5, 75));
                successful = true;
            }
            else if (item == 1 && damage < maxDamage)
            {
                getGameWorld().addEntity(PickupFactory.newDamagePickup(getWidth() / 2 - 37.5, 75));
                successful = true;
            }
            else if (item == 2 && speed < maxSpeed)
            {
                getGameWorld().addEntity(PickupFactory.newSpeedPickup(getWidth() / 2 - 37.5, 75));
                successful = true;
            }
            else if (item == 3 && orbitalSpeed < maxOrbitalSpeed)
            {
                getGameWorld().addEntity(PickupFactory.newOrbitalSpeedPickup(getWidth() / 2 - 37.5, 75));
                successful = true;
            }
        }
    }

    private void gameOver()
    {
        //clear enemies and projectiles
        clearEntityType(EntityType.ENEMY);
        clearEntityType(EntityType.CHASER);

        resetGameScene();

        Label gameOverText = new Label("Game Over!");
        gameOverText.setTextFill(Color.WHITE);
        gameOverText.setTranslateX(200);
        gameOverText.setTranslateY(200);
        getGameScene().addUINode(gameOverText);

        Label finalScore = new Label("You lost at boss #" + bossNum + "!");
        finalScore.setTextFill(Color.WHITE);
        finalScore.setTranslateX(200);
        finalScore.setTranslateY(220);
        getGameScene().addUINode(finalScore);

        gameOver = true;
    }

    private void clearEntityType(EntityType type)
    {
        List<Entity> ents = getGameWorld().getEntities();
        for(int i = ents.size() - 1; i >= 0; i--){
            if(ents.get(i).getComponentUnsafe(TypeComponent.class).getValue() == type)
                ents.get(i).removeFromWorld();
        }
    }

    private void resetGameScene()
    {
        List<Node> nodes = getGameScene().getUINodes();
        for(int i = nodes.size() - 1; i >= 0; i--)
            getGameScene().removeUINode(nodes.get(i));
    }

    private boolean noBoss()
    {
        return getGameWorld().getEntitiesByType(EntityType.ENEMY).isEmpty();
    }

    public int getBossNum()
    {
        return bossNum;
    }

    public void setBossNum(int bossNum)
    {
        this.bossNum = bossNum;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
