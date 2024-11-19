package portfolio.eams.config.jasypt;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.util.StringUtils.hasText;

@Configuration
public class JasyptEncryptorConfig {
    /*
    jasypt 암호화 & 복호화 로직
     */

    @Bean
    public String getJasyptKey() {
        String jasyptPw = System.getProperty("jasypt.encryptor.password");
        if(!hasText(jasyptPw)) throw new IllegalStateException("jasypt 복호화 패스워드가 없습니다.");
        return jasyptPw;
    }

    public String encrypt(String plain) {
        AES256TextEncryptor enc = new AES256TextEncryptor();
        enc.setPassword(getJasyptKey());
//        enc.setPassword(ENCRYPTOR_PASSWORD);

        return enc.encrypt(plain);
    }

    public String decrypt(String encrypted) {
        AES256TextEncryptor dec = new AES256TextEncryptor();
        dec.setPassword(getJasyptKey());
//        dec.setPassword(ENCRYPTOR_PASSWORD);

        return dec.decrypt(encrypted);
    }


}
