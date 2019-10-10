package com.cnnp.race.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.cnnp.race.demo.entity.Game;

@Repository
public interface GameMapper {

	@Select(value= {"<script>"
			+ "select * from game where 1=1 "
			+ "<if test='game_no!=null and game_no!=\"\"'>"
			+ "and game_no like concat('%',#{game_no},'%') "
			+ "</if>"
			+ "<if test='start_time!=null and start_time!=\"\"'>"
			+ "and start_time like concat('%',#{start_time},'%') "
			+ "</if>"
			+ "<if test='location!=null and location!=\"\"'>"
			+ "and location like concat('%',#{location},'%') "
			+ "</if>"
			+ "<if test='judge_names!=null and judge_names!=\"\"'>"
			+ "and judge_names like concat('%',#{judge_names},'%') "
			+ "</if>"
			+ "<if test='judge_nos!=null and judge_nos!=\"\"'>"
			+ "and judge_nos like concat('%',#{judge_nos},'%') "
			+ "</if>"
			+ "</script>"})
	public List<Game> selectAll(Game params);
	
	@Select("<script>"
			+ "select * from game where 1=1 "
			+ "<if test='date!=null and date!=\"\"'>"
			+ "and start_time like #{date} "
			+ "</if>"
			+ "<if test='judgeNo!=null and judgeNo!=\"\"'>"
			+ "and judge_nos like #{judgeNo} "
			+ "</if>"
			+ "<if test='location!=null and location!=\"\"'>"
			+ "and location like #{location} "
			+ "</if>"
			+ "</script>")
	public List<Game> selectAllLike(@Param("date")String date, @Param("judgeNo")String judgeNo, @Param("location")String location);
	
	@Select(value= {"insert into game (id,game_no,start_time,location,comment) values(#{id}, #{game_no} ,#{start_time} ,#{location},#{comment})"})
	public void insert(Game params);
	
	@Delete("delete from game where id = #{id}")
	public int delete(Game game);
	
	@Update("update game set game_no=#{game_no},start_time=#{start_time},location=#{location},judge_names=#{judge_names},judge_nos=#{judge_nos},comment=#{comment} where id = #{id}")
	public void update(Game game);
}
