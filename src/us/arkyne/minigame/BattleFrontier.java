package us.arkyne.minigame;

import us.arkyne.minigame.command.BattleFrontierCommand;
import us.arkyne.minigame.event.EventListener;
import us.arkyne.server.minigame.Minigame;

public class BattleFrontier extends Minigame
{
	private EventListener eventListener;
	
	public BattleFrontier()
	{
		super(MinigameMain.getInstance(), "BattleFrontier", "BF-G");
		
		//Try not to use this method
	}
	
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
		
		
		super.onUnload();
	}
}