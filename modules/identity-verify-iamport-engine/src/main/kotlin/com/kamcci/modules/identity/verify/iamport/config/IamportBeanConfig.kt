package com.kamcci.modules.identity.verify.iamport.config

import com.google.gson.GsonBuilder
import com.siot.IamportRestClient.Iamport
import com.siot.IamportRestClient.request.ScheduleEntry
import com.siot.IamportRestClient.request.escrow.EscrowLogisInvoiceData
import com.siot.IamportRestClient.response.PaymentBalanceEntry
import com.siot.IamportRestClient.response.Schedule
import com.siot.IamportRestClient.response.escrow.EscrowLogisInvoice
import com.siot.IamportRestClient.serializer.BalanceEntrySerializer
import com.siot.IamportRestClient.serializer.EscrowInvoiceEntrySerializer
import com.siot.IamportRestClient.serializer.ScheduleEntrySerializer
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
@EnableConfigurationProperties(value = [IamportProperty::class, IamportMerchantProperty::class])
class IamportBeanConfig(
    private val iamportProperty: IamportProperty
) {

    @Bean
    fun iamport(): Iamport {
        val readTimeout = 30L
        val connectTimeout = 10L
        val client: OkHttpClient = OkHttpClient.Builder().readTimeout(readTimeout, TimeUnit.SECONDS)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS).build()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(iamportProperty.apiUrl)
            .addConverterFactory(buildGsonConverter())
            .client(client).build()

        return retrofit.create(Iamport::class.java)
    }

    private fun buildGsonConverter(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()

        // Adding custom deserializers
        val escrowInvoiceStrategy: Any = EscrowInvoiceEntrySerializer()

        gsonBuilder.registerTypeAdapter(ScheduleEntry::class.java, ScheduleEntrySerializer())
        gsonBuilder.registerTypeAdapter(Schedule::class.java, ScheduleEntrySerializer())
        gsonBuilder.registerTypeAdapter(PaymentBalanceEntry::class.java, BalanceEntrySerializer())
        gsonBuilder.registerTypeAdapter(EscrowLogisInvoiceData::class.java, escrowInvoiceStrategy)
        gsonBuilder.registerTypeAdapter(EscrowLogisInvoice::class.java, escrowInvoiceStrategy)

        val myGson = gsonBuilder.create()

        return GsonConverterFactory.create(myGson)
    }
}