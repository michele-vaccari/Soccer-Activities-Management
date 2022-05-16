package com.sam.webapi.service;

import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.AdminDto;
import com.sam.webapi.dto.MatchDto;
import com.sam.webapi.model.AdminUser;
import com.sam.webapi.model.Report;
import com.sam.webapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class MatchServiceImpl implements MatchService {

	private final UserRepository userRepository;
	private final RefereeRepository refereeRepository;
	private final MatchRepository matchRepository;
	private final ReportRepository reportRepository;

	@Autowired
	public MatchServiceImpl(UserRepository userRepository,
							RefereeRepository refereeRepository,
							MatchRepository matchRepository,
							ReportRepository reportRepository) {
		this.userRepository = userRepository;
		this.refereeRepository = refereeRepository;
		this.matchRepository = matchRepository;
		this.reportRepository = reportRepository;
	}

	@Override
	@Transactional
	public void updateMatch(Integer id, MatchDto matchDto, String adminEmail) {
		isAuthorizedUser(adminEmail);

		var match = matchRepository.findById(id);
		if (match.isEmpty())
			throw new MatchNotFoundException();

		var referee = refereeRepository.findById(matchDto.getRefereeId());
		if (referee.isEmpty())
			throw new BadRequestException();

		match.get().setDate(matchDto.getDate());
		match.get().setPlace(matchDto.getPlace());

		matchRepository.save(match.get());

		var reportMaxId = reportRepository.getMaxId();
		var report = new Report(
				reportMaxId == null ? 1 : ++reportMaxId,
				id,
				matchDto.getRefereeId()
		);

		reportRepository.save(report);
	}

	private void isAuthorizedUser(String userEmail) {
		var user = userRepository.findByEmailAndActive(userEmail,"Y");
		if (user == null ||
			!user.getRole().equals("Admin"))
			throw new UnauthorizedException();
	}
}
