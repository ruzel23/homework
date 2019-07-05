package ru.sberbank.school.task13;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanFieldCopierImpl implements BeanFieldCopier {

    @Override
    public void copy(Object from, Object to) {
        Map<String, Method> gettersMap;
        Map<String, Method> settersMap;
        Method[] getters = from.getClass().getMethods();
        Method[] setters = to.getClass().getMethods();
        gettersMap = Arrays.stream(getters).
                filter(method -> method.getName().startsWith("get")).
                filter(method -> method.getParameterCount() == 0).
                filter(method -> !method.getReturnType().equals(Void.TYPE)).
                collect(Collectors.toMap(method -> method.getName().substring(3), method -> method));
        settersMap = Arrays.stream(setters).
                filter(method -> method.getName().startsWith("set")).
                filter(method -> method.getParameterCount() == 1).
                filter(method -> method.getReturnType().equals(Void.TYPE)).
                collect(Collectors.toMap(method -> method.getName().substring(3), method -> method));
        System.out.println(gettersMap);
        System.out.println(settersMap);

        for(String set: settersMap.keySet()) {
            Method getter = gettersMap.get(set);
            Method setter = settersMap.get(set);

            if (getter != null && getter.getReturnType().equals(setter.getParameterTypes()[0])) {
                try {
                    setter.invoke(to, getter.invoke(from));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        BeanFieldCopier beanFieldCopier = new BeanFieldCopierImpl();
        Ruzik Ruzik = new Ruzik(23, "Рузелька", true);
        Ruzik Ruzik1 = new Ruzik();
        beanFieldCopier.copy(Ruzik, Ruzik1);
        System.out.println("Ruzik1:" + Ruzik1.toString());
        System.out.println("Ruzik:" + Ruzik.toString());
    }
}


class Ruzik {

    private int age;
    private String name;
    private boolean sex;

    @Override
    public String toString() {
        return "{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }

    public Ruzik() {
    }

    public Ruzik(int age, String name, boolean sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}