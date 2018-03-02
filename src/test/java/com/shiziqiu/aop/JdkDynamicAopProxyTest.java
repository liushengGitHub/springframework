package com.shiziqiu.aop;

import org.junit.Test;

import com.shiziqiu.service.HelloWorldService;
import com.shiziqiu.service.impl.HelloWorldServiceImpl;
import com.shiziqiu.springframework.aop.AdvisedSupport;
import com.shiziqiu.springframework.aop.JdkDynamicAopProxy;
import com.shiziqiu.springframework.aop.TargetSource;
import com.shiziqiu.springframework.context.ApplicationContext;
import com.shiziqiu.springframework.context.ClassPathXmlApplicationContext;
/**
 * @title : JdkDynamicAopProxyTest
 * @author : shiziqiu
 * @date : 2018年3月2日 下午1:59:27
 * @Fun :
 */
public class JdkDynamicAopProxyTest {

	@Test
	public void testInterceptor() throws Exception {
		// --------- helloWorldService without AOP，这里是和IOC结合的吧
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("testioc.xml");
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		//helloWorldService.helloWorld();

		System.out.println("-----------分割-----------------------------");
		
		
		
		// --------- helloWorldService with AOP，这里是纯粹的代理模式了
		// 1. 设置被代理对象(Joinpoint)
		AdvisedSupport advisedSupport = new AdvisedSupport();
		TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldServiceImpl.class,HelloWorldService.class);
		//设置原始对象
		advisedSupport.setTargetSource(targetSource);

		// 2. 设置拦截器(Advice)
		TimerInterceptor timerInterceptor = new TimerInterceptor();
		//设置拦截方法
		advisedSupport.setMethodInterceptor(timerInterceptor);

		// 3. 创建代理(Proxy)
		JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);
		//获取代理类实例，调用代理类的方法时，应该会自动取调用代理类invoke方法。
		HelloWorldService helloWorldServiceProxy = (HelloWorldService) jdkDynamicAopProxy.getProxy();

		// 4. 基于AOP的调用
		helloWorldServiceProxy.helloWorld();

	}
}
