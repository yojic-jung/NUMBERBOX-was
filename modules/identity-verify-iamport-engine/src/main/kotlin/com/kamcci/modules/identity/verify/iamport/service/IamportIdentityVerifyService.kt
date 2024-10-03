package com.kamcci.modules.identity.verify.iamport.service

import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.kamcci.modules.identity.verify.dto.response.IdVerifyResponse
import com.kamcci.modules.identity.verify.exception.IdVerifyException
import com.kamcci.modules.identity.verify.iamport.config.IamportMerchantProperty
import com.kamcci.modules.identity.verify.iamport.config.IamportProperty
import com.kamcci.modules.identity.verify.service.IdentityVerifyService
import com.kamcci.modules.identity.verify.vo.CetificationVo
import com.kamcci.modules.identity.verify.vo.IdVerifyMerchantVo
import com.siot.IamportRestClient.Iamport
import com.siot.IamportRestClient.request.AuthData
import com.siot.IamportRestClient.response.AccessToken
import com.siot.IamportRestClient.response.Certification
import com.siot.IamportRestClient.response.IamportResponse
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

/**
 * iamport 문서 참조
 */
@Service
class IamportIdentityVerifyService(
    private val iamportProps: IamportProperty,
    private val iamportMerchantProps: IamportMerchantProperty,
    private val iamport: Iamport,
) : IdentityVerifyService {
    override fun certifyByUserId(uid: String): IdVerifyResponse<CetificationVo> {
        // iamport로 본인인증 요청
        val auth = getAuth().response
        val call: Call<IamportResponse<Certification>> =
            this.iamport.certification_by_imp_uid(auth.token, uid)

        val response: Response<IamportResponse<Certification>> = call.execute()
        if (!response.isSuccessful) {
            throw IdVerifyException(getExceptionMessage(response), response.code())
        }

        // iamport 응답값 null 체크
        if (
            response.body() == null ||
            response.body()?.code == null ||
            response.body()?.message == null ||
            response.body()?.response == null
        ) {
            throw IdVerifyException("본인인증 응답이 존재하지 않습니다.", 400)
        }

        // 응답 반환
        val iamportRes = response.body()!!.response
        val cetificationVo = CetificationVo(
            isCertified = iamportRes.isCertified,
            gender = iamportRes.gender,
            birth = iamportRes.birth,
            phone = iamportRes.phone,
            certifiedAt = iamportRes.certifiedAt,
        )
        return IdVerifyResponse(response.body()!!.code, response.body()!!.message, cetificationVo)
    }

    @Throws(IOException::class)
    private fun getAuth(): IamportResponse<AccessToken> {
        val call =
            this.iamport.token(AuthData(iamportProps.apiKey, iamportProps.apiSecretKey))
        val response = call.execute()

        if (!response.isSuccessful) {
            throw IdVerifyException(getExceptionMessage(response), response.code())
        }

        return response.body()!!
    }

    private fun getExceptionMessage(response: Response<*>): String {
        try {
            val element =
                JsonParser.parseString(response.errorBody()!!.string())
            return element.getAsJsonObject().get("message").asString
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return response.message()
    }

    override fun findMerchantInfo(): IdVerifyMerchantVo =
        IdVerifyMerchantVo(iamportMerchantProps.uid, iamportMerchantProps.idCode)

}