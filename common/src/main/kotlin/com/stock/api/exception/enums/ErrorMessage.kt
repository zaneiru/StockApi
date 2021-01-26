package com.stock.api.exception.enums

enum class ErrorMessage(private val errorCodeParam: Int, private val messageParam: String) {

    // 100000 ~ 199999 : 회원
    // 100100 : 회원가입
    MEMBER_SIGN_UP_ALREADY_MEMBER(100100, "해당 계정은 이미 가입된 사용자입니다."),
    MEMBER_NOT_EXIST(100110, "회원 정보가 존재하지 않습니다."),
    MEMBER_UUID_IS_EMPTY(100110, "인증 정보가 헤더에 존재하지 않습니다."),

    // 200000 ~ 299999 : 종목 관련 (추천 테마, 증권사 리포트 등, ... etc)
    // 200000 ~ 209999 : 추천 테마
    RECOMMENDED_ITEM_NOT_EXIST(200000, "테마 추천 정보가 존재하지 않습니다."),

    // 950000 ~ 959999 : 공통
    PARAM_EMAIL_EMPTY(950000, "이메일을 요청해야 합니다."),
    PARAM_KAKAO_NUMBER_EMPTY(950100, "카카오 계정 번호를 요청해야 합니다."),

    // 950100 ~ 950199 : 코멘트
    COMMENT_ID_NOT_VALID(950100, "코멘트 정보[ID]가 유효하지 않습니다."),
    COMMENT_MEMBER_NOT_VALID(950101, "코멘트 작성자 정보가 유효하지 않습니다."),
    COMMENT_NOT_OWNER(950102, "본인이 작성한 댓글이 아닙니다."),
    COMMENT_NOT_EXIST(950103, "댓글이 존재하지 않습니다."),

    ETC_BAD_REQUEST(90001, "잘못된 요청입니다."),
    ETC_INTERNAL_ERROR(90002, "내부 오류입니다."),
    ETC_FORBIDDEN(90003, "권한이 없습니다."),
    ETC_DOMAIN_VALIDATION_ERROR(90006, "validation 오류"),
    ETC_UNKNOWN_ERROR(99999, "에러");

    //@JvmName("getMessage")
    fun getMessage(): String {
        return this.messageParam
    }

    //@JvmName("getErrorCode")
    fun getErrorCode(): Int {
        return this.errorCodeParam
    }
}
