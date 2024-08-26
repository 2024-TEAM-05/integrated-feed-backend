package team05.integrated_feed_backend.module.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.module.user.dto.request.MemberSignupReq;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	
	public void signUp(MemberSignupReq request) {
		// TODO: 회원가입 로직 구현
	}
}
