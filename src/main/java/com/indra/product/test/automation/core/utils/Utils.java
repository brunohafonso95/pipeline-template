
package com.indra.product.test.automation.core.utils;

import java.lang.reflect.Method;

/**
 * O Objetivo desta classe é prover funções de utilidades para o projeto.
 * 
 * @author msergio
 * @since Setembro de 2018
 * @version {version}
 */
 public class Utils {

    public void getMethods() {
        Class<?> cls = null;
        try {
            cls = Class.forName("org.openqa.selenium.By");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = cls.getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            m.getName();

        }

    }

}
