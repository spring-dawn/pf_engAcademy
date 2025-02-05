package portfolio.eams.config.jasypt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("notebook")
class JasyptEncryptorConfigTest {

    // jasypt encryption

    @Autowired
    JasyptEncryptorConfig jasypt;

    @Test
    @DisplayName("jasypt 활용")
    void setJasypt() {
        String enc = "enc";

        String encrypted = jasypt.encrypt(enc);
        System.out.println("암호화:" + encrypted);

        String decrypted = jasypt.decrypt(encrypted);
        System.out.println("복호화:" + decrypted);
    }


}