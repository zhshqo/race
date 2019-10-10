package com.cnnp.race.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.cnnp.race.demo.entity.Judge;

@Repository
public interface JudgeMapper {
	
	@Select(value= {"select * from judge"})
	public List<Judge> selectAll();
	
	@Delete("delete from judge where id=#{id}")
	public int delete(Judge judge);
	
}
