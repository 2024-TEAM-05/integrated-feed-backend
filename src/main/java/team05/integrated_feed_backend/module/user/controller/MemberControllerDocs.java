package team05.integrated_feed_backend.module.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.module.user.dto.request.MemberSignupReq;

@Tag(name = "Member", description = "멤버 관련 API")
public interface MemberControllerDocs {
	@Operation(summary = "회원가입")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "202", description = "회원가입이 완료되었습니다. 이메일 인증을 진행해 주세요.", useReturnTypeSchema = true)
	})
	BaseApiResponse<Void> signUp(MemberSignupReq request);
}
