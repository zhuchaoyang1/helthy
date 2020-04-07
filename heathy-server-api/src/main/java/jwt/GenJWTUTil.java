package jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * JWT 工具类
 */
@Component
public class GenJWTUTil {

    @Value("${jwt.secret:geiwodiangasfdjsikolkjikolkijswe}")
    private String jwtSecret;

    /**
     * 生成一个token
     *
     * @param payloadMap JWT 载荷数据
     * @return
     * @throws JOSEException
     */
    public String creatToken(Map<String, Object> payloadMap) throws JOSEException {
        //3.先建立一个头部Header
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        //建立一个载荷Payload
        Payload payload = new Payload(new JSONObject(payloadMap));
        //将头部和载荷结合在一起
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //建立一个密匙
        JWSSigner jwsSigner = new MACSigner(jwtSecret.getBytes());
        //签名
        jwsObject.sign(jwsSigner);
        //生成token
        return jwsObject.serialize();
    }

}
