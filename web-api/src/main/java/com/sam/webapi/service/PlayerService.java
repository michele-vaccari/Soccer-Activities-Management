package com.sam.webapi.service;

import com.sam.webapi.dto.PlayerDto;

public interface PlayerService {
	PlayerDto getPlayer(Integer id);
	void updatePlayer(Integer id, String userEmail, PlayerDto playerDto);
	void deletePlayer(Integer id, String userEmail);
}
