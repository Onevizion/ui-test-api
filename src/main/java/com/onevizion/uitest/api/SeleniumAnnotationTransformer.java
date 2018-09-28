package com.onevizion.uitest.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class SeleniumAnnotationTransformer implements IAnnotationTransformer {

    private static final ClassPool pool = ClassPool.getDefault();

    @Override
    public void transform(ITestAnnotation annotation, @SuppressWarnings("rawtypes") Class testClass, @SuppressWarnings("rawtypes") Constructor testConstructor, Method testMethod) {
        List<TestNgMethod> testNgMethods = new ArrayList<TestNgMethod>();
        for (int i = 0; i < testClass.getDeclaredMethods().length; i++) {
            if (testClass.getDeclaredMethods()[i].isAnnotationPresent(Test.class)) {
                testNgMethods.add(new TestNgMethod(testClass.getDeclaredMethods()[i].getName(), getMethodLineNumber(testClass, testClass.getDeclaredMethods()[i])));
            }
        }

        Collections.sort(testNgMethods, (TestNgMethod arg0, TestNgMethod arg1) -> Integer.compare(arg0.getLineNumber(), arg1.getLineNumber()));

        int methodNumber = 0;

        for (int i = 0; i < testNgMethods.size(); i++) {
            if (testNgMethods.get(i).getMethodName().equals(testMethod.getName())) {
                methodNumber = i;
                break;
            }
        }

        if (methodNumber > 0) {
            annotation.setDependsOnMethods(new String[]{testNgMethods.get(methodNumber - 1).getMethodName()});
        } else {
            annotation.setDependsOnMethods(null);
        }
   }

    private static int getMethodLineNumber(@SuppressWarnings("rawtypes") Class testClass, Method testMethod) {
        pool.insertClassPath(new ClassClassPath(testClass));

        CtClass classX = null;
        try {
            classX = pool.get(testClass.getName());
        } catch (NotFoundException e) {
            throw new RuntimeException("Class " + testClass.getName() + " not found", e);
        }

        CtMethod methodX = null;
        try {
            methodX = classX.getDeclaredMethod(testMethod.getName());
        } catch (NotFoundException e) {
            throw new RuntimeException("Method " + testMethod.getName() + " not found", e);
        }

        classX.detach();
        return methodX.getMethodInfo().getLineNumber(0);
    }

    public class TestNgMethod {

        private String methodName;
        private int lineNumber;

        TestNgMethod(String methodName, int lineNumber){
            this.methodName = methodName;
            this.lineNumber = lineNumber;
        }

        public String getMethodName() {
            return methodName;
        }

        public int getLineNumber() {
            return lineNumber;
        }

    }

}