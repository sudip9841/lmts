package com.lmts.service;

import com.lmts.Dao.MusicDao;
import com.lmts.model.MusicModel;
import java.util.List;


public class MusicService {
    private MusicDao musicDao;
    
    public MusicService(){
        this.musicDao = new MusicDao();
    }
    
    public List<MusicModel> getALlMusicList(){
        return this.musicDao.getAllMusicList();
    }
}
