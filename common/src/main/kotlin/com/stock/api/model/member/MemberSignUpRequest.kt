package com.stock.api.model.member

import com.stock.api.model.enums.AuthVendorType
import com.stock.api.model.enums.DeviceType
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class MemberSignUpRequest (
    @NotNull
    val authVendorType: AuthVendorType,

    @NotNull
    val accountId: String,

    @NotNull
    @Size(min = 5, max = 100)
    @Email
    val email: String,

    @NotNull
    @Size(min = 4, max = 10)
    val nickName: String,

    @URL
    val profileImageUrl: String?,

    val gender: String?,
    val age: Int?,

    @NotNull
    val kakaoNumber: String,
    val kakaoNickname: String?,

    @NotNull
    val device: DeviceType
)
