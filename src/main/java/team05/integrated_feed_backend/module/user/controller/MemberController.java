package team05.integrated_feed_backend.module.user.controller;

import static org.springframework.http.HttpStatus.*;
import static team05.integrated_feed_backend.common.code.StatusCode.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.module.user.dto.request.MemberSignupReq;
import team05.integrated_feed_backend.module.user.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {
	private final MemberService memberService;

	@PostMapping
	@ResponseStatus(ACCEPTED)
	public BaseApiResponse<Void> signUp(@Valid MemberSignupReq request) {
		memberService.signUp(request);
		return BaseApiResponse.of(SIGN_UP_ACCEPTED);
	}
}
