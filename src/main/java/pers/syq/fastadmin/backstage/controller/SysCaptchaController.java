package pers.syq.fastadmin.backstage.controller;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.common.utils.RedisUtils;
import pers.syq.fastadmin.backstage.constants.WebConstants;
import pers.syq.fastadmin.backstage.vo.CaptchaVO;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("sys/captcha")
public class SysCaptchaController {

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping
    public R<CaptchaVO> captcha(){
        SpecCaptcha specCaptcha = new SpecCaptcha(120, 45, 5);
        String key = IdUtil.fastUUID();
        String image = specCaptcha.toBase64();
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setKey(key);
        captchaVO.setImage(image);
        redisUtils.set(key,specCaptcha.text().toLowerCase(), WebConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        return R.ok(captchaVO);
    }

}
