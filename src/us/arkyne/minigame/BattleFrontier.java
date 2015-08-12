package us.arkyne.minigame;

import us.arkyne.minigame.command.BattleFrontierCommand;
import us.arkyne.minigame.event.EventListener;
import us.arkyne.minigame.game.BFGame;
import us.arkyne.server.minigame.Minigame;

public class BattleFrontier extends Minigame
{
	private EventListener eventListener;
	
	public BattleFrontier()
	{
		super(MinigameMain.getInstance(), "BattleFrontier", "BF-G");
		
		//Try not to use this method
	}
	
	//Create game with command, really simple: /bf creategame, then add a lobby and arena
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		
		eventListener = new EventListener();
		
		getMain().getCommandHandler().registerCommand(MinigameMain.getInstance(), BattleFrontierCommand.class);
		
		//Load all games
	}

	@Override
	public void onUnload()
	{
		getMain().getCommandHandler().unregisterCommand(BattleFrontierCommand.class);
		
		super.onUnload();
	}

	@Override
	public int createGame()
	{
		int id = getNextId();
		
		if (!containsGame(id))
		{
			BFGame game = new BFGame(id);
			
			addGame(game);
			save(game);
			
			return id;
		}
		
		return -1;
	}
}