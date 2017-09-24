/**
 * Created by jingchun.zhang on 2017/9/23.
 */
package com.whh.common.utils.hessian;

import com.caucho.hessian.client.HessianProxyFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author jingchun.zhang
 * @version 1.0.0
 * @date 2017/9/23
 */
public class HessianUtil {

    /**
     *  客户端通过hessian协议访问dubbo接口
     * @param url
     * @param clazz
     * @param methodName 方法名称
     * @param parms  参数列表
     * @param <T>
     * @throws Exception
     */
    public static <T> void request(String url, Class<T> clazz, String methodName, Object ...parms){
        System.out.println("服务："+ url);
        System.out.println("方法：" + methodName);
        System.out.println("参数：" + Arrays.toString(parms));
        HessianProxyFactory factory = new HessianProxyFactory();
        //支持方法重载
        factory.setOverloadEnabled(true);
        try {
            T basic = (T) factory.create(clazz, url);
            //获取Method并调用
            Method[] methods = clazz.getMethods();
            Method method = null;
            for (Method m: methods) {
                if (m.getName().equals(methodName)){
                    method = m;
                    break;
                }
            }
            if (Objects.isNull(method)){
                throw new IllegalArgumentException("class中找不到" + methodName + "方法，请检查方法名称是否正确！");
            }
            Optional ret = Optional.ofNullable(method.invoke(basic, parms));
            System.out.println("返回数据：" + ret.orElse(""));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        String serviceUrl = "http://192.168.6.31:9096/com.wwwarehouse.xdw.commonindustry.service.TempPbContractService";
        Long buId = 10001972L;
        Long rsDefinedUkid = 123L;
        //次该clazz
        HessianUtil.request(serviceUrl, null, "removeRsItem", buId, rsDefinedUkid);
    }

}
