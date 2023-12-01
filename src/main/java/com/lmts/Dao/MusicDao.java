/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmts.Dao;

import com.lmts.helpers.DBUtils;
import com.lmts.model.MusicModel;
import com.lmts.model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MusicDao {
    private Connection con;
    
    public MusicDao(){
        try{
            this.con = DBUtils.getDBConnection();
        }catch(SQLException throwables){
           throwables.printStackTrace();
        }
    }
    
    
    public List<MusicModel> getAllMusicList() {
        List<MusicModel> musicList = new ArrayList<>();

        try {
            String selectQuery = "select music.id, music.name as music_name, "
                    + "music.description as music_description, show_time.time,"
                    + " show_time.date, show_time.available_seat, show_time.id as show_time_id "
                    + "from music inner join show_time on music.id = show_time.music_id";
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    
                    MusicModel musicModel = new MusicModel(
                    resultSet.getInt("id"),
                     resultSet.getString("music_name"),
                     resultSet.getString("music_description"),
                     resultSet.getString("date"),
                     resultSet.getString("time"),
                     resultSet.getInt("available_seat"),
                     resultSet.getInt("show_time_id")      
                    );
                   

                    musicList.add(musicModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musicList;
    }
    
    
    
}
