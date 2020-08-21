package com.hjrpc.constant;

import java.util.Random;

public class Constant {
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 12345;
    public static final String DELIMITER = "@!";
    public static final String[] MESSAGES = new String[]{
            "洞若观火：形容看得清楚明白。",
            "对簿公堂：在公堂受审。",
            "耳濡目染：形容见得多听得多之后，无形之中受到影响。",
            "耳熟能详：听得次数多了，熟悉得能详尽地说出来。",
            "耳提面命：表示长辈的谆谆教导，不用于同辈之间和贬义。",
            "罚不当罪：处罚和所犯的罪行不相当，多指处罚过重。",
            "翻云覆雨：比喻反复无常或玩弄手段。",
            "繁文缛节：比喻其他烦琐多余的事项，也说繁文缛礼。比喻疑神疑鬼，妄自惊慌。",
            "方枘圆凿：形容格格不入。",
            "方兴未艾：事物正在发展，一时不会终止。",
            "耿耿于怀：对某些事总记在心里，形容心存怨恨。（一般指自己对某些事不满）",
            "功败垂成：在快要成功的时候遭到失败。（多含惋惜意）",
            "狗尾续貂：泛指以坏续好，前后不相称，多指文艺作品。",
            "孤注一掷：把所有的钱一下子投做赌注，企图最后得胜。比喻在危急时把全部力量拿出来冒一次险。",
            "瓜田李下：经过瓜田，不弯下身来提鞋，免得人家怀疑摘瓜；走过李树下面，不举起手来整理帽子，免得人家怀疑摘李子。比喻容易引起嫌疑的地方。",
            "刮目相看：用新的眼光来看待。",
            "挂一漏万：形容列举不全，遗漏很多。",
            "管窥蠡测：从竹管里看天，用瓢来量海水，比喻眼光狭窄，见识短浅。",
            "光天化日：比喻大家看得很清楚的地方。"
    };
    public static final Random random = new Random();

    public static String getFixedMessage() {

        return "这是一条定长消息";
    }
    public static String getMessage(boolean withLineSeparator) {
        if(withLineSeparator) {
            return MESSAGES[random.nextInt(MESSAGES.length)] + System.lineSeparator();
        }else{
            return MESSAGES[random.nextInt(MESSAGES.length)];
        }
    }
}
