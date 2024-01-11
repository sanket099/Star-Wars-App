package com.sankets.starwars.domain.utils

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //Request
        var request: Request = chain.request()
        try {
            val requestTime = System.currentTimeMillis()

            val requestBuilder = request.newBuilder()

            request = requestBuilder.build()

            request.run {
                Timber.d(
                    "NETWORK_REQUEST => RequestUrl: ${url}\nRequestBody: ${
                        bodyToString(this)
                    }",
                )
            }


            //Response
            val response = chain.proceed(request)
            response.run {
                Timber.d(
                    "NETWORK_RESPONSE =>Url: ${request.url} " +
                            "  & ResponseCode: $code \nResponseIsSuccessful: ${isSuccessful}\n" + "ResponseTime: ${System.currentTimeMillis() - requestTime}\nResponseMessage: ${message}\nResponseObject: ${
                        peekBody(Long.MAX_VALUE).string()
                    }"
                )
            }
            return response.newBuilder()
                .body(response.body!!.bytes().toResponseBody(response.body?.contentType()))
                .build()

        } catch (e: Exception) {
            request.run {
                Timber.e("NETWORK_RESPONSE => Error while parsing response for url: ${url}\n Error: ${e.message}")
            }
            return Response.Builder().request(request).code(999).protocol(Protocol.HTTP_1_1)
                .message(e.message.toString()).body("{${e}}".toResponseBody(null)).build()
        }
    }


    private fun bodyToString(request: Request): String? {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: Exception) {
            e.message
        }
    }
}