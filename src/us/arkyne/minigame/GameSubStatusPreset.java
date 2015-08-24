package us.arkyne.minigame;

import org.bukkit.ChatColor;

import us.arkyne.server.game.status.IGameSubStatus;

public enum GameSubStatusPreset implements IGameSubStatus
{
	PREGAME_STANDBY( -1, null, null),
	PREGAME_COUNTDOWN(5, "{time} seconds until the game starts!", ChatColor.GOLD + "Game starting in:"),
	
	GAME_COUNTDOWN(5,  ChatColor.GREEN + "{time} seconds until the game starts!", ChatColor.GOLD + "Game starting in:"),
	GAME_PLAYING(  50, ChatColor.RED + "{time} seconds until the game ends!", null),
	
	GAME_END(      5,  null, null),
	GAME_REGEN(   -1,  null, null);
	
	private int duration;
	
	private String timeString;
	private String screenString;
	
	private GameSubStatusPreset(int duration, String timeString, String screenString)
	{
		this.duration = duration;
		
		this.timeString = timeString;
		this.screenString = screenString;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public String getTimeString()
	{
		return timeString;
	}
	
	public String getScreenString()
	{
		return screenString;
	}
}