package com.cnnp.race.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnnp.race.demo.entity.Judge;
import com.cnnp.race.demo.mapper.JudgeMapper;

@CrossOrigin
@Controller
@RequestMapping("/judge")
public class JudgeController {
	
	@Autowired
	JudgeMapper judgeMapper;
	
	@RequestMapping(value="/list",method=RequestMethod.GET, produces= {"application/json;charset=utf-8"})
	@ResponseBody
	public List<Judge> list(){
		return judgeMapper.selectAll();
	}

}
