package com.hitachi.oss.ciba.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hitachi.oss.ciba.AuthenticationChannelControlParameterStore;

@Controller
@RequestMapping("/params")
public class AuthenticationChannelParameterController {

    @RequestMapping("/")
    public String input() {
        return "params/index";
    }

    @RequestMapping(value = "paramsform", method = RequestMethod.POST)
    public String output(
            @RequestParam(name = "authresult") String authResult,
            @RequestParam(name = "waitinsec") int waitInSec,
            Model model) {
        model.addAttribute("authresult", authResult);
        model.addAttribute("waitinsec", waitInSec);
        AuthenticationChannelControlParameterStore.getInstance().setAuthResult(authResult);
        AuthenticationChannelControlParameterStore.getInstance().setWaitingInSec(waitInSec);
        return "params/index";
    }
}
