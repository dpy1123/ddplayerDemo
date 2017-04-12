package demo.devgo.top;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Vector;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	Vector<Danmaku> danmakuRepos = new Vector<>();

	@Controller
    public class DanmakuController {
        @RequestMapping({"/"})
        public String index() {
            return "forward:/view.html";
        }

        @RequestMapping("getDanmaku")
        public @ResponseBody List<Danmaku> getDanmaku(String vid){
            List<Danmaku> result = null;
            try{
                result = danmakuRepos;
            }catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @RequestMapping(value = "sendDanmaku", method = { RequestMethod.POST })
        public @ResponseBody String saveDanmaku(@RequestBody Danmaku danmu){//danmu前台是json格式post过来的，要加@RequestBody以解析
            try {
                if ("Custom".equals(danmu.getStyle())) {
                    danmu.setClazz(URLDecoder.decode(danmu.getClazz(), "UTF-8"));
                    danmu.setParam(URLDecoder.decode(danmu.getParam(), "UTF-8"));
                } else {
                    danmu.setText(URLDecoder.decode(danmu.getText(), "UTF-8"));// danmu的Text在前台做过encodeURI
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            danmakuRepos.add(danmu);

            return "ok";
        }
    }


}
