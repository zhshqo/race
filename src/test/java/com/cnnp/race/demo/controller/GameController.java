package com.cnnp.race.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnnp.race.demo.entity.Game;
import com.cnnp.race.demo.entity.Judge;
import com.cnnp.race.demo.mapper.GameMapper;
import com.cnnp.race.demo.vo.AjaxRespVo;
import com.cnnp.race.demo.vo.CanAddJudgeParamVo;

@CrossOrigin
@Controller
@RequestMapping(value= "/game")
public class GameController {
	
	@Autowired
	GameMapper gameMapper;
	
	@RequestMapping(value="/str", method = RequestMethod.GET, produces="text/plain;charset=utf-8")
	@ResponseBody
	public String hello() {
		return "测试";
	}
	
	@RequestMapping(value= "/test", method=RequestMethod.GET, produces= {"application/json;charset=utf-8"})
	@ResponseBody
	public List<Game> test(){
		List<Game> list = new ArrayList<Game>();
		Game game1= new Game();
		game1.setId("测试1");
		game1.setLocation("sdfsdf");
		list.add(game1);
		Game game2= new Game();
		game2.setId("测试2");
		game2.setLocation("sdfsdf");
		list.add(game2);
		return list;
	}
	
	@RequestMapping(value= {"/list"}, method=RequestMethod.POST, produces= {"application/json;charset=utf-8"})
	@ResponseBody
	public List<Game> gameList(@RequestBody Game params){
		List<Game> games = gameMapper.selectAll(params);
		return games;
	}
	
	@Transactional
	@RequestMapping(value= {"/save"}, method=RequestMethod.POST, produces= {"application/json;charset=utf-8"})
	@ResponseBody
	public AjaxRespVo gameSave(@RequestBody Game params){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//通过录入地点查询场次，针对每个场次，如果开始时间小于录入时间，且开始时间+2小时仍然小于录入时间，则通过，如果开始时间大于录入时间，且开始时间-2小时仍然大于录入时间，则通过
		List<Game> games = gameMapper.selectAllLike("","","%"+params.getLocation()+"%");
		for (Game game : games) {
			try {
				if(dateFormat.parse(game.getStart_time()).equals(dateFormat.parse(params.getStart_time()))) {
					return AjaxRespVo.fail("已存在相同时间"+params.getStart_time()+"相同位置"+params.getLocation()+"的场次"+game.getGame_no());
				}
				if(dateFormat.parse(game.getStart_time()).before(dateFormat.parse(params.getStart_time()))) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateFormat.parse(game.getStart_time()));
					calendar.add(Calendar.HOUR_OF_DAY, 2);
					if(calendar.getTime().after(dateFormat.parse(params.getStart_time()))) {
						return AjaxRespVo.fail("已存在相同时间"+params.getStart_time()+"相同位置"+params.getLocation()+"的场次"+game.getGame_no());
					}
				}
				if(dateFormat.parse(game.getStart_time()).after(dateFormat.parse(params.getStart_time()))) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateFormat.parse(game.getStart_time()));
					calendar.add(Calendar.HOUR_OF_DAY, -2);
					if(calendar.getTime().before(dateFormat.parse(params.getStart_time()))) {
						return AjaxRespVo.fail("已存在相同时间"+params.getStart_time()+"相同位置"+params.getLocation()+"的场次"+game.getGame_no());
					}
				}
			} catch (ParseException e) {
				return AjaxRespVo.fail(e.getLocalizedMessage());
			}
		}
		
		if(params.getId()==null) {
			params.setId(UUID.randomUUID().toString());
		}
		try {
			gameMapper.insert(params);
		} catch (Exception e) {
			return AjaxRespVo.fail(e.getLocalizedMessage());
		}
		return AjaxRespVo.success(null);
	}
	
	@Transactional
	@RequestMapping(value= {"/delete"}, method=RequestMethod.POST, produces= {"application/json;charset=utf-8"})
	@ResponseBody
	public AjaxRespVo gameDel(@RequestBody Game game){
		try {
			gameMapper.delete(game);
		} catch (Exception e) {
			return AjaxRespVo.fail(e.getLocalizedMessage());
		}
		return AjaxRespVo.success(null);
	}
	
	@Transactional
	@RequestMapping(value= {"/update"}, method=RequestMethod.POST, produces= {"application/json;charset=utf-8"})
	@ResponseBody
	public AjaxRespVo saveJudge(@RequestBody CanAddJudgeParamVo param) {
		
		Game game = param.getGame();
		List<Judge> judges = param.getJudges();
		List<Game> games = null;
		String judge_names = "";
		String judge_nos = "";
		for(Judge judge:judges) {
			//根据裁判查询所有场次的次数，如果大于5次，则返回
			games = gameMapper.selectAllLike(null, "%"+judge.getJudge_no()+"%", null);
			if(games.size()>=5) {
				return AjaxRespVo.fail("裁判"+judge.getJudge_name()+"在整个赛事中的执法总场次已经超过5次");
			}
			//根据开始时间中的日期和裁判查询所有场次的次数，如果大于2次，则返回
			games = gameMapper.selectAllLike("%"+game.getStart_time().substring(0, 10)+"%", "%"+judge.getJudge_no()+"%", null);
			if(games.size()>=2) {
				return AjaxRespVo.fail("裁判"+judge.getJudge_name()+"在"+game.getStart_time().substring(0, 10)+"的执法总场次已经超过2次");
			}
			if(judge_names.length()==0||judge_names.endsWith(",")) {
				judge_names = judge_names + judge.getJudge_name();
			}else {
				judge_names = judge_names + "," + judge.getJudge_name();
			}
			if(judge_nos.length()==0||judge_nos.endsWith(",")) {
				judge_nos = judge_nos + judge.getJudge_no();
			}else {
				judge_nos = judge_nos + "," + judge.getJudge_no();
			}
		}
		
		//保存
		game.setJudge_names(judge_names);
		game.setJudge_nos(judge_nos);
		try {
			gameMapper.update(game);
		} catch (Exception e) {
			return AjaxRespVo.fail(e.getLocalizedMessage());
		}
		
		return AjaxRespVo.success(null);
		
	}

}
