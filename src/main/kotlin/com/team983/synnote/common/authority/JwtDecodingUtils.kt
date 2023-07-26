package com.team983.synnote.common.authority

import com.google.gson.JsonParser
import com.team983.synnote.common.dto.UserAttributeDto
import io.jsonwebtoken.Jwts
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.net.ssl.HttpsURLConnection

fun decodeJwt(encodedJwt: String): UserAttributeDto {
    // Step 1: Get the key id from JWT headers (the kid field)
    val jwtHeaders = encodedJwt.split('.')[0]
    val decodedJwtHeaders = Base64.getUrlDecoder().decode(jwtHeaders)
    val decodedJson = String(decodedJwtHeaders, StandardCharsets.UTF_8)
    val kid = JsonParser.parseString(decodedJson).asJsonObject["kid"].asString

    // Step 2: Get the public key from regional endpoint
    val url = "https://public-keys.auth.elb.ap-northeast-2.amazonaws.com/$kid"
    val httpClient = URI(url).toURL().openConnection() as HttpsURLConnection
    httpClient.requestMethod = "GET"
    httpClient.setRequestProperty("User-Agent", "Mozilla/5.0")

    var pubKey: String
    httpClient.inputStream.use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            pubKey = response.toString()
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
        }
    }

    // Step 3: Get the payload
    val decoder: Base64.Decoder = Base64.getDecoder()
    val keySpec = X509EncodedKeySpec(decoder.decode(pubKey))
    val keyFactory = KeyFactory.getInstance("EC")
    val ecPublicKey = keyFactory.generatePublic(keySpec)
    val parser = Jwts.parserBuilder().setSigningKey(ecPublicKey).build()
    val payload = parser.parseClaimsJws(encodedJwt).body

    val sub = payload.subject
    val email = payload["email"] as String

    return UserAttributeDto(sub, email)
}
