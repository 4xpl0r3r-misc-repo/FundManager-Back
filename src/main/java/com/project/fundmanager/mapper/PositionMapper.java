package com.project.fundmanager.mapper;

import com.project.fundmanager.entity.Position;
import com.project.fundmanager.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PositionMapper {

    @Select("Select * FROM position WHERE id =${id}")
    Position getById(@Param("id") long id);

    @Select("Select * FROM position WHERE uid =${uid}")
    Position getByUId(@Param("uid") long uid);

    @Select("Select * FROM position WHERE uid =${uid} AND fid = ${fid}")
    Position getByUIdAndFId(@Param("uid") long uid,@Param("fid")long fid);


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO position (uid, fid, count, \"haveCost\") VALUES (#{position.uid}, #{position.fid}, #{position.count}, #{position.haveCost})")
    int insert(@Param("position") Position position);

    @Update("UPDATE position SET uid = #{position.uid},fid = #{position.fid}, fid = #{position.fid} ,count = #{position.count},haveCost = #{position.haveCost} WHERE id = #{position.id}")
    int update(@Param("position") Position position);
}
