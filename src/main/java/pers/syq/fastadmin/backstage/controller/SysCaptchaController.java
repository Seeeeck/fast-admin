package pers.syq.fastadmin.backstage.controller;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.vo.CaptchaVO;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("sys/captcha")
public class SysCaptchaController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping
    public R<CaptchaVO> captcha(){
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String key = IdUtil.fastUUID();
        String image = specCaptcha.toBase64();
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setKey(key);
        captchaVO.setImage(image);
        redisTemplate.opsForValue().set(key,specCaptcha.text().toLowerCase(),10, TimeUnit.MINUTES);
        return R.ok(captchaVO);
    }

}
