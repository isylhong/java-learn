package redis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    public static void main(String[] args) {
        //模拟验证码发送
        generateVerifyCode("13678765435");
//        "VerifyCode13678765435:count"

        //模拟验证码校验
//        checkVerifyCode("13678765435","476251");
    }

    //3 验证码校验
    public static void checkVerifyCode(String phone, String code) {
        Jedis jedis = getJedis();
        //从redis获取验证码
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);

        // 判断
        if (redisCode.equals(code)) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        jedis.close();

    }


    //2 每个手机每天只能发送三次，验证码放到redis中，设置过期时间120
    public static void generateVerifyCode(String phone) {
        Jedis jedis = getJedis();

        String countKey = "VerifyCode" + phone + ":count";
        String codeKey = "VerifyCode" + phone + ":code";

        String count = jedis.get(countKey);
        if (count == null) {
            //没有发送次数，第一次发送
            //设置发送次数是1
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) < 3) {
            //发送次数+1
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) >= 3) {
            //发送三次，不能再发送
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
        }

        String vcode = getCode();
        jedis.setex(codeKey, 120, vcode);
        jedis.close();

    }


    //1 生成6位数字验证码
    public static String getCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public static Jedis getJedis() {
        String hostname = "192.168.80.128";
        int port = 6379;
        Jedis jedis = new Jedis(hostname, port);
        return jedis;
    }
}
