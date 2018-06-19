package com.gmail.zendarva.parachronology.Configuration.domain;

/**
 * Created by James on 3/3/2016.
 */
public class DropData {
	public String mobName;
	public int simpleMomentChance;
	public int momentChance;
	public int complexMomentChance;

	public DropData(String mobName, int simpleMomentChance, int momentChance, int complexMomentChance) {
		this.mobName = mobName;
		this.simpleMomentChance = simpleMomentChance;
		this.momentChance = momentChance;
		this.complexMomentChance = complexMomentChance;
	}
}
