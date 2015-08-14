package us.arkyne.minigame;

import us.arkyne.minigame.command.BattleFrontierCommand;
import us.arkyne.minigame.event.EventListener;
import us.arkyne.minigame.game.BFGame;
import us.arkyne.server.minigame.Minigame;

public class BattleFrontier extends Minigame
{
	public BattleFrontier()
	{
		super(MinigameMain.getInstance(), "BattleFrontier", "BF");
		
		//Try not to use this method
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		
		new EventListener();
		
		getMain().getCommandHandler().registerCommand(MinigameMain.getInstance(), BattleFrontierCommand.class);
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
		int id = getGameHandler().getNextId();
		
		if (!getGameHandler().containsGame(id))
		{
			BFGame game = new BFGame(this, id);
			
			getGameHandler().addGame(game);
			getGameHandler().save(game);
			
			return id;
		}
		
		return -1;
	}
}