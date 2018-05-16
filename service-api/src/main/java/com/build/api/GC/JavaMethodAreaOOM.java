package com.build.api.GC;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class JavaMethodAreaOOM {

	public static void main(String sck[]){
		
		while(true){
			Enhancer enhancer =  new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor(){
				@Override
				public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy proxy)
						throws Throwable {
					return proxy.invoke(obj, args);
				}
			});
			enhancer.create();
		}
	}
	
	static class OOMObject{
		
	}
}
