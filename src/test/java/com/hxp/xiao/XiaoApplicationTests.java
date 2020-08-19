package com.hxp.xiao;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XiaoApplicationTests {

	/**
	 * https://github.com/wslong/jetbrains-agent/tree/master/v3.2.1
	 * 半径为2m的半球形盛满水水池，若将水从上方全抽出所做功为Q，则Q/2时抽去水之百分比（答案为73.25%）;
	 * 将73.25%做1亿次md2加密
	 * 最终结果：b64488729b831330072b051ce45172c8
	 * 破解插件地址：https://gitee.com/pengzhile/ide-eval-resetter
	 */
	@Test
	void getResult() {
		String val = "73.25%";
		for (int i = 0; i < 100000000; i++) {
			val = DigestUtils.md2Hex(val);
			System.out.println(i);
		}
		System.out.println("最终结果：" + val);
	}

}
