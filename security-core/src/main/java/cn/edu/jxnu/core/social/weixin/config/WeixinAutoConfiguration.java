package cn.edu.jxnu.core.social.weixin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

import cn.edu.jxnu.core.properties.SecurityProperties;
import cn.edu.jxnu.core.properties.WeixinProperties;
import cn.edu.jxnu.core.social.ImoocConnectView;
import cn.edu.jxnu.core.social.weixin.connet.WeixinConnectionFactory;

/**
 * 微信登录配置
 * 
 * @author 梦境迷离.
 * @time 2018年6月2日
 * @version v1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "imooc.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}

	/**
	 * 注册绑定视图
	 * 
	 * 绑定/解绑
	 *
	 */
	@Bean({ "connect/weixinConnect", "connect/weixinConnected" })
	@ConditionalOnMissingBean(name = "weixinConnectedView") // 只有没有weixinConnectedView的时候才会使用默认
	public View weixinConnectedView() {
		return new ImoocConnectView();
	}

}
