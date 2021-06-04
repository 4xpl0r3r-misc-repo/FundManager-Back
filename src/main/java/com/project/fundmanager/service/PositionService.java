package com.project.fundmanager.service;

import com.project.fundmanager.entity.Position;
import com.project.fundmanager.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class PositionService {

    @Autowired
    PositionMapper positionMapper;

    public Position getById(long id){
        return positionMapper.getById(id);
    }

    public Position getByUId(long uid){
        return positionMapper.getByUId(uid);
    }

    public Position getByUIdAndFId(long uid,long fid){
        return positionMapper.getByUIdAndFId(uid,fid);
    }

    public void addNewPosition(Position position){
        if(positionMapper.insert(position) == 0){
            throw new RuntimeException("SQL didn't modified");
        }
    }

    public void updatePosition(Position position){
        if(positionMapper.update(position) == 0){
            throw new RuntimeException("SQL didn't modified");
        }
    }
}
