package com.zosh.service;

import java.util.List;

import com.zosh.models.Reels;
import com.zosh.models.User;

public interface ReelsService {
	
	public Reels createReels(Reels reel,User user);
	
	public List<Reels> findAllReels();
	
	public List<Reels> findUserReel (Integer userId) throws Exception; 
}
