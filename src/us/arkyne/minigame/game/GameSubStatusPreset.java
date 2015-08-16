package us.arkyne.minigame.game;

import org.bukkit.ChatColor;

import us.arkyne.server.game.status.IGameSubStatus;

public enum GameSubStatusPreset implements IGameSubStatus
{
	PREGAME_STANDBY( -1, null),
	PREGAME_COUNTDOWN(5, "{time} seconds until the game starts!"),
	
	GAME_COUNTDOWN(5,  ChatColor.GREEN + "{time} seconds until the game starts!"),
	GAME_PLAYING(  20, ChatColor.RED + "{time} seconds until the game ends!"),
	GAME_END(      5,  null),
	GAME_REGEN(   -1,  null);
	
	private int duration;
	private String timeString;
	
	private GameSubStatusPreset(int duration, String timeString)
	{
		this.duration = duration;
		this.timeString = timeString;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public String getTimeString()
	{
		return timeString;
	}
}