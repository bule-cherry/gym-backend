import com.clz.GYMServiceWebApplication;
import com.clz.utils.ResultUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
@SpringBootTest(classes = GYMServiceWebApplication.class)
public class CodeTest {
    @Resource
    DefaultKaptcha kaptcha;
    @Test
    public void test() {
        BufferedImage bufferedImage = kaptcha.createImage("V我50");// 这个是内存中的图片, 每个像素点
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage,"jpg", out);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(out.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64,"+ base64.replace("\r\n","");
            System.out.println(captchaBase64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
