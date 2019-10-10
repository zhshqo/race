package com.cnnp.race.demo.vo;

import java.util.ArrayList;
import java.util.List;

import com.cnnp.race.demo.entity.Game;
import com.cnnp.race.demo.entity.Judge;

public class CanAddJudgeParamVo {
	Game game;;
	List<Judge> judges;
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public List<Judge> getJudges() {
		return judges;
	}
	public void setJudges(List<Judge> judges) {
		this.judges = judges;
	}
	
	
}
