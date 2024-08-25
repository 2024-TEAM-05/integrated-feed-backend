package team05.integrated_feed_backend.module.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberSignupReq(
	@NotBlank(message = "계정은 필수값입니다.")
	@Schema(description = "계정", example = "uijin-j")
	String account,

	@NotBlank(message = "이메일은 필수값입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	@Schema(description = "이메일", example = "email@gmail.com")
	String email,

	@NotBlank(message = "비밀번호는 필수값입니다.")
	@Schema(description = "비밀번호", example = "password00!")
	String password
) {
}
